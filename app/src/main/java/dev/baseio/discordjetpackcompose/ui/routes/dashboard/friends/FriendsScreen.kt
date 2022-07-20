package dev.baseio.discordjetpackcompose.ui.routes.dashboard.friends


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.DiscordAppBar
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.friends.components.FriendRowComponent
import dev.baseio.discordjetpackcompose.ui.theme.*
import dev.baseio.discordjetpackcompose.viewmodels.FriendsViewModel

@Composable
fun FriendsScreen(
    composeNavigator: ComposeNavigator,
    viewModel: FriendsViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()

    val sysUiController = rememberSystemUiController()

    val colors = DiscordColorProvider.colors
    SideEffect {
        sysUiController.setSystemBarsColor(color = colors.discordBackgroundOne)
        sysUiController.setNavigationBarColor(color = colors.background)
    }

    val suggestionsList by viewModel.friendsSuggestionsList.collectAsState()
    val suggestions by suggestionsList.collectAsState(initial = emptyList())

    val friendsList by viewModel.friendsList.collectAsState()
    val friends by friendsList.collectAsState(initial = emptyList())

    val onlineList = friends.filter {
        it.isOnline
    }

    val offlineList = friends.filter {
        !it.isOnline
    }

    DiscordScaffold(
        scaffoldState = scaffoldState,
        navigator = composeNavigator,

        topAppBar = {
            DiscordAppBar(title = {
                Text(
                    text = stringResource(id = R.string.friends),
                    style = Typography.h3.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                )
            },
                backgroundColor = create_server_screen,
                actions = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chat_bubble),
                        contentDescription = stringResource(
                            id = R.string.invite_friend
                        ),
                        tint = discord_icon_button,
                        modifier = Modifier.clickable {

                        }
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_person_add_alt_1_24),
                        contentDescription = stringResource(
                            id = R.string.add_friend
                        ),
                        tint = discord_icon_button,
                        modifier = Modifier.clickable {

                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 30.dp)
        ) {
            item {
                Header(title = R.string.friend_suggestions, suggestions.size)
            }
            items(suggestions) { friend ->
                FriendRowComponent(chatUserEntity = friend, false)
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            item {
                Header(title = R.string.online, onlineList.size)
            }
            items(onlineList) { friend ->
                FriendRowComponent(chatUserEntity = friend, true)
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            item {
                Header(title = R.string.offline, offlineList.size)
            }
            items(offlineList) { friend ->
                FriendRowComponent(chatUserEntity = friend, true)
            }
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }

        }
    }
}

@Composable
fun Header(title: Int, size: Int, style: TextStyle = DirectMessageListTypography.h5) {
    Text(
        text = stringResource(title, size),
        style = style,
        color = DiscordColorProvider.colors.onSurface.copy(alpha = ContentAlpha.medium)
    )
}