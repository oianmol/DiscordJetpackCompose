package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.MessageTypography
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ChatMessageItem(
  message: DiscordMessageEntity,
  bottomSheetState: ModalBottomSheetState
) {
  val coroutineScope = rememberCoroutineScope()
  ListItem(
    modifier = Modifier
      .padding(0.dp)
      .combinedClickable(
        onClick = {},
        onLongClick = {
          coroutineScope.launch {
            bottomSheetState.show()
          }
        }
      ),
    icon = {
      ImageBox(
        Modifier.size(48.dp),
        imageUrl = "https://images.unsplash.com/photo-1464863979621-258859e62245?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=686&q=80"
      )
    },
    text = {
      ChatTitle(message)
    },
    secondaryText = {
      ChatBody(message)
    }
  )
}

@Composable
fun ImageBox(
  modifier: Modifier,
  imageUrl: String
) {
  val painter = rememberAsyncImagePainter(
    ImageRequest.Builder(LocalContext.current).data(imageUrl).apply {
      transformations(RoundedCornersTransformation(12.0f, 12.0f, 12.0f, 12.0f))
    }.build()
  )
  Image(
    modifier = Modifier
      .size(40.dp)
      .clip(CircleShape),
    painter = painter,
    contentDescription = null
  )
}

@Composable
fun ChatBody(message: DiscordMessageEntity) {
  Column {
    Text(
      message.message,
      style = MessageTypography.subtitle2.copy(
        color = DiscordColorProvider.colors.textSecondary
      )
    )
  }
}

@Composable
fun ChatTitle(message: DiscordMessageEntity) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(
      message.createdBy,
      style = MessageTypography.h1.copy(
        color = DiscordColorProvider.colors.textPrimary
      ),
      modifier = Modifier.padding(end = 8.dp)
    )
    Text(
      message.createdDate.calendar().formattedFullDateTime(),
      style = MessageTypography.overline.copy(
        color = DiscordColorProvider.colors.textSecondary.copy(alpha = 0.8f)
      )
    )
  }
}

@SuppressLint("SimpleDateFormat")
fun Calendar.formattedFullDateTime(): String {
  return SimpleDateFormat("EEE, d MMM yyyy hh:mm a").format(this.time)
}

@SuppressLint("SimpleDateFormat")
fun Calendar.formattedFullDate(): String {
  return SimpleDateFormat("EEE, d MMM yyyy").format(this.time)
}

fun Long.calendar(): Calendar {
  return Calendar.getInstance().apply {
    this.timeInMillis = this@calendar
  }
}