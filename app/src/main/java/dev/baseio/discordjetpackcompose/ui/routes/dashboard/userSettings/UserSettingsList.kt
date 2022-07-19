package dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TabRowDefaults.Divider
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
import com.google.accompanist.insets.navigationBarsPadding
import dev.baseio.discordjetpackcompose.R.string
import dev.baseio.discordjetpackcompose.entities.SettingsEntity
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.OnlineIndicator
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider.colors
import dev.baseio.discordjetpackcompose.ui.theme.Typography
import dev.baseio.discordjetpackcompose.ui.theme.user_settings_bg
import dev.baseio.discordjetpackcompose.ui.theme.user_settings_text
import dev.baseio.discordjetpackcompose.ui.utils.rememberCoilImageRequest
import dev.baseio.discordjetpackcompose.utils.Constants

@Composable
fun UserSettingsList(
  userSettings: List<SettingsEntity>,
  nitroSettings: List<SettingsEntity>,
  appSettings: List<SettingsEntity>,
  appInfo: List<SettingsEntity>
) {
  val lazyListState = rememberLazyListState()
  var scrolledY = 0f
  var previousOffset = 0
  LazyColumn(state = lazyListState, modifier = Modifier.background(colors.settingsBackground)) {
    item {
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
        Box(
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
          Row(
            modifier = Modifier
              .height(32.dp)
              .fillMaxWidth()
              .background(Color(0xFF00a6d5))
          ) {
          }
          Column(
            modifier = Modifier
              .padding(start = 16.dp)
              .fillMaxWidth()
              .wrapContentHeight()
          ) {
            OnlineIndicator(
              isOnline = true,
              indicatorSize = 22.dp
            ) {
              AsyncImage(
                model = rememberCoilImageRequest(data = Constants.MMLogoUrl),
                contentDescription = stringResource(string.settings_profile_image),
                modifier = Modifier
                  .size(72.dp)
                  .clip(CircleShape)
                  .border(4.dp, user_settings_bg, CircleShape)
              )
            }
          }
        }
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp)
        ) {
          Text(
            text = "mutual",
            style = Typography.h3.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 20.sp
            ),
            textAlign = TextAlign.Start
          )
          Text(
            text = "#1234",
            style = Typography.h3.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 20.sp,
              color = Color.Gray
            ),
            textAlign = TextAlign.Start
          )
        }
        Spacer(modifier = Modifier.height(16.dp))
      }
    }
    item {
      Column(
        modifier = Modifier
          .background(colors.settingsBackground)
          .fillMaxWidth()
      ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
          text = stringResource(string.settings_user_settings),
          style = Typography.h3.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = user_settings_text
          ),
          textAlign = TextAlign.Start,
          modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )
      }
    }
    items(userSettings.size) { index ->
      UserSettingsListItem(settingsEntity = userSettings[index],
        onItemClick = {})
    }
    item {
      Spacer(modifier = Modifier.height(8.dp))
      Divider(color = Color.DarkGray, thickness = 1.dp)
    }
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = stringResource(string.settings_nitro_settings),
        style = Typography.h3.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 15.sp,
          color = user_settings_text
        ),
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
      )
    }
    items(nitroSettings.size) { index ->
      UserSettingsListItem(settingsEntity = nitroSettings[index],
        onItemClick = {})
    }
    item {
      Spacer(modifier = Modifier.height(8.dp))
      Divider(color = Color.DarkGray, thickness = 1.dp)
    }
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = stringResource(string.settings_app_settings),
        style = Typography.h3.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 15.sp,
          color = user_settings_text
        ),
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
      )

    }
    items(appSettings.size) { index ->
      UserSettingsListItem(settingsEntity = appSettings[index],
        onItemClick = {})
    }
    item {
      Spacer(modifier = Modifier.height(8.dp))
      Divider(color = Color.DarkGray, thickness = 1.dp)
    }
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = stringResource(string.settings_app_info),
        style = Typography.h3.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 15.sp,
          color = user_settings_text
        ),
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
      )
    }
    items(appInfo.size) { index ->
      UserSettingsListItem(settingsEntity = appInfo[index],
        onItemClick = {})
    }
    item {
      Spacer(
        modifier = Modifier
          .navigationBarsPadding()
          .padding(vertical = 32.dp)
      )
    }
  }
}