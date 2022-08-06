package de.jeremiasloos.yearlies.data.repository

import de.jeremiasloos.yearlies.data.local.YearlyDao
import de.jeremiasloos.yearlies.data.mapper.toYearlyEvent
import de.jeremiasloos.yearlies.data.mapper.toYearlyEntity
import de.jeremiasloos.yearlies.domain.model.YearlyEvent
import de.jeremiasloos.yearlies.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val dao: YearlyDao
): Repository {
    override suspend fun insertYearlyEvent(yearly: YearlyEvent) {
        dao.insertYearly(yearly.toYearlyEntity())
    }

    override suspend fun deleteYearlyEvent(yearly: YearlyEvent) {
        dao.deleteYearly(yearly.toYearlyEntity())
    }

    override fun getYearlies(): Flow<List<YearlyEvent>> {
        return dao.getYearlies()
            .map { entities ->
                entities.map { it.toYearlyEvent() }
            }
    }
}