package de.jeremiasloos.yearlies.domain.repository

import de.jeremiasloos.yearlies.domain.model.YearlyEvent
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun insertYearlyEvent(yearly: YearlyEvent)

    suspend fun deleteYearlyEvent(yearly: YearlyEvent)

    fun getYearlies(): Flow<List<YearlyEvent>>
}