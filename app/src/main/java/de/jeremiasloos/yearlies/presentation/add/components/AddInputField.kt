package de.jeremiasloos.yearlies.presentation.add.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import de.jeremiasloos.yearlies.ui.theme.LocalSpacing

@Composable
fun AddInputField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    isNumber: Boolean = false
) {
    val thisKeyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text
    val spacing = LocalSpacing.current
    OutlinedTextField(
        value = text,
        label = {
            Text(text = label)
        },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = thisKeyboardType
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
    )
}