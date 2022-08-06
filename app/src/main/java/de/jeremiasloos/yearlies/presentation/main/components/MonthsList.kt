package de.jeremiasloos.yearlies.presentation.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import de.jeremiasloos.yearlies.domain.model.YearlyEvent
import de.jeremiasloos.yearlies.presentation.main.EventBox
import de.jeremiasloos.yearlies.presentation.main.MainViewModel
import java.time.LocalDate
import java.time.Month

@Composable
fun MonthsList(
    snackbarHostState: SnackbarHostState,
    viewModel: MainViewModel,
    month: Month,
    yearlies: List<YearlyEvent>
) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    val yearlyToDelete = remember {
        mutableStateOf(
            YearlyEvent(
                date = LocalDate.now(),
                name = "",
                note = "",
                id = null
            )
        )
    }
    MonthSepearator(month = month)
    Column {
        for (yearly in yearlies) {
            EventBox(
                date = yearly.date,
                name = yearly.name,
                note = yearly.note,
                onDeleteClick = {
                    yearlyToDelete.value = yearly
                    showDeleteDialog.value = true
                }
            )
        }
    }
    if (showDeleteDialog.value) {
        DeleteAlertDialogComponent(
            snackbarHostState = snackbarHostState,
            viewModel = viewModel,
            yearly = yearlyToDelete.value,
            onClose = { showDeleteDialog.value = false }
        )
    }
}