package de.jeremiasloos.yearlies.presentation.main

import de.jeremiasloos.yearlies.domain.model.YearlyEvent

sealed class MainEvent {
    data class OnDeleteYearly(val yearly: YearlyEvent): MainEvent()
}
