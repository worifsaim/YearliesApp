package de.jeremiasloos.yearlies.presentation.settings

sealed class SettingsEvent {
    object OnIntervalSave: SettingsEvent()
}