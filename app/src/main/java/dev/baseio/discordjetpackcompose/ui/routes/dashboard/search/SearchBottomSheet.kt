package dev.baseio.discordjetpackcompose.ui.routes.dashboard.search

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.entities.search.SearchFilter
import dev.baseio.discordjetpackcompose.entities.search.SearchSheetListItem
import dev.baseio.discordjetpackcompose.ui.components.DiscordText
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.components.SearchSheetFilteredItemList
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.components.SearchSheetServerSlider
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.components.SearchSheetTextField
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.theme.contentColorFor
import dev.baseio.discordjetpackcompose.utils.getSampleSheetListItems
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchBottomSheet(
    sheetState: BottomSheetScaffoldState,
    serverIdList: List<String>,
    listItems: List<SearchSheetListItem>,
    onServerSelect: (serverId: String) -> Unit,
    onChannelSelect: (channelId: String) -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val surfaceColor = DiscordColorProvider.colors.surface
    val contentColor = DiscordColorProvider.colors.contentColorFor(surfaceColor)

    var searchQuery by remember { mutableStateOf(TextFieldValue()) }
    var lastChannel: SearchSheetListItem? by remember { mutableStateOf(null) }

    LaunchedEffect(lastChannel) {
        lastChannel?.let { nnLastChannel -> onChannelSelect(nnLastChannel.id) }
    }

    BackHandler(enabled = sheetState.bottomSheetState.isExpanded) {
        coroutineScope.launch { sheetState.bottomSheetState.collapse() }
    }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetGesturesEnabled = false,
        sheetContent = {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchSheetTextField(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(top = 8.dp),
                    query = searchQuery,
                    onQueryChanged = { updatedQuery -> searchQuery = updatedQuery },
                    filters = SearchFilter.values().toList(),
                    onFilterChanged = { newFilter: SearchFilter ->
                        searchQuery = TextFieldValue(
                            if (SearchFilter.values().map { it.sign }.contains(searchQuery.text.toCharArray().firstOrNull())) {
                                if (searchQuery.text.isNotEmpty()) {
                                    searchQuery.text.replaceFirstChar { newFilter.sign }
                                } else {
                                    newFilter.sign.toString()
                                }
                            } else {
                                "${newFilter.sign}${searchQuery.text}"
                            },
                            selection = TextRange(searchQuery.text.length + 1)
                        )
                    },
                    placeholder = stringResource(R.string.search_bottom_sheet_topbar_placeholder),
                    onNavigateUp = { coroutineScope.launch { sheetState.bottomSheetState.collapse() } }
                )
                if (searchQuery.text.isBlank()) {
                    SearchSheetServerSlider(
                        serverIdList = serverIdList,
                        onServerSelect = { serverId ->
                            onServerSelect(serverId)
                            coroutineScope.launch { sheetState.bottomSheetState.collapse() }
                        },
                    )
                }
                SearchSheetFilteredItemList(
                    isSearchQueryBlank = searchQuery.text.isBlank(),
                    lastChannel = lastChannel,
                    listItems = listItems.filter { item ->
                        val currentItemType = SearchFilter.values().find { it.sign == searchQuery.text.firstOrNull() }
                        println("currentItemType is: $currentItemType")
                        currentItemType?.let { nnCurrentItemType ->
                            (nnCurrentItemType == item.itemType) && item.title.contains(searchQuery.text.replaceFirstChar { "" }.trimStart(), ignoreCase = true)
                        } ?: item.title.contains(searchQuery.text, ignoreCase = true)
                    },
                    onItemClick = { item ->
                        lastChannel = item
                        coroutineScope.launch { sheetState.bottomSheetState.collapse() }
                    }
                )
            }
        },
        content = content,
        contentColor = contentColor,
        sheetContentColor = contentColor,
        sheetBackgroundColor = surfaceColor,
        backgroundColor = surfaceColor,
        sheetPeekHeight = 0.dp,
        modifier = Modifier.statusBarsPadding()
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SearchBottomSheetPreview() {
    DiscordJetpackComposeTheme {
        val sheetState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
        )
        val coroutineScope = rememberCoroutineScope()
        var selectedServerId: String? by remember { mutableStateOf(null) }
        var selectedChannelId: String? by remember { mutableStateOf(null) }

        SearchBottomSheet(
            sheetState = sheetState,
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        selectedServerId?.let {
                            DiscordText(text = "Selected server is: $selectedServerId")
                        }
                        selectedChannelId?.let {
                            DiscordText(text = "Selected channel is: $selectedChannelId")
                        }
                        Button(
                            onClick = {
                                coroutineScope.launch { sheetState.bottomSheetState.expand() }
                            },
                            modifier = Modifier.wrapContentSize(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = DiscordColorProvider.colors.primary,
                                contentColor = DiscordColorProvider.colors.contentColorFor(
                                    DiscordColorProvider.colors.primary
                                )
                            )
                        ) {
                            DiscordText(text = "Click to open sheet")
                        }
                    }
                }
            },
            serverIdList = listOf("1", "2", "3", "4", "5"),
            onServerSelect = { serverId -> selectedServerId = serverId },
            listItems = getSampleSheetListItems(),
            onChannelSelect = { channelId -> selectedChannelId = channelId }
        )
    }
}