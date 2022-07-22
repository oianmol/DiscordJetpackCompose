package dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.models.NotificationSettingsType

@Composable
fun NotificationsMuteSection(
  isMute: Boolean,
  name: String?,
  screenType: NotificationSettingsType,
  onMuteChange: () -> Unit
) {

  MuteUnMuteToggle(isMute = isMute, name = name) { onMuteChange() }

  if (isMute) {
    Text(
      text = buildAnnotatedString {
        append(stringResource(id = R.string.muted_msg))
        append(" " + stringResource(id = typeMessageMuted(screenType = screenType)))
      },
      style = TextStyle(color = Color.LightGray, fontSize = 12.sp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp, horizontal = 12.dp)
    )
  }

  val messageRes = typeMessageForScreenType(screenType)

  Text(
    text = stringResource(id = messageRes),
    style = TextStyle(color = Color.LightGray, fontSize = 12.sp),
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp, vertical = 22.dp)
  )
}

@Composable
fun MuteUnMuteToggle(
  isMute: Boolean,
  name: String?,
  onMuteChange: () -> Unit
) {
  val spannedString = muteSectionTitle(isMute, name)

  SectionItem(
    onClick = { onMuteChange() },
    paddingHorizontal = 12.dp,
    paddingVertical = 8.dp,
    leadingComposable = {
      Text(text = spannedString)
    },
    trailingComposable = {
      Icon(
        imageVector = Icons.Filled.ChevronRight,
        contentDescription = null,
        modifier = Modifier.padding(start = 8.dp),
      )
    }
  )



}

@Composable
private fun typeMessageForScreenType(screenType: NotificationSettingsType): Int {
  val messageRes = when (screenType) {
    NotificationSettingsType.SERVER -> R.string.server_mute_msg
    NotificationSettingsType.CATEGORY -> R.string.category_mute_msg
    NotificationSettingsType.CHANNEL -> R.string.channel_mute_msg
  }
  return messageRes
}

@Composable
private fun typeMessageMuted(screenType: NotificationSettingsType): Int {
  val messageRes = when (screenType) {
    NotificationSettingsType.SERVER -> R.string.server
    NotificationSettingsType.CATEGORY -> R.string.category
    NotificationSettingsType.CHANNEL -> R.string.channel
  }
  return messageRes
}

@Composable
private fun muteSectionTitle(
  isMute: Boolean,
  name: String?
): AnnotatedString {
  val spannedString = buildAnnotatedString {
    withStyle(style = SpanStyle(fontSize = 14.sp)) {
      append(
        if (isMute) stringResource(id = R.string.unmute)
        else stringResource(id = R.string.mute)
      )
    }
    name?.let {
      withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)) {
        append(" $name")
      }
    }
  }
  return spannedString
}