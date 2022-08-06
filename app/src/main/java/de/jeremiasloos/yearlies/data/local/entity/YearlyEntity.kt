package de.jeremiasloos.yearlies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class YearlyEntity(
    val name: String,
    val day: Int,
    val month: Int,
    val year: Int,
    val note: String,
    @PrimaryKey val id: Int? = null
)
