package dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.components

import android.icu.text.CaseMap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.models.FrequencyType
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.models.OverrideItem
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.models.OverrideType
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider

@Composable
fun NotificationOverridesList() {
  NotificationOverridesAdd()

  NotificationOverridesItems()
}

@Composable
fun NotificationOverridesAdd() {

  SectionItem(
    onClick = {},
    paddingVertical = 10.dp,
    leadingComposable = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Filled.Add,
          contentDescription = null,
          modifier = Modifier.padding(end = 8.dp),
        )
        Text(text = stringResource(id = R.string.add_msg))
      }

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
fun NotificationOverridesItems() {

  val listOverrides = remember {
    mutableStateListOf(
      OverrideItem(
        title = "Android",
        subtitle = null,
        type = OverrideType.CATEGORY,
        frequencyType = FrequencyType.ALL_MESSAGES
      ),
      OverrideItem(
        title = "welcome",
        subtitle = "welcome",
        type = OverrideType.CHANNEL,
        frequencyType = FrequencyType.MENTIONS
      ),
      OverrideItem(
        title = "Other",
        subtitle = null,
        type = OverrideType.CATEGORY,
        frequencyType = FrequencyType.NOTHING
      ),
      OverrideItem(
        title = "ui-ux-designs",
        subtitle = "Android",
        type = OverrideType.CHANNEL,
        frequencyType = FrequencyType.MENTIONS
      ),
      OverrideItem(
        title = "app-showcase",
        subtitle = "Android",
        type = OverrideType.CHANNEL,
        frequencyType = FrequencyType.MENTIONS
      ),
    )
  }

  listOverrides.forEach { overrideItem ->
    NotificationOverridesItem(
      overrideItem = overrideItem,
      onDismiss = {
        listOverrides.remove(overrideItem)
      }
    )
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationOverridesItem(
  overrideItem: OverrideItem,
  onDismiss: () -> Unit
) {
  val state = DismissState(
    initialValue = DismissValue.Default,
    confirmStateChange = {
      if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
        onDismiss()
        false
      }
      true
    }
  )

  SwipeToDismiss(
    state = state, background = {
      val color = when (state.dismissDirection) {
        DismissDirection.EndToStart -> Color.Red
        DismissDirection.StartToEnd -> Color.Red
        else -> Color.Transparent
      }
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(color = color)
          .padding(10.dp)
      )
      {
        Icon(
          imageVector = Icons.Default.Delete,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.align(
            if (state.dismissDirection == DismissDirection.StartToEnd) Alignment.CenterStart else Alignment.CenterEnd
          )
        )
      }
    },
    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart)
  ) {
    SectionItem(
      onClick = {},
      color = DiscordColorProvider.colors.background,
      paddingVertical = 10.dp,
      leadingComposable = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = if(overrideItem.type == OverrideType.CHANNEL) Icons.Filled.HealthAndSafety else Icons.Filled.SystemUpdate,
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 4.dp),
          )
          Column(verticalArrangement = Arrangement.Center) {
            Text(text = overrideItem.title, style = TextStyle(fontSize = 14.sp))
            overrideItem.subtitle?.let {
              Text(text = it, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold))
            }
          }
        }
      },
      trailingComposable = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = getTextForFrequency(type = overrideItem.frequencyType))
          Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp),
          )
        }
      }
    )
  }
}

@Composable
fun getTextForFrequency(type: FrequencyType): AnnotatedString {
  return when (type) {
    FrequencyType.ALL_MESSAGES -> AnnotatedString(stringResource(id = R.string.all_messages))
    FrequencyType.NOTHING -> AnnotatedString(stringResource(id = R.string.nothing))
    FrequencyType.MENTIONS -> buildAnnotatedString {
      append(stringResource(id = R.string.only))
      withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
        append(" " + stringResource(id = R.string.at_mentions))
      }
    }
  }
}