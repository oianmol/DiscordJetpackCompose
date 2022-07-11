package dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.ServerEmojis
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.ServerImageWithPoster
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.ServerInfoActions
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.ServerNameWithBasicInfo
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.ServerQuickActions
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.models.ServerInfoAction
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.models.ServerQuickAction
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.theme.contentColorFor
import dev.baseio.discordjetpackcompose.utils.Constants
import kotlinx.coroutines.launch

val DiscordSwitchColors @Composable get() = SwitchDefaults.colors(
    checkedThumbColor = DiscordColorProvider.colors.primary,
    uncheckedThumbColor = DiscordColorProvider.colors.surface,
    uncheckedTrackColor = DiscordColorProvider.colors.onSurface,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ServerInfoBottomSheet(
    sheetState: ModalBottomSheetState,
    serverEntity: ServerEntity,
    onBoostIconClick: () -> Unit = {},
    onNotificationsIconClick: () -> Unit = {},
    onInviteIconClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetBackgroundColor = DiscordColorProvider.colors.surface,
        scrimColor = Color.Black.copy(alpha = 0.32f),
        sheetContentColor = DiscordColorProvider.colors.contentColorFor(DiscordColorProvider.colors.surface),
        sheetState = sheetState,
        content = content,
        sheetContent = {
            LazyColumn {
                item {
                    ServerImageWithPoster(
                        imageUri = serverEntity.thumbnailUri, posterUri = serverEntity.posterUri
                    )
                }
                item {
                    ServerNameWithBasicInfo(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        serverName = serverEntity.name,
                        serverDescription = serverEntity.description,
                        onlineMembersCount = serverEntity.onlineMembersCount,
                        totalMembersCount = serverEntity.totalMembersCount,
                    )
                }
                item {
                    ServerQuickActions(
                        modifier = Modifier.fillMaxWidth(), actions = listOf(
                            ServerQuickAction(
                                icon = R.drawable.ic_boost,
                                iconTint = Color.Magenta,
                                label = stringResource(
                                    R.string.server_info_boost_btn_txt,
                                    serverEntity.boostCount.toString()
                                ),
                                onClick = onBoostIconClick,
                            ),
                            ServerQuickAction(
                                icon = R.drawable.ic_notifications,
                                label = stringResource(R.string.server_info_notifications_btn_txt),
                                onClick = onNotificationsIconClick,
                            ),
                            ServerQuickAction(
                                icon = R.drawable.ic_invite,
                                label = stringResource(R.string.server_info_invite_btn_txt),
                                onClick = onInviteIconClick,
                            ),
                        )
                    )
                }
                item {
                    ServerInfoActions(
                        actions = listOf(
                            ServerInfoAction(title = stringResource(R.string.server_info_action_title_mark_as_read),
                                titleColor = DiscordColorProvider.colors.onSurface.copy(alpha = ContentAlpha.medium),
                                subtitle = null,
                                trailingComposable = {},
                                onClick = {}),
                        )
                    )
                }
                item {
                    var isDirectMessagesEnabled by remember { mutableStateOf(false) }
                    var isHideMutedChannelsEnabled by remember { mutableStateOf(false) }
                    ServerInfoActions(
                        actions = listOf(
                            ServerInfoAction(title = stringResource(R.string.server_info_action_title_edit_server_profile),
                                titleColor = DiscordColorProvider.colors.onSurface.copy(alpha = ContentAlpha.medium),
                                subtitle = null,
                                trailingComposable = {},
                                onClick = {}),
                            ServerInfoAction(title = stringResource(R.string.server_info_action_title_direct_messages),
                                titleColor = DiscordColorProvider.colors.onSurface,
                                subtitle = stringResource(R.string.server_info_action_subtitle_direct_messages),
                                trailingComposable = {
                                    Switch(checked = isDirectMessagesEnabled,
                                        onCheckedChange = { isChecked -> isDirectMessagesEnabled = isChecked },
                                        colors = DiscordSwitchColors
                                    )
                                },
                                onClick = { isDirectMessagesEnabled = !isDirectMessagesEnabled }),
                            ServerInfoAction(title = stringResource(R.string.server_info_action_title_hide_muted_channels),
                                titleColor = DiscordColorProvider.colors.onSurface,
                                subtitle = null,
                                trailingComposable = {
                                    Switch(checked = isHideMutedChannelsEnabled,
                                        onCheckedChange = { isChecked -> isHideMutedChannelsEnabled = isChecked },
                                        colors = DiscordSwitchColors)
                                },
                                onClick = { isHideMutedChannelsEnabled = !isHideMutedChannelsEnabled }),
                            ServerInfoAction(title = stringResource(R.string.server_info_action_title_leave_server),
                                titleColor = DiscordColorProvider.colors.error,
                                subtitle = null,
                                trailingComposable = {},
                                onClick = {}),
                        )
                    )
                }
                item {
                    ServerEmojis(
                        modifier = Modifier.padding(16.dp), emojiUris = serverEntity.serverEmojiUris
                    )
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true)
@Composable
private fun ServerInfoBottomSheetPreview() {
    DiscordJetpackComposeTheme {
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()
        ServerInfoBottomSheet(sheetState = sheetState, serverEntity = ServerEntity(
            id = "1",
            name = "Coding in Flow",
            thumbnailUri = Constants.MMLogoUrl,
            selectedAnimationUri = null,
            posterUri = Constants.MMLogoUrl,
            channels = listOf(),
            allChannelsUnreadCount = 0,
            hasNitroSubscription = false,
            totalMembersCount = 6717,
            onlineMembersCount = 271,
            description = "The best place for programmers to learn and find new friends.",
            boostCount = 3,
            serverEmojiUris = mutableListOf<String>().apply {
                repeat(16) {
                    add("https://cdn.shopify.com/s/files/1/1061/1924/files/Hugging_Face_Emoji_2028ce8b-c213-4d45-94aa-21e1a0842b4d_large.png?15202324258887420558")
                }
            }
        ), content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable {
                    coroutineScope.launch { sheetState.show() }
                }) { Text(text = "Click to open server sheet", color = DiscordColorProvider.colors.onSurface) }
        })
    }
}