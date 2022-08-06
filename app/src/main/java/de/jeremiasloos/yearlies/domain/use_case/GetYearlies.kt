package de.jeremiasloos.yearlies.domain.use_case

import de.jeremiasloos.yearlies.domain.model.YearlyEvent
import de.jeremiasloos.yearlies.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetYearlies(
    private val repository: Repository
) {

    operator fun invoke(): Flow<List<YearlyEvent>> {
        return repository.getYearlies()
    }
}