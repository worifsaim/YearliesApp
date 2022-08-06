package de.jeremiasloos.yearlies.presentation.settings

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.jeremiasloos.yearlies.R
import de.jeremiasloos.yearlies.domain.preferences.Preferences
import de.jeremiasloos.yearlies.domain.use_case.AlarmCancel
import de.jeremiasloos.yearlies.domain.use_case.AlarmStart
import de.jeremiasloos.yearlies.util.UiEvent
import de.jeremiasloos.yearlies.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: Preferences,
    //private val alarmStart: AlarmStart,
    //private val alarmCancel: AlarmCancel,
    application: Application
) : AndroidViewModel(application) {

    var notificationsInterval by mutableStateOf(preferences.loadNotificationsInterval())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnIntervalSave -> {
                saveClick()
            }
        }
    }

    fun onIntervalClick(interval: String) {
        notificationsInterval = interval
    }

    private fun saveClick() {
        viewModelScope.launch {
            preferences.saveNotificationsInterval(interval = notificationsInterval)
            if (notificationsInterval == "off") {
                //alarmCancel
                AlarmCancel(getApplication<Application>().applicationContext).alarmCancel()
            } else {
                // this one is not working...
                // alarmStart
                // yet this one is... where is the problem?!
                AlarmStart(getApplication<Application>().applicationContext).alarmStart()
            }
            _uiEvent.send(
                UiEvent.ShowSnackbar(
                    UiText.StringResource(R.string.settings_saved)
                )
            )
            _uiEvent.send(UiEvent.Success)
        }
    }
}