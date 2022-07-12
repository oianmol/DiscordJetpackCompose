package dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.register

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.contentColorFor
import dev.baseio.discordjetpackcompose.ui.theme.text_field_cursor_color

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isEnabled: Boolean = true,
    focusRequester: FocusRequester = remember { FocusRequester() },
    isPasswordTextFieldProvider: () -> Boolean = { false },
    isCountryCodeTextFieldProvider: () -> Boolean = { false },
    isPhoneNumberTextFieldProvider: () -> Boolean = { false },
    trailingIconOnClick: () -> Unit = { }
) {
    val isPasswordVisible = remember {
        mutableStateOf(false)
    }
    TextField(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() }
            .focusRequester(focusRequester),
        shape = RoundedCornerShape(4.dp),
        enabled = isEnabled,
        colors = textFieldColors(),
        value = value,
        onValueChange = onValueChange,
        visualTransformation = if (isPasswordTextFieldProvider()) {
            if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = if (isPasswordTextFieldProvider()) {
                KeyboardType.Password
            } else if (isPhoneNumberTextFieldProvider()) {
                KeyboardType.Phone
            } else if (isCountryCodeTextFieldProvider()) {
                KeyboardType.Text
            } else {
                KeyboardType.Email
            }
        ),
        trailingIcon = {
            if (isPasswordTextFieldProvider()) {
                TextFieldTrailingIcon(
                    iconId = if (isPasswordVisible.value) {
                        R.drawable.ic_show_password
                    } else {
                        R.drawable.ic_hide_password
                    },
                    onClick = { isPasswordVisible.value = !isPasswordVisible.value }
                )
            } else if (isCountryCodeTextFieldProvider()) {
                TextFieldTrailingIcon(iconSize = 0.dp)
            } else if (value.isNotEmpty() || value != "") {
                TextFieldTrailingIcon(
                    iconId = R.drawable.ic_outline_cancel,
                    onClick = { trailingIconOnClick() })
            }
        },
        singleLine = true,
        label = { Text(label, color = contentColor()) }
    )
}

@Composable
fun CountryPickerTextField(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isEnabled: Boolean = true,
    focusRequester: FocusRequester = remember { FocusRequester() }
) {
    TextField(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() }
            .focusRequester(focusRequester),
        shape = RoundedCornerShape(4.dp),
        enabled = isEnabled,
        colors = textFieldColors(),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(label, color = contentColor()) }
    )
}

@Composable
fun TextFieldTrailingIcon(
    @DrawableRes iconId: Int? = null,
    iconSize: Dp = 24.dp,
    color: Color = DiscordColorProvider.colors.textFieldContentColor,
    onClick: () -> Unit = {}
) {
    iconId?.let {
        IconButton(onClick = { onClick() }) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
private fun contentColor() =
    DiscordColorProvider.colors.contentColorFor(DiscordColorProvider.colors.secondaryBackground)

@Composable
private fun textFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = DiscordColorProvider.colors.textFieldContentColor,
    disabledTextColor = contentColor(),
    cursorColor = text_field_cursor_color,
    backgroundColor = DiscordColorProvider.colors.secondaryBackground,
    focusedIndicatorColor = Color.Transparent, // hide the indicator
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    placeholderColor = contentColor()
)