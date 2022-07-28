package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.OnlineIndicator
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.friends.Header
import dev.baseio.discordjetpackcompose.ui.theme.*
import dev.baseio.discordjetpackcompose.viewmodels.FriendsViewModel

@Composable
fun ChannelMemberScreen(
    modifier: Modifier,
    onInviteButtonClicked: () -> Unit,
    viewModel: FriendsViewModel = hiltViewModel()
) {

    val friendsList by viewModel.friendsList.collectAsState()
    val friends by friendsList.collectAsState(initial = emptyList())

    val onlineList = friends.filter {
        it.isOnline
    }

    val offlineList = friends.filter {
        !it.isOnline
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        ChannelHeaderText()
        Divider(
            color = channel_member_secondary_bg,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(create_server_screen)
        )
        ChannelMemberActions()
        InviteMembers(onInviteButtonClicked)
        ChannelMembersList(offlineList, onlineList)
    }
}

@Composable
fun ChannelHeaderText() {
    Text(
        text = stringResource(id = R.string.channel_header), modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        style = Typography.h5.copy(
            fontWeight = FontWeight.SemiBold
        ),
        color = DiscordColorProvider.colors.onSurface
    )
}

@Composable
fun ChannelMemberActions() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(channel_member_bg),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionIcons(
            label = stringResource(id = R.string.threads),
            painterResource = R.drawable.ic_hashtag_solid,
            icon = null
        )
        ActionIcons(Icons.Filled.PushPin, stringResource(id = R.string.pins), null)
        ActionIcons(Icons.Filled.Notifications, stringResource(id = R.string.notifications), null)
        ActionIcons(Icons.Filled.Settings, stringResource(id = R.string.settings), null)
    }
}

@Composable
fun ActionIcons(
    icon: ImageVector?,
    label: String,
    painterResource: Int?
) {
    if (icon != null) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                icon,
                contentDescription = label,
                modifier = Modifier.size(18.dp),
                tint = channel_member_action_icon
            )
            Spacer(modifier = Modifier.height(4.dp))
            ActionIconLabel(label = label)
        }
    } else if (painterResource != null) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painterResource(id = painterResource),
                contentDescription = label,
                modifier = Modifier.size(18.dp),
                tint = channel_member_action_icon
            )
            Spacer(modifier = Modifier.height(4.dp))
            ActionIconLabel(label = label)
        }
    }
}

@Composable
fun InviteMembers(onInviteButtonClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(channel_member_secondary_bg)
            .clickable {
                onInviteButtonClicked()
            }
            .padding(16.dp),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(id = R.drawable.ic_baseline_person_add_alt_1_24),
            contentDescription = stringResource(id = R.string.invite_members),
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(channel_member_bg)
                .padding(6.dp),
            tint = channel_member_action_icon
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(id = R.string.invite_members),
            color = DiscordColorProvider.colors.onSurface,
            style = Typography.subtitle2.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        )

    }
}


@Composable
fun ActionIconLabel(label: String) {
    Text(
        text = label,
        color = channel_member_action_label,
        style = Typography.subtitle2.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
    )
}

@Composable
fun ChannelMembersList(
    offlineMembersList: List<ChatUserEntity>,
    onlineMembersList: List<ChatUserEntity>
) {
    LazyColumn(modifier = Modifier
        .background(channel_member_secondary_bg)
        .padding(8.dp)) {
        item {
            Header(title = R.string.online, onlineMembersList.size)
        }
        items(onlineMembersList) { friend ->
            FriendComponent(chatUserEntity = friend)
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Header(
                title = R.string.offline,
                offlineMembersList.size,
                style = DirectMessageListTypography.h5.copy(
                    fontSize = 14.sp
                )
            )
        }
        items(offlineMembersList) { friend ->
            FriendComponent(chatUserEntity = friend)
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun FriendComponent(chatUserEntity: ChatUserEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.padding(8.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(chatUserEntity.profileImage)
                    .placeholder(R.drawable.light_app_logo)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(32.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape),
            )
            OnlineIndicator(isOnline = chatUserEntity.isOnline)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = chatUserEntity.username,
            style = DirectMessageListTypography.h6.copy(
                fontWeight =
                FontWeight.Normal,
                fontSize = 14.sp

            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = DiscordColorProvider.colors.onSurface
        )
    }
}