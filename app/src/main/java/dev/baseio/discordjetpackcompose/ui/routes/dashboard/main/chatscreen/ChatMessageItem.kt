package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.URL_TAG
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.hashTagPattern
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.mentionTagPattern
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.urlPattern
import dev.baseio.discordjetpackcompose.custom.extractSpans
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.MessageTypography
import dev.baseio.discordjetpackcompose.viewmodels.ChatScreenViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ChatMessageItem(
  message: DiscordMessageEntity,
  position: Int,
  onItemLongPressed: (Int) -> Unit,
  viewModel: ChatScreenViewModel,
  bottomSheetState: ModalBottomSheetState
) {
  // UiState of the ChatScreen
  val uiState by viewModel.uiState.collectAsState()

  val coroutineScope = rememberCoroutineScope()
  Column(
    modifier = Modifier.combinedClickable(
      onClick = {},
      onLongClick = {
        coroutineScope.launch {
          onItemLongPressed(position)
          bottomSheetState.show()
        }
      }
    )
  ) {
    if (message.replyToMessage.isNotEmpty()) {
      MessageItemReplyHeader(
        replyToMessage = message.replyToMessage
      )
    }
    ChatListItem(
      modifier = Modifier
        .padding(0.dp),
      imageSize = 55.dp,
      message = message,
      urlRecognizer = { url ->
        viewModel.fetchUrlMetadata(url)
      }
    )
    uiState.discordUrlMetaEntity?.let { safeDiscordUrlMetaEntity ->
      UrlPreviewItem(
        url = safeDiscordUrlMetaEntity.url,
        imageUrl = safeDiscordUrlMetaEntity.image,
        title = safeDiscordUrlMetaEntity.title,
        desc = safeDiscordUrlMetaEntity.desc
      )
    }
  }
}

@Composable
fun ChatListItem(
  modifier: Modifier,
  imageSize: Dp,
  urlRecognizer: (url: String) -> Unit = {},
  message: DiscordMessageEntity
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(end = 16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    ImageBox(
      Modifier
        .size(imageSize)
        .padding(8.dp),
      imageUrl = "https://images.unsplash.com/photo-1464863979621-258859e62245?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=686&q=80"
    )
    Column(
      horizontalAlignment = Alignment.Start,
      modifier = Modifier.padding(start = 8.dp)
    ) {
      ChatTitle(message)
      Spacer(modifier = Modifier.height(4.dp))
      ChatBody(
        message = message,
        urlRecognizer = urlRecognizer
      )
    }
  }
}

@Composable
fun UrlPreviewItem(
  url: String?,
  imageUrl: String?,
  title: String?,
  desc: String?
) {
  val uriHandler = LocalUriHandler.current
  Box(
    modifier = Modifier
      .padding(top = 2.dp, start = 64.dp, end = 16.dp)
      .clickable { uriHandler.openUri(url ?: "www.google.com") }
      .clip(
        RoundedCornerShape(4.dp)
      )
      .background(
        color = DiscordColorProvider.colors.appBarColor.copy(alpha = 0.8f)
      )
  ) {
    Row(
      modifier = Modifier
        .padding(4.dp)
        .fillMaxSize(),
      verticalAlignment = Alignment.Top
    ) {
      MetadataTitleAndDesc(
        title = title ?: "Some Title",
        desc = desc ?: "Some Description",
        modifier = Modifier.weight(4f)
      )
      imageUrl?.let { safeImageUrl ->
        ImageBox(
          Modifier
            .height(56.dp)
            .padding(8.dp)
            .weight(1f),
          imageUrl = safeImageUrl
        )
      }
    }
  }
}

@Composable
fun MetadataTitleAndDesc(
  title: String,
  desc: String,
  modifier: Modifier
) {
  Column(
    modifier = modifier
  ) {
    Text(
      text = title,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      style = MessageTypography.subtitle2.copy(
        color = DiscordColorProvider.colors.linkColor,
        fontWeight = FontWeight.SemiBold
      ),
      modifier = Modifier.padding(4.dp)
    )
    Text(
      text = desc,
      maxLines = 10,
      overflow = TextOverflow.Ellipsis,
      style = MessageTypography.subtitle2.copy(
        color = DiscordColorProvider.colors.textSecondary
      ),
      modifier = Modifier.padding(4.dp)
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
fun ChatBody(
  message: DiscordMessageEntity,
  urlRecognizer: (url: String) -> Unit = {}
) {
  Column {
    val linksList =
      extractSpans(message.message, listOf(urlPattern, hashTagPattern, mentionTagPattern))
    val uriHandler = LocalUriHandler.current
    val layoutResult = remember {
      mutableStateOf<TextLayoutResult?>(null)
    }
    val annotatedString = buildAnnotatedString {
      append(message.message)
      if (linksList.isNotEmpty() && linksList.first().tag == URL_TAG) {
        urlRecognizer.invoke(linksList.first().spanText)
      }
      linksList.forEach {
        addStyle(
          style = SpanStyle(
            color = DiscordColorProvider.colors.linkColor,
            textDecoration = TextDecoration.Underline
          ),
          start = it.start,
          end = it.end
        )
        addStringAnnotation(
          tag = it.tag,
          annotation = it.spanText,
          start = it.start,
          end = it.end
        )
      }
    }
    Text(
      text = annotatedString,
      style = MessageTypography.subtitle2.copy(
        color = DiscordColorProvider.colors.textSecondary
      ),
      modifier = Modifier.pointerInput(Unit) {
        detectTapGestures { offsetPosition ->
          layoutResult.value?.let {
            val position = it.getOffsetForPosition(offsetPosition)
            annotatedString.getStringAnnotations(position, position).firstOrNull()
              ?.let { result ->
                when (result.tag) {
                  URL_TAG -> {
                    uriHandler.openUri(result.item)
                  }
                }
              }
          }
        }
      },
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
      modifier = Modifier.padding(top = 0.dp, bottom = 0.dp, end = 8.dp)
    )
    Text(
      message.createdDate.calendar().formattedFullDateTime(),
      style = MessageTypography.overline.copy(
        color = DiscordColorProvider.colors.textSecondary.copy(alpha = 0.8f)
      ),
      modifier = Modifier.padding(top = 0.dp, bottom = 0.dp)
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