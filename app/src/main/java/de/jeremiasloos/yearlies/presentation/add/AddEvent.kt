package de.jeremiasloos.yearlies.presentation.add

import java.time.LocalDate

sealed class AddEvent {
    data class OnDayChange(val day: String): AddEvent()
    data class OnMonthChange(val month: String): AddEvent()
    data class OnYearChange(val year: String): AddEvent()
    data class OnNameChange(val name: String): AddEvent()
    data class OnNoteChange(val note: String): AddEvent()
    object OnAdd: AddEvent()
    object OnSaveClick: AddEvent()
    data class OnAddFocusChange(val isFocused: Boolean): AddEvent()
}
