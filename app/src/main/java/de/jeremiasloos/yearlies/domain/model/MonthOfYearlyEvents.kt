package de.jeremiasloos.yearlies.domain.model

import java.time.Month

data class MonthOfYearlyEvents(
    val month: Month,
    val yearlies: List<YearlyEvent>
)
