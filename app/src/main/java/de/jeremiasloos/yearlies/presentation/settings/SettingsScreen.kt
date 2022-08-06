package de.jeremiasloos.yearlies.presentation.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.jeremiasloos.yearlies.R
import de.jeremiasloos.yearlies.ui.theme.LocalSpacing
import de.jeremiasloos.yearlies.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    snackbarHostState: SnackbarHostState,
    onNavigateUp: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val selected = viewModel.notificationsInterval
    LaunchedEffect(key1 = true) {
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
            .padding(spacing.spaceSmall)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FilledTonalButton(
                        onClick = { onNavigateUp() },
                        content = {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.main_desc_settings)
                            )
                        },
                        modifier = Modifier
                            .weight(2f)
                            .padding(end = spacing.spaceSmall)
                    )
                    Text(
                        text = stringResource(id = R.string.settings_heading),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.weight(6f),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = {
                            viewModel.onEvent(
                                SettingsEvent.OnIntervalSave
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
                Spacer(modifier = Modifier.height(spacing.spaceLarge))

            }
            item {
                Text(text = stringResource(id = R.string.settings_interval_text))
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(3.dp))
                            .border(1.dp, MaterialTheme.colorScheme.onBackground)
                            //.background(MaterialTheme.colorScheme.inversePrimary)
                            .clickable { viewModel.onIntervalClick("off") }
                    ) {
                        RadioButton(
                            selected = selected == "off",
                            onClick = { viewModel.onIntervalClick("off") })
                        Text(text = stringResource(id = R.string.settings_interval_off))
                    }
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(3.dp))
                            .border(1.dp, MaterialTheme.colorScheme.onSurface)
                            //.background(MaterialTheme.colorScheme.inversePrimary)
                            .clickable { viewModel.onIntervalClick("daily") }
                    ) {
                        RadioButton(
                            selected = selected == "daily",
                            onClick = { viewModel.onIntervalClick("daily") })
                        Text(text = stringResource(id = R.string.settings_interval_daily))
                    }
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(3.dp))
                            .border(1.dp, MaterialTheme.colorScheme.onBackground)
                            //.background(MaterialTheme.colorScheme.inversePrimary)
                            .clickable { viewModel.onIntervalClick("weekly") }
                    ) {
                        RadioButton(
                            selected = selected == "weekly",
                            onClick = { viewModel.onIntervalClick("weekly") })
                        Text(text = stringResource(id = R.string.settings_interval_weekly))
                    }
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(3.dp))
                            .border(1.dp, MaterialTheme.colorScheme.onBackground)
                            //.background(MaterialTheme.colorScheme.inversePrimary)
                            .clickable { viewModel.onIntervalClick("monthly") }
                    ) {
                        RadioButton(
                            selected = selected == "monthly",
                            onClick = { viewModel.onIntervalClick("monthly") })
                        Text(text = stringResource(id = R.string.settings_interval_monthly))
                    }
                }
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
//                Divider()
                //              Spacer(modifier = Modifier.height(spacing.spaceMedium))

            }
            item {
                Text(
                    text = stringResource(id = R.string.settings_notifications_not_working_yet),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Divider()
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Text(
                    text = stringResource(id = R.string.settings_text),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(spacing.spaceLarge))
                Text(
                    text = stringResource(id = R.string.settings_thanks),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )

            }
        }
    }
    /*Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(spacing.spaceSmall),
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
                .align(Alignment.BottomEnd)
                .padding(spacing.spaceSmall),
            onClick = {
                viewModel.onEvent(
                    SettingsEvent.OnIntervalSave
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