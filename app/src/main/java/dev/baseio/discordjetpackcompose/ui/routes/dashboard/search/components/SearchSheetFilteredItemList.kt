package dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.components.DiscordText
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.CountIndicator
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.SearchFilter
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.components.models.SearchSheetListItem
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.theme.DiscordSurface
import dev.baseio.discordjetpackcompose.ui.theme.SearchSheetDialogTypography
import dev.baseio.discordjetpackcompose.ui.theme.contentColorFor
import dev.baseio.discordjetpackcompose.ui.utils.clickableWithRipple
import dev.baseio.discordjetpackcompose.ui.utils.getSampleSheetListItems
import dev.baseio.discordjetpackcompose.ui.utils.rememberCoilImageRequest
import dev.baseio.discordjetpackcompose.utils.Constants

@Composable
fun SearchSheetFilteredItemList(
    isSearchQueryBlank: Boolean,
    lastChannel: SearchSheetListItem?,
    listItems: List<SearchSheetListItem>,
    onItemClick: (SearchSheetListItem) -> Unit
) {
    val surfaceColor = DiscordColorProvider.colors.surface
    val contentColor = DiscordColorProvider.colors.contentColorFor(surfaceColor)

    DiscordSurface(
        elevation = 1.dp,
        color = surfaceColor,
        contentColor = contentColor
    ) {
        LazyColumn {
            if (isSearchQueryBlank && lastChannel != null) {
                item {
                    Column {
                        DiscordText(
                            text = stringResource(R.string.last_channel_title),
                            style = SearchSheetDialogTypography.body2,
                            modifier = Modifier.padding(16.dp),
                        )
                        SearchSheetFilteredItem(
                            searchSheetListItem = lastChannel,
                            onItemClick = onItemClick
                        )
                    }
                }
            }
            if (listItems.isNotEmpty()) {
                item {
                    DiscordText(
                        text = stringResource(R.string.search_sheet_filtered_list_suggestions_title),
                        style = SearchSheetDialogTypography.body2,
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
            items(listItems.size) { index ->
                SearchSheetFilteredItem(
                    searchSheetListItem = listItems[index],
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
private fun SearchSheetFilteredItem(
    modifier: Modifier = Modifier,
    searchSheetListItem: SearchSheetListItem,
    onItemClick: (SearchSheetListItem) -> Unit
) {
    DiscordIndicator(
        modifier = modifier
            .padding(bottom = 4.dp)
            .clickableWithRipple {
                onItemClick(searchSheetListItem)
            }
            .padding(vertical = 4.dp),
        isEnabled = with(searchSheetListItem.unreadCount) { this != null && this > 0 },
        includeInvisibleIndicatorPadding = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (searchSheetListItem.iconUri is ImageVector) {
                    Image(
                        imageVector = searchSheetListItem.iconUri,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                } else {
                    AsyncImage(
                        model = rememberCoilImageRequest(data = searchSheetListItem.iconUri),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                }
                Spacer(modifier = Modifier.padding(start = 16.dp))
                Column {
                    DiscordText(
                        text = searchSheetListItem.title,
                        style = SearchSheetDialogTypography.subtitle1
                    )
                    searchSheetListItem.subtitle?.let { nnSubtitle ->
                        DiscordText(
                            text = nnSubtitle.uppercase(),
                            style = SearchSheetDialogTypography.subtitle1,
                            color = DiscordColorProvider.colors.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                searchSheetListItem.serverName?.let { nnServerName ->
                    DiscordText(
                        text = nnServerName,
                        style = SearchSheetDialogTypography.subtitle1,
                        modifier = Modifier.padding(end = 8.dp),
                        color = DiscordColorProvider.colors.onSurface.copy(alpha = 0.5f)
                    )
                }
                CountIndicator(
                    count = searchSheetListItem.unreadCount,
                    modifier = Modifier.padding(end = 8.dp),
                    showCardBackground = false,
                    textSize = 10.sp
                )
            }
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SearchSheetFilteredItemPreview() {
    DiscordJetpackComposeTheme {
        SearchSheetFilteredItem(
            modifier = Modifier.wrapContentSize(),
            searchSheetListItem = SearchSheetListItem(
                id = "Server01",
                itemType = SearchFilter.Servers,
                iconUri = Constants.MMLogoUrl,
                title = "Coding in Flow",
                subtitle = "This is a coding channel",
                serverName = "Coding in Flow",
                unreadCount = 92
            ),
            onItemClick = {}
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SearchSheetFilteredItemListPreview() {
    DiscordJetpackComposeTheme {
        SearchSheetFilteredItemList(
            isSearchQueryBlank = !false,
            lastChannel = SearchSheetListItem(
                id = "Server02",
                itemType = SearchFilter.Servers,
                iconUri = Constants.MMLogoUrl,
                title = "Coding in Flow",
                subtitle = "This is a coding channel",
                serverName = "Coding in Flow",
                unreadCount = null
            ),
            listItems = getSampleSheetListItems(),
            onItemClick = {}
        )
    }
}