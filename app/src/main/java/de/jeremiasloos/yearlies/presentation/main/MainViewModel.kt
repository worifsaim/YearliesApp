package de.jeremiasloos.yearlies.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.jeremiasloos.yearlies.R
import de.jeremiasloos.yearlies.domain.model.MonthOfYearlyEvents
import de.jeremiasloos.yearlies.domain.use_case.UseCases
import de.jeremiasloos.yearlies.util.UiEvent
import de.jeremiasloos.yearlies.util.UiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getYearliesJob: Job? = null

    init {
        refreshYearlies()
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnDeleteYearly -> {
                viewModelScope.launch {
                    useCases.deleteEvent(event.yearly)
                    refreshYearlies()
                }
            }
        }
    }

    private fun refreshYearlies() {
        getYearliesJob?.cancel()
        /*getYearliesJob = useCases
            .getYearlies()
            .onEach { yearlies ->
                val today = LocalDate.now()
                val passedDays = yearlies.filter { it.date.dayOfYear < today.dayOfYear }
                val todayAndAheadDays = yearlies.filter { it.date.dayOfYear >= today.dayOfYear }
                val newYearliesOrder = todayAndAheadDays + passedDays
                state = state.copy(
                    yearlies = newYearliesOrder
                )
            }
            .launchIn(viewModelScope)
        */
        getYearliesJob = useCases
            .getYearlies()
            .onEach { yearlies ->
                val monthsList: MutableList<MonthOfYearlyEvents> = mutableListOf()
                val today = LocalDate.now()
                val currentMonth = today.monthValue
                val currentMonthTwice = today.dayOfMonth != 1
                val upperBound = if (currentMonthTwice) 13 else 12
                for (i in 1..upperBound) {
                    var thisMonthsValue = currentMonth + i - 1
                    if (thisMonthsValue > 12) thisMonthsValue -= 12
                    val thisMonth = LocalDate.of(2000, thisMonthsValue, 1).month
                    val days = when (i) {
                        1 -> {
                            yearlies.filter { it.date.monthValue == thisMonthsValue && it.date.dayOfMonth >= today.dayOfMonth }
                        }
                        13 -> {
                            yearlies.filter { it.date.monthValue == thisMonthsValue && it.date.dayOfMonth < today.dayOfMonth }
                        }
                        else -> {
                            yearlies.filter { it.date.monthValue == thisMonthsValue }
                        }
                    }
                    monthsList.add(MonthOfYearlyEvents(thisMonth, days))
                }
                state = state.copy(
                    monthYearlies = monthsList
                )
            }
            .launchIn(viewModelScope)
    }

    fun confirmClick(name: String) {
        viewModelScope.launch {
            _uiEvent.send(
                UiEvent.ShowSnackbar(
                    UiText.StringResourceWithParameter(R.string.main_alert_confirmed, name)
                )
            )
        }
    }
/*
    fun cancelClick() {
        viewModelScope.launch {
            _uiEvent.send(
                UiEvent.ShowSnackbar(
                    UiText.StringResource(R.string.main_alert_cancelled)
                )
            )
        }
    }
 */

}