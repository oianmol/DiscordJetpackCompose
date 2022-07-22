package dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.components

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.models.FrequencyType
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider

@Composable
fun NotificationFrequencySection(
  isMute: Boolean
) {
  var selectionState by remember { mutableStateOf(FrequencyType.ALL_MESSAGES) }

  RadioSelectionItem(
    title = AnnotatedString(stringResource(id = R.string.all_messages)),
    onClick = {
      if(isMute) return@RadioSelectionItem
      selectionState = FrequencyType.ALL_MESSAGES
    },
    currentSelection = selectionState,
    selectionValue = FrequencyType.ALL_MESSAGES,
    disabled = isMute
  )

  RadioSelectionItem(
    title = buildAnnotatedString {
      append(stringResource(id = R.string.only))
      withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
        append(" " + stringResource(id = R.string.at_mentions))
      }
    },
    onClick = {
      if(isMute) return@RadioSelectionItem
      selectionState = FrequencyType.MENTIONS
    },
    currentSelection = selectionState,
    selectionValue = FrequencyType.MENTIONS,
    disabled = isMute
  )

  RadioSelectionItem(
    title = AnnotatedString(stringResource(id = R.string.nothing)),
    onClick = {
      if(isMute) return@RadioSelectionItem
      selectionState = FrequencyType.NOTHING
    },
    currentSelection = selectionState,
    selectionValue = FrequencyType.NOTHING,
    disabled = isMute
  )

}

@Composable
fun RadioSelectionItem(
  onClick: () -> Unit,
  title: AnnotatedString,
  currentSelection: FrequencyType,
  selectionValue: FrequencyType,
  disabled: Boolean
) {
  SectionItem(
    disabled = disabled,
    paddingVertical = 4.dp,
    onClick = onClick,
    leadingComposable = {
      Text(
        text = title,
        color = Color.LightGray
      )
    },
    trailingComposable = {
      RadioButton(
        selected = currentSelection == selectionValue,
        onClick = onClick,
        colors = DiscordRadioButtonColors
      )
    }
  )
}

val DiscordRadioButtonColors @Composable get() = RadioButtonDefaults.colors(
  selectedColor = DiscordColorProvider.colors.primary,
  unselectedColor = DiscordColorProvider.colors.surface
)

