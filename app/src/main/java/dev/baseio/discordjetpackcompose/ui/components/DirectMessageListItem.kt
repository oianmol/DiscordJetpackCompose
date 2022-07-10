package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import dev.baseio.discordjetpackcompose.ui.theme.DirectMessageListTypography
import dev.baseio.discordjetpackcompose.ui.utils.clickableWithRipple

@Composable
fun DirectMessageListItem(
    chatUserEntity: ChatUserEntity, isSelected: Boolean, onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(if (isSelected) DiscordColorProvider.colors.onSurface.copy(alpha = 0.1f) else Color.Transparent)
            .clickableWithRipple(onClick = onItemClick)
        ,
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
        Column(
            modifier = Modifier.padding(start = 8.dp),
        ) {
            Text(
                text = chatUserEntity.name, style = DirectMessageListTypography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = DiscordColorProvider.colors.onSurface
            )
            AnimatedVisibility(
                modifier = Modifier.padding(top = 2.dp),
                visible = chatUserEntity.currentStatus != null
            ) {
                Text(
                    text = chatUserEntity.currentStatus.orEmpty(),
                    style = DirectMessageListTypography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = DiscordColorProvider.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DirectMessageListItemPreview() {
    DirectMessageListItem(
        chatUserEntity = ChatUserEntity(
            username = "testusername",
            name = "Test User",
            currentStatus = "Studying",
            isOnline = false,
        ),
        isSelected = true,
        onItemClick = {},
    )
}