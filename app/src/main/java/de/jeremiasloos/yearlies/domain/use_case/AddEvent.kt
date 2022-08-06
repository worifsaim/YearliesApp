package de.jeremiasloos.yearlies.domain.use_case

import de.jeremiasloos.yearlies.R
import de.jeremiasloos.yearlies.domain.model.YearlyEvent
import de.jeremiasloos.yearlies.domain.repository.Repository
import java.time.LocalDate
import java.util.*

class AddEvent(
    private val repository: Repository
) {
    suspend operator fun invoke(
        name: String,
        date: LocalDate,
        note: String
    ) {
        repository.insertYearlyEvent(
            YearlyEvent(
                name = name,
                date = date,
                note = note,
                id = null
            )
        )
    }
}