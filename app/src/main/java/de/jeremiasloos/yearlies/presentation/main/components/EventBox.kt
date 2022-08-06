package de.jeremiasloos.yearlies.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import de.jeremiasloos.yearlies.R
import de.jeremiasloos.yearlies.ui.theme.LocalSpacing
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EventBox(
    date: LocalDate,
    name: String,
    note: String,
    onDeleteClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider()
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                //text = date.toString(),
                text = date.format(DateTimeFormatter.ofPattern(stringResource(id = R.string.main_date_formatter))),
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.main_desc_delete),
                modifier = Modifier
                    .clickable {
                        onDeleteClick()
                    }
            )
        }
        //Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge
        )
        if (note != "") {
            //Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Text(
                text = note,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
    }
}