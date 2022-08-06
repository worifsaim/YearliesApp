package de.jeremiasloos.yearlies.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.jeremiasloos.yearlies.R
import de.jeremiasloos.yearlies.domain.model.YearlyEvent
import de.jeremiasloos.yearlies.presentation.main.components.DeleteAlertDialogComponent
import de.jeremiasloos.yearlies.presentation.main.components.MonthsList
import de.jeremiasloos.yearlies.ui.theme.LocalSpacing
import de.jeremiasloos.yearlies.util.UiEvent
import java.time.LocalDate
import java.time.Month

@Composable
fun MainScreen(
    snackbarHostState: SnackbarHostState,
    onNavigatetoCredits: () -> Unit,
    onNavigatetoAdd: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
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
    //val currentEntryMonth = remember { mutableStateOf(LocalDate.now().month) }
    var currentEntryMonth2: Month? = null
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceSmall)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.main_heading),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(spacing.spaceLarge))
            /*LazyColumn {
                items(state.yearlies) { yearly ->
                    /*if (currentEntryMonth.value != yearly.date.month) {
                        MonthSepearator(month = yearly.date.month)
                        currentEntryMonth.value = yearly.date.month
                    }*/
                    if (currentEntryMonth2 != yearly.date.month) {
                        MonthSepearator(month = yearly.date.month)
                        currentEntryMonth2 = yearly.date.month
                    }
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
            }*/
            LazyColumn {
                items(state.monthYearlies) { monthYearlies ->
                    if (monthYearlies.yearlies.isNotEmpty()) {
                        MonthsList(
                            month = monthYearlies.month,
                            yearlies = monthYearlies.yearlies,
                            snackbarHostState = snackbarHostState,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(spacing.spaceLarge),
            onClick = { onNavigatetoCredits() },
            elevation = FloatingActionButtonDefaults.elevation(),
            content = {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = stringResource(id = R.string.main_desc_settings),
                    modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
                )
            }
        )
        LargeFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(spacing.spaceLarge),
            onClick = { onNavigatetoAdd() },
            elevation = FloatingActionButtonDefaults.elevation(),
            content = {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.main_desc_add),
                    modifier = Modifier.size(96.dp)
                )
            }
        )
        when {
            state.monthYearlies.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.main_wow_such_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
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