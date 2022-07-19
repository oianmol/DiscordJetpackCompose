package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.MessageTypography

@Composable
fun MessageItemReplyHeader(
  replyToMessage: String
) {
  ChatListReplyItem(
    modifier = Modifier
      .padding(0.dp),
    replyToMessage = replyToMessage
  )
}

@Composable
fun ReplyLine() {
  Canvas(
    modifier = Modifier
      .size(40.dp)
      .padding(0.dp)
  ) {
    val width = size.width
    val height = size.height
    val path = Path().apply {
      moveTo(
        x = width.times(1.5f),
        y = height.times(.5f)
      )
      cubicTo(
        x1 = width.times(0.55f),
        y1 = height.times(.5f),

        x2 = width.times(.7f),
        y2 = height.times(.5f),

        x3 = width.times(.7f),
        y3 = height.times(1.1f)
      )
    }
    drawPath(
      path = path,
      color = Color.White.copy(alpha = .20f),
      style = Stroke(
        width = 6f,
        cap = StrokeCap.Round,
        join = StrokeJoin.Miter
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
      maxLines = 1,
      overflow = TextOverflow.Clip,
      inlineContent = inlineContentMap,
      style = MessageTypography.h1.copy(
        color = DiscordColorProvider.colors.textPrimary,
        fontSize = 14.sp
      ),
      modifier = Modifier.padding(start = 24.dp, end = 8.dp)
    )
  }
}

@Composable
fun ChatListReplyItem(
  modifier: Modifier,
  replyToMessage: String
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    ReplyLine()
    ChatReplyTitle(
      toUserName = "Person",
      message = replyToMessage
    )
  }
}