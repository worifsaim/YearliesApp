package de.jeremiasloos.yearlies.data.local

import androidx.room.*
import de.jeremiasloos.yearlies.data.local.entity.YearlyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface YearlyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYearly(yearlyEntity: YearlyEntity)

    @Delete(entity = YearlyEntity::class)
    suspend fun deleteYearly(yearlyEntity: YearlyEntity)

    @Query(
        """
            SELECT *
            FROM yearlyentity
            ORDER BY month, day, name
        """
    )
    fun getYearlies(): Flow<List<YearlyEntity>>
}