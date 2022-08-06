package de.jeremiasloos.yearlies.presentation.main.components

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import de.jeremiasloos.yearlies.R
import de.jeremiasloos.yearlies.domain.model.YearlyEvent
import de.jeremiasloos.yearlies.presentation.main.MainEvent
import de.jeremiasloos.yearlies.presentation.main.MainViewModel

@Composable
fun DeleteAlertDialogComponent(
    snackbarHostState: SnackbarHostState,
    viewModel: MainViewModel,
    yearly: YearlyEvent,
    onClose: (Boolean) -> Unit
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = {
            onClose(true)
        },
        title = { Text(text = stringResource(R.string.main_alert_title)) },
        text = {
            Text(
                text = stringResource(
                    R.string.main_alert_text,
                    yearly.name
                )
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onClose(true)
                    viewModel.confirmClick(yearly.name)
                    viewModel.onEvent(
                        MainEvent
                            .OnDeleteYearly(yearly)
                    )
                }
            ) {
                Text(stringResource(R.string.main_alert_confirm_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onClose(true)
                    //viewModel.cancelClick()
                }
            ) {
                Text(stringResource(R.string.main_alert_cancel_button))
            }
        }
    )
}