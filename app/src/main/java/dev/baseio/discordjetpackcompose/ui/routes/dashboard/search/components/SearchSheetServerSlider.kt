package dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.CountIndicator
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.utils.clickableWithRipple
import dev.baseio.discordjetpackcompose.ui.utils.rememberCoilImageRequest
import dev.baseio.discordjetpackcompose.viewmodels.DashboardScreenViewModel

@Composable
fun SearchSheetServerSlider(
    serverIdList: List<String>,
    dashboardScreenVM: DashboardScreenViewModel = hiltViewModel(),
    onServerSelect: (String) -> Unit
) {
    val serverList = dashboardScreenVM.searchSheetServerList

    LaunchedEffect(serverIdList) {
        dashboardScreenVM.getServers(serverIds = serverIdList)
    }

    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
    ) {
        item { Spacer(modifier = Modifier.padding(start = 8.dp)) }
        items(serverList.size) { index ->
            DiscordIndicator(
                modifier = Modifier.padding(start = 8.dp),
                indicatorPosition = DiscordIndicatorPosition.Bottom,
                isEnabled = serverList[index].allChannelsUnreadCount > 0
            ) {
                CountIndicator(count = serverList[index].allChannelsUnreadCount) {
                    AsyncImage(
                        model = rememberCoilImageRequest(data = serverList[index].thumbnailUri),
                        contentDescription = null,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .clickableWithRipple { onServerSelect(serverList[index].id) },
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SearchSheetServerSliderPreview() {
    DiscordJetpackComposeTheme {
        SearchSheetServerSlider(
            serverIdList = listOf("1", "2", "3", "4", "5"),
            onServerSelect = {}
        )
    }
}