package dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.components

import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.DiscordSwitchColors

@Composable
fun MentionsSection(
  isMute: Boolean
) {
  var suppressEveryoneMentions by remember { mutableStateOf(false) }
  var suppressRoleMentions by remember { mutableStateOf(false) }
  var suppressPush by remember { mutableStateOf(false) }

  SwitchItem(
    onClick = {
      suppressEveryoneMentions = !suppressEveryoneMentions
    },
    title = buildAnnotatedString {
      append(stringResource(id = R.string.suppress))
      withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
        append(" "+stringResource(id = R.string.at_everyone))
      }
      append(" "+stringResource(id = R.string.and))
      withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
        append(" "+stringResource(id = R.string.at_here))
      }
    },
    disabled = false,
    isChecked = suppressEveryoneMentions
  )
  SwitchItem(
    onClick = {
      suppressRoleMentions = !suppressRoleMentions
    },
    title = buildAnnotatedString { append(stringResource(id = R.string.role_mentions)) },
    disabled = false,
    isChecked = suppressRoleMentions
  )
  SwitchItem(
    onClick = {
      if (isMute) return@SwitchItem
      suppressPush = !suppressPush
    },
    title = buildAnnotatedString { append(stringResource(id = R.string.mobile_push)) },
    disabled = isMute,
    isChecked = suppressPush
  )
}

@Composable
fun SwitchItem(
  onClick: () -> Unit,
  title: AnnotatedString,
  disabled: Boolean,
  isChecked: Boolean
) {
  SectionItem(
    disabled = disabled,
    onClick = onClick,
    leadingComposable = {
      Text(
        text = title,
        color = Color.LightGray
      )
    },
    trailingComposable = {
      Switch(
        checked = isChecked,
        onCheckedChange = { onClick() },
        colors = DiscordSwitchColors
      )
    }
  )
}