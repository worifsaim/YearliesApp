package de.jeremiasloos.yearlies.domain.use_case

import de.jeremiasloos.yearlies.domain.model.YearlyEvent
import de.jeremiasloos.yearlies.domain.repository.Repository

class DeleteEvent(
    private val repository: Repository
) {

    suspend operator fun invoke(yearlyEvent: YearlyEvent) {
        repository.deleteYearlyEvent(yearlyEvent)
    }
}