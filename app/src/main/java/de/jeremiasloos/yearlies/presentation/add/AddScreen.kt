package de.jeremiasloos.yearlies.presentation.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.jeremiasloos.yearlies.R
import de.jeremiasloos.yearlies.presentation.add.components.AddInputField
import de.jeremiasloos.yearlies.ui.theme.LocalSpacing
import de.jeremiasloos.yearlies.util.UiEvent

@ExperimentalComposeUiApi
@Composable
fun AddScreen(
    snackbarHostState: SnackbarHostState,
    onNavigateUp: () -> Unit,
    viewModel: AddViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val day = viewModel.day
    val month = viewModel.month
    val year = viewModel.year
    val name = viewModel.name
    val note = viewModel.note
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateUp -> onNavigateUp()
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
            //.background(MaterialTheme.colorScheme.primary)
            .padding(spacing.spaceSmall)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FilledTonalButton(
                        onClick = {
                            onNavigateUp()
                        },
                        content = {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.main_desc_settings)
                            )
                        },
                        modifier = Modifier
                            .weight(2f)
                            .padding(end = spacing.spaceSmall),
                        //shape = RoundedCornerShape(10.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.add_heading),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.weight(6f),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = {
                            viewModel.onEvent(
                                AddEvent.OnSaveClick
                            )
                        },
                        content = {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = stringResource(id = R.string.add_desc_check)
                            )
                        },
                        modifier = Modifier
                            .weight(2f)
                            .padding(start = spacing.spaceSmall)
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.add_day),
                                style = MaterialTheme.typography.titleMedium
                            )
                            AddInputField(
                                text = day,
                                onValueChange = { viewModel.onDayEnter(it) },
                                label = stringResource(id = R.string.add_day),
                                isNumber = true
                            )
                        }
                        Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.add_month),
                                style = MaterialTheme.typography.titleMedium
                            )
                            AddInputField(
                                text = month,
                                onValueChange = { viewModel.onMonthEnter(it) },
                                label = stringResource(id = R.string.add_month),
                                isNumber = true
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_year),
                            style = MaterialTheme.typography.titleMedium
                        )
                        AddInputField(
                            text = year,
                            onValueChange = { viewModel.onYearEnter(it) },
                            label = stringResource(id = R.string.add_year),
                            isNumber = true
                        )
                    }
                }
                Spacer(modifier = Modifier.height(spacing.spaceLarge))
                Column {
                    Text(
                        text = stringResource(id = R.string.add_name),
                        style = MaterialTheme.typography.titleMedium
                    )
                    AddInputField(
                        text = name,
                        onValueChange = { viewModel.onNameEnter(it) },
                        label = stringResource(id = R.string.add_name)
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceLarge))
                Column {
                    Text(
                        text = stringResource(id = R.string.add_note),
                        style = MaterialTheme.typography.titleMedium
                    )
                    AddInputField(
                        text = note,
                        onValueChange = { viewModel.onNoteEnter(it) },
                        label = stringResource(id = R.string.add_note)
                    )
                }
            }
        }
    }
    /*Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(spacing.spaceLarge),
            onClick = { onNavigateUp() },
            elevation = FloatingActionButtonDefaults.elevation(),
            content = {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.main_desc_settings),
                    modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
                )
            }
        )
        LargeFloatingActionButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(spacing.spaceLarge),
            onClick = {
                viewModel.onEvent(
                    AddEvent.OnSaveClick
                )
            },
            elevation = FloatingActionButtonDefaults.elevation(),
            content = {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.add_desc_check),
                    modifier = Modifier.size(96.dp)
                )
            }
        )
    }*/
}