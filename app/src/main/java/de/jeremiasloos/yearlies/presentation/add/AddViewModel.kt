package de.jeremiasloos.yearlies.presentation.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.jeremiasloos.yearlies.R
import de.jeremiasloos.yearlies.domain.preferences.Preferences
import de.jeremiasloos.yearlies.domain.use_case.FilterOutDigits
import de.jeremiasloos.yearlies.domain.use_case.UseCases
import de.jeremiasloos.yearlies.util.UiEvent
import de.jeremiasloos.yearlies.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val useCases: UseCases,
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
): ViewModel() {

    var state by mutableStateOf(AddState())
        private set

    var day by mutableStateOf("")
        private set

    var month by mutableStateOf("")
        private set

    var year by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set

    var note by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onDayEnter(day: String) {
        if (day.length <= 2) {
            this.day = filterOutDigits(day)
        }
    }

    fun onMonthEnter(month: String) {
        if (month.length <= 2) {
            this.month = filterOutDigits(month)
        }
    }

    fun onYearEnter(year: String) {
        if (year.length <= 4) {
            this.year = filterOutDigits(year)
        }
    }

    fun onNameEnter(name: String) {
        this.name = name
    }

    fun onNoteEnter(note: String) {
        this.note = note
    }

    fun onEvent(event: AddEvent) {
        when(event) {
            is AddEvent.OnSaveClick -> {
                saveClick()
            }
            else -> Unit
        }
    }

    private fun saveClick() {
        viewModelScope.launch {
            val dayNumber = day.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(R.string.add_day_cant_be_empty)
                    )
                )
                return@launch
            }
            val monthNumber = month.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(R.string.add_month_cant_be_empty)
                    )
                )
                return@launch
            }
            val yearNumber = year.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(R.string.add_year_cant_be_empty)
                    )
                )
                return@launch
            }


            if (name.isBlank()) {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(R.string.add_name_cant_be_empty)
                    )
                )
                return@launch
            }

            val dateDate: LocalDate
            try {
                dateDate = LocalDate.of(yearNumber, monthNumber, dayNumber)
            } catch(e: Exception) {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(R.string.add_not_a_valid_date)
                    )
                )
                return@launch
            }


            useCases.addEvent(
                name = name,
                date = dateDate,
                note = note
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}