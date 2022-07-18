package dev.baseio.discordjetpackcompose.ui.routes.dashboard.friends.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.OnlineIndicator
import dev.baseio.discordjetpackcompose.ui.theme.*

@Composable
fun FriendRowComponent(chatUserEntity: ChatUserEntity, isFriend: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileComponent(chatUserEntity = chatUserEntity, isFriend)
        if (isFriend) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                ActionButton(
                    icon = Icons.Default.Call,
                    contentDescription = stringResource(id = R.string.call_friend)
                )
                Spacer(modifier = Modifier.width(20.dp))
                ActionButton(
                    painterResource =
                    R.drawable.ic_chat_bubble,
                    contentDescription = stringResource(id = R.string.message_friend)
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                ActionButton(
                    icon = Icons.Default.Close,
                    tintColor = Color.Red,
                    contentDescription = stringResource(id = R.string.remove_friend)
                )
                Spacer(modifier = Modifier.width(20.dp))
                ActionButton(
                    painterResource =
                    R.drawable.ic_baseline_person_add_alt_1_24,
                    contentDescription = stringResource(id = R.string.add_friend)
                )
            }
        }
    }
}

@Composable
fun ActionButton(
    background: Color = discord_icon_button_bg,
    icon: ImageVector? = null,
    painterResource: Int? = null,
    contentDescription: String,
    tintColor: Color = icon_button
) {

    if (icon != null) {
        Icon(
            icon,
            contentDescription = contentDescription,
            tint = tintColor,
            modifier = Modifier
                .clip(CircleShape)
                .background(background)
                .size(40.dp)
                .padding(10.dp).clickable {  },
        )
    } else if (painterResource != null) {
        Icon(
            painter = painterResource(id = painterResource),
            contentDescription = contentDescription,
            tint = tintColor,
            modifier = Modifier
                .clip(CircleShape)
                .background(background)
                .size(40.dp)
                .padding(10.dp).clickable {  },
        )
    }

}

@Composable
fun ProfileComponent(chatUserEntity: ChatUserEntity, isFriend: Boolean = true) {
    Row(
        modifier = Modifier.wrapContentWidth(),
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
            if (isFriend) {
                OnlineIndicator(isOnline = chatUserEntity.isOnline)
            }
        }
        Column(
            modifier = Modifier.padding(start = 8.dp),
        ) {
            Text(
                text = chatUserEntity.username,
                style = DirectMessageListTypography.h6.copy(
                    fontWeight = if (isFriend) {
                        FontWeight.Normal
                    } else {
                        FontWeight.Bold
                    }
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (isFriend) {
                Text(
                    text = chatUserEntity.currentStatus.orEmpty(),
                    style = DirectMessageListTypography.body2.copy(
                        fontSize = 14.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = DiscordColorProvider.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                )
            } else {
                Text(
                    text = chatUserEntity.name,
                    style = DirectMessageListTypography.body2.copy(
                        fontSize = 14.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = DiscordColorProvider.colors.onSurface.copy(alpha = ContentAlpha.medium)

                )
            }
        }
    }
}