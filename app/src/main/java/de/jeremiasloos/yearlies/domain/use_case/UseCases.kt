package de.jeremiasloos.yearlies.domain.use_case

data class UseCases(
    val addEvent: AddEvent,
    val deleteEvent: DeleteEvent,
    val getYearlies: GetYearlies
)
