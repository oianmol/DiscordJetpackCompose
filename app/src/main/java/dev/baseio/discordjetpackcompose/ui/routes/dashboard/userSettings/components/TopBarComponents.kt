package dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.baseio.discordjetpackcompose.R.string
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.OnlineIndicator
import dev.baseio.discordjetpackcompose.ui.theme.Typography
import dev.baseio.discordjetpackcompose.ui.theme.user_settings_bg
import dev.baseio.discordjetpackcompose.ui.utils.rememberCoilImageRequest

@Composable
fun GetTopComponent(
  lazyListState: LazyListState,
  profileImageUrl: String,
  username: String,
  discordTag: String
) {
  var scrolledY = 0f
  var previousOffset = 0

  Column(
    Modifier
      .fillMaxWidth()
      .background(user_settings_bg)
      .graphicsLayer {
        scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
        translationY = scrolledY * 0.5f
        previousOffset = lazyListState.firstVisibleItemScrollOffset
      }, horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top
  ) {
    GetImageWithBanner(profileImageUrl)
    GetUsername(username,discordTag)
    Spacer(modifier = Modifier.height(16.dp))
  }
}

@Composable
fun GetImageWithBanner(profileImageUrl: String) {
  Spacer(
    modifier = Modifier
      .height(40.dp)
      .fillMaxWidth()
      .background(Color(0xFF00a6d5))
  )
  Box(
    Modifier
      .fillMaxWidth()
      .wrapContentHeight()
  ) {
    Spacer(
      modifier = Modifier
        .height(40.dp)
        .fillMaxWidth()
        .background(Color(0xFF00a6d5))
    )
    Box(
      modifier = Modifier
        .padding(start = 16.dp)
        .fillMaxWidth()
        .wrapContentHeight()
    ) {
      OnlineIndicator(
        isOnline = true,
        indicatorSize = 26.dp
      ) {
        AsyncImage(
          model = rememberCoilImageRequest(data = profileImageUrl),
          contentDescription = stringResource(string.settings_profile_image),
          modifier = Modifier
            .size(84.dp)
            .clip(CircleShape)
            .border(4.dp, user_settings_bg, CircleShape)
        )
      }
    }
  }
}

@Composable
fun GetUsername( username: String,
  discordTag: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 16.dp, top = 8.dp)
  ) {
    Text(
      text = username,
      style = Typography.h3.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
      ),
      textAlign = TextAlign.Start
    )
    Text(
      text = discordTag,
      style = Typography.h3.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        color = Color.Gray
      ),
      textAlign = TextAlign.Start
    )
  }
}