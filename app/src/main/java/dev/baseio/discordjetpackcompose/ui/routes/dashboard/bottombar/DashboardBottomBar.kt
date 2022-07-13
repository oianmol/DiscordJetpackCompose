package dev.baseio.discordjetpackcompose.ui.routes.dashboard.bottombar

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.BottomNavigation
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.components.DiscordIcon
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.CountIndicator
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.OnlineIndicator
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.theme.contentColorFor
import dev.baseio.discordjetpackcompose.ui.utils.rememberCoilImageRequest
import dev.baseio.discordjetpackcompose.utils.Constants

enum class DashboardBottomBarItemType {
    Home, Friends, Search, Mentions, Profile
}

data class DashboardBottomBarItem(
    val isSelected: Boolean,
    val icon: @Composable () -> Unit,
    val unreadCount: Int? = null,
    val onClick: () -> Unit,
    val type: DashboardBottomBarItemType
)

@Composable
fun DashboardBottomBar(
    isDisplayed: Boolean,
    bottomBarItems: List<DashboardBottomBarItem>
) {
    val surfaceColor = DiscordColorProvider.colors.surface
    val contentColor = DiscordColorProvider.colors.contentColorFor(surfaceColor)

    AnimatedVisibility(
        visible = isDisplayed,
        enter = slideInVertically(animationSpec = tween(), initialOffsetY = { it }),
        exit = slideOutVertically(animationSpec = tween(), targetOffsetY = { it })
    ) {
        BottomNavigation(
            backgroundColor = surfaceColor,
            contentColor = contentColor,
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.navigationBars
            )
        ) {
            bottomBarItems.forEach { item ->
                if (!DashboardBottomBarItemType.values().contains(item.type)) {
                    throw Exception(stringResource(R.string.unknown_bottom_bar_item_type))
                }

                BottomNavigationItem(
                    selected = item.isSelected,
                    onClick = item.onClick,
                    icon = {
                        CountIndicator(
                            count = item.unreadCount,
                            forceCircleShape = false,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            item.icon()
                        }
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = contentColor,
                    unselectedContentColor = contentColor.copy(alpha = ContentAlpha.disabled)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DashboardBottomBarPreview() {
    var currentSelectedItem: DashboardBottomBarItemType by remember {
        mutableStateOf(
            DashboardBottomBarItemType.Home
        )
    }
    val userProfileImage = remember { Constants.MMLogoUrl }
    val iconSize = remember { 24.dp }
    val commonModifier = remember { Modifier.padding(16.dp) }

    DiscordJetpackComposeTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            DashboardBottomBar(
                bottomBarItems = listOf(
                    DashboardBottomBarItem(
                        isSelected = currentSelectedItem == DashboardBottomBarItemType.Home,
                        icon = {
                            DiscordIcon(
                                painter = painterResource(id = R.drawable.ic_discord_icon),
                                contentDescription = null,
                                modifier = commonModifier,
                            )
                        },
                        unreadCount = 242,
                        onClick = {
                            currentSelectedItem = DashboardBottomBarItemType.Home
                        },
                        type = DashboardBottomBarItemType.Home
                    ),
                    DashboardBottomBarItem(
                        isSelected = currentSelectedItem == DashboardBottomBarItemType.Friends,
                        icon = {
                            DiscordIcon(
                                imageVector = Icons.Default.EmojiPeople,
                                contentDescription = null,
                                modifier = commonModifier,
                            )
                        },
                        unreadCount = null,
                        onClick = {
                            currentSelectedItem = DashboardBottomBarItemType.Friends
                        },
                        type = DashboardBottomBarItemType.Friends
                    ),
                    DashboardBottomBarItem(
                        isSelected = currentSelectedItem == DashboardBottomBarItemType.Search,
                        icon = {
                            DiscordIcon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                modifier = commonModifier,
                            )
                        },
                        unreadCount = null,
                        onClick = {
                            currentSelectedItem = DashboardBottomBarItemType.Search
                        },
                        type = DashboardBottomBarItemType.Search
                    ),
                    DashboardBottomBarItem(
                        isSelected = currentSelectedItem == DashboardBottomBarItemType.Mentions,
                        icon = {
                            DiscordIcon(
                                imageVector = Icons.Default.AlternateEmail,
                                contentDescription = null,
                                modifier = commonModifier,
                            )
                        },
                        unreadCount = 20,
                        onClick = {
                            currentSelectedItem = DashboardBottomBarItemType.Mentions
                        },
                        type = DashboardBottomBarItemType.Mentions
                    ),
                    DashboardBottomBarItem(
                        isSelected = currentSelectedItem == DashboardBottomBarItemType.Profile,
                        icon = {
                            OnlineIndicator(isOnline = true) {
                                AsyncImage(
                                    model = rememberCoilImageRequest(data = userProfileImage),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .size(iconSize)
                                )
                            }
                        },
                        unreadCount = null,
                        onClick = {
                            currentSelectedItem = DashboardBottomBarItemType.Profile
                        },
                        type = DashboardBottomBarItemType.Profile
                    ),
                ),
                isDisplayed = true
            )
        }
    }
}