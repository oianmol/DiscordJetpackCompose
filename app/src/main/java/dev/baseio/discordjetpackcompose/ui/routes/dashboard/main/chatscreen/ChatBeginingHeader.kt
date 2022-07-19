package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.baseio.discordjetpackcompose.R.drawable
import dev.baseio.discordjetpackcompose.R.raw
import dev.baseio.discordjetpackcompose.R.string
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.commonui.OnboardingScreensButton
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.MessageTypography

@Composable
fun ChatBeginningHeader(
  modifier: Modifier = Modifier,
  lastDrawnMessage: String?,
  userName: State<String>
) {
  Column(modifier = modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 48.dp)) {
    ProfilePhoto()
    Text(
      modifier = Modifier
        .padding(start = 4.dp, top = 16.dp)
        .fillMaxWidth(),
      maxLines = 1,
      style = MessageTypography.h2,
      overflow = TextOverflow.Ellipsis,
      text = userName.value,
      color = DiscordColorProvider.colors.onSurface
    )
    Text(
      modifier = Modifier
        .padding(start = 4.dp, top = 8.dp)
        .fillMaxHeight(),
      style = MessageTypography.caption,
      text = "This is the beginning of your direct message history with @${userName.value}",
      color = DiscordColorProvider.colors.textSecondary
    )
    AnimatedVisibility(
      visible = lastDrawnMessage.isNullOrBlank(),
      exit = fadeOut(
        animationSpec = TweenSpec(800, 300, FastOutSlowInEasing)
      )
    ) {
      val composition by rememberLottieComposition(
        RawRes(raw.discord_wave_sticker)
      )
      Column(modifier = modifier.align(Alignment.CenterHorizontally)) {
        LottieAnimation(
          modifier = Modifier
            .padding(top = 32.dp)
            .size(160.dp)
            .align(Alignment.CenterHorizontally),
          iterations = LottieConstants.IterateForever,
          composition = composition
        )
        OnboardingScreensButton(
          modifier = Modifier
            .padding(start = 8.dp, top = 16.dp, end = 8.dp)
            .fillMaxWidth()
            .height(38.dp)
            .align(Alignment.CenterHorizontally),
          buttonTextProvider = { string.wave_to },
          onClick = { }
        )
      }
    }
  }
}

@Composable
private fun ProfilePhoto(
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .size(80.dp)
      .padding(0.dp)
      .background(
        color = Color(0xFFec4245),
        shape = CircleShape
      )
  ) {
    Icon(
      modifier = Modifier
        .align(Alignment.Center)
        .size(48.dp),
      painter = painterResource(id = drawable.ic_discord_icon),
      contentDescription = null,
      tint = DiscordColorProvider.colors.brand
    )
  }
}