package de.jeremiasloos.yearlies.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.jeremiasloos.yearlies.ui.theme.LocalSpacing
import java.time.Month
import java.time.format.TextStyle

@Composable
fun MonthSepearator(month: Month) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary),

        ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = spacing.spaceSmall),
            text = month.getDisplayName(TextStyle.FULL, java.util.Locale.getDefault()),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}