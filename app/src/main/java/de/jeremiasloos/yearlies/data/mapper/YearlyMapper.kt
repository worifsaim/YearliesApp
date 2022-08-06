package de.jeremiasloos.yearlies.data.mapper

import de.jeremiasloos.yearlies.data.local.entity.YearlyEntity
import de.jeremiasloos.yearlies.domain.model.YearlyEvent
import java.time.LocalDate

fun YearlyEntity.toYearlyEvent(): YearlyEvent {
    return YearlyEvent(
        name = name,
        date = LocalDate.of(year, month, day),
        note = note,
        id = id
    )
}

fun YearlyEvent.toYearlyEntity(): YearlyEntity {
    return YearlyEntity(
        name = name,
        day = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        note = note,
        id = id
    )
}