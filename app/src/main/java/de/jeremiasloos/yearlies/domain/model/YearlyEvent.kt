package de.jeremiasloos.yearlies.domain.model

import java.time.LocalDate

data class YearlyEvent(
    val name: String,
    val date: LocalDate,
    val note: String,
    val id: Int?
)
