package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.MessageTypography
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatMessageItem(message: DiscordMessageEntity) {
  ListItem(
    icon = {
      ImageBox(
        Modifier.size(48.dp),
        imageUrl = "https://images.unsplash.com/photo-1464863979621-258859e62245?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=686&q=80"
      )
    },
    modifier = Modifier.padding(2.dp),
    secondaryText = {
      ChatMedia(message)
    },
    text = {
      ChatUserDateTime(message)
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
    painter = painter,
    contentDescription = null,
    modifier = modifier.clip(CircleShape)
  )
}

@Composable
fun ChatMedia(message: DiscordMessageEntity) {
  Column {
    Text(
      message.message,
      style = MessageTypography.subtitle2.copy(
        color = DiscordColorProvider.colors.textSecondary
      ),
      modifier = Modifier.padding(4.dp)
    )
  }
}

@Composable
fun ChatUserDateTime(message: DiscordMessageEntity) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(
      message.createdBy,
      style = MessageTypography.h1.copy(
        color = DiscordColorProvider.colors.textPrimary
      ),
      modifier = Modifier.padding(4.dp)
    )
    Text(
      message.createdDate.calendar().formattedTime(),
      style = MessageTypography.overline.copy(
        color = DiscordColorProvider.colors.textSecondary.copy(alpha = 0.8f)
      ), modifier = Modifier.padding(4.dp)
    )
  }
}

@SuppressLint("SimpleDateFormat")
fun Calendar.formattedTime(): String {
  return SimpleDateFormat("hh:mm a").format(this.time)
}

fun Long.calendar(): Calendar {
  return Calendar.getInstance().apply {
    this.timeInMillis = this@calendar
  }
}