package dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.ui.utils.Strings

@Composable
fun RegistrationTypeSelector(
    modifier: Modifier,
    selectedOption: RegistrationType,
    onSelectionChange: (RegistrationType) -> Unit
) {
    Row(
        modifier = modifier
          .height(32.dp)
          .background(
            color = DiscordColorProvider.colors.tabsBackgroundColor,
            shape = RoundedCornerShape(4.dp)
          )
          .fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
              .weight(1f)
              .fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = bgColor(selectedOption == RegistrationType.Phone),
                contentColor = contentColor(selectedOption == RegistrationType.Phone)
            ),
            onClick = { onSelectionChange(RegistrationType.Phone) },
        ) {
            Text(stringResource(id = Strings.phone))
        }

        Button(
            modifier = Modifier
              .weight(1f)
              .fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = bgColor(selected = selectedOption == RegistrationType.Email),
                contentColor = contentColor(selected = selectedOption == RegistrationType.Email)
            ),
            onClick = { onSelectionChange(RegistrationType.Email) },
        ) {
            Text(stringResource(id = Strings.email))
        }
    }
}

@Composable
private fun contentColor(selected: Boolean) =
    if (selected) {
        Color(0xFFFFFFFF)
    } else {
        Color(0xFF55555d)
    }

@Composable
private fun bgColor(selected: Boolean) =
    if (selected) {
        DiscordColorProvider.colors.tabSelectedColor
    } else {
        DiscordColorProvider.colors.tabsBackgroundColor
    }

sealed class RegistrationType {
    object Phone : RegistrationType()
    object Email : RegistrationType()
}