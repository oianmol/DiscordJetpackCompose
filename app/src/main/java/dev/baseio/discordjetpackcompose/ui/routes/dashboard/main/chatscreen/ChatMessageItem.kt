package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
  position: Int,
  onItemLongPressed: (Int) -> Unit,
  bottomSheetState: ModalBottomSheetState
) {
  val coroutineScope = rememberCoroutineScope()
  Column {
    if (message.replyToMessage.isNotEmpty()) {
      MessageItemReplyHeader(
        replyToMessage = message.replyToMessage
      )
    }
    ListItem(
      modifier = Modifier
        .padding(0.dp)
        .combinedClickable(
          onClick = {},
          onLongClick = {
            coroutineScope.launch {
              onItemLongPressed(position)
              bottomSheetState.show()
            }
          }
        ),
      icon = {
        ImageBox(
          Modifier.size(40.dp),
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
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageItemReplyHeader(
  replyToMessage: String
) {
  ListItem(
    modifier = Modifier
      .padding(0.dp),
    icon = {
      ReplyLine()
    },
    text = {
      ChatReplyTitle(
        toUserName = "Person",
        message = replyToMessage
      )
    }
  )
}

@Composable
fun ReplyLine() {
  Canvas(
    modifier = Modifier
      .size(50.dp)
      .padding(0.dp)
  ) {
    val width = size.width
    val height = size.height
    val path = Path().apply {
      moveTo(
        x = width.times(1.2f),
        y = height.times(.45f)
      )
      cubicTo(
        x1 = width.times(0f),
        y1 = height.times(.4f),

        x2 = width.times(.35f),
        y2 = height.times(1.5f),

        x3 = width.times(.30f),
        y3 = height.times(1.4f)
      )
    }
    drawPath(
      path = path,
      color = Color.White.copy(alpha = .90f),
      style = Stroke(
        width = 2f,
        join = StrokeJoin.Miter
      )
    )
  }
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
    modifier = modifier
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

@Composable
fun ChatReplyTitle(
  toUserName: String,
  message: String
) {
  val annotatedString = buildAnnotatedString {
    appendInlineContent(id = "imageId")
    pushStyle(
      SpanStyle(
        fontWeight = FontWeight.Medium,
        color = DiscordColorProvider.colors.textPrimary
      )
    )
    append("   $toUserName   ")
    pushStyle(
      SpanStyle(
        fontWeight = FontWeight.Normal,
        color = DiscordColorProvider.colors.textSecondary.copy(alpha = 0.8f)
      )
    )
    append(message)
  }
  val inlineContentMap = mapOf(
    "imageId" to InlineTextContent(
      Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
    ) {
      ImageBox(
        modifier = Modifier.size(32.dp),
        imageUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80"
      )
    }
  )
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(
      text = annotatedString,
      inlineContent = inlineContentMap,
      style = MessageTypography.h1.copy(
        color = DiscordColorProvider.colors.textPrimary,
        fontSize = 14.sp
      ),
      modifier = Modifier.padding(end = 8.dp)
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