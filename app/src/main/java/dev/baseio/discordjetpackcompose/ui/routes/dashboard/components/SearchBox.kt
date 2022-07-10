package dev.baseio.discordjetpackcompose.ui.routes.dashboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(currentValue: String, onValueChanged: (String) -> Unit) {
    val keyboardManager = LocalSoftwareKeyboardController.current
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(DiscordColorProvider.colors.onSurface.copy(alpha = 0.05f))
            .testTag("countryPickerSearchBox"),
        value = currentValue,
        onValueChange = onValueChanged,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = DiscordColorProvider.colors.onSurface.copy(alpha = 0.05f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = DiscordColorProvider.colors.onSurface.copy(alpha = ContentAlpha.disabled)
            )
        },
        placeholder = { Text(text = stringResource(id = R.string.country_picker_hint)) },
        trailingIcon = {
            AnimatedVisibility(visible = currentValue.isNotBlank()) {
                IconButton(onClick = { onValueChanged("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "countryPickerSearchBoxTrailingIcon"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { keyboardManager?.hide() }),
        shape = RoundedCornerShape(6.dp)
    )
}