package de.jeremiasloos.yearlies.presentation.main

import de.jeremiasloos.yearlies.domain.model.MonthOfYearlyEvents
import de.jeremiasloos.yearlies.domain.model.YearlyEvent
import java.time.LocalDate

data class MainState(
    val date: LocalDate = LocalDate.now(),
    val yearlies: List<YearlyEvent> = emptyList(),
    val monthYearlies: List<MonthOfYearlyEvents> = emptyList()
)
