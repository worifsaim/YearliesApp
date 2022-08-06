package de.jeremiasloos.yearlies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import de.jeremiasloos.yearlies.data.local.entity.YearlyEntity

@Database(
    entities = [YearlyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class YearlyDatabase: RoomDatabase() {

    abstract val dao: YearlyDao
}