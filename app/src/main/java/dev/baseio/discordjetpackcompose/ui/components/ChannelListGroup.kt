package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Podcasts
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.discordjetpackcompose.entities.server.ChannelEntity
import dev.baseio.discordjetpackcompose.entities.server.ChannelType
import dev.baseio.discordjetpackcompose.ui.theme.ChannelListTypography
import dev.baseio.discordjetpackcompose.ui.utils.clickableWithRipple

@Composable
fun ChannelListGroup(
    category: String?,
    channelGroup: List<ChannelEntity>,
    onItemClick: (String) -> Unit,
    currentSelectedItemId: String?,
) {
    var isCategoryExpanded by remember { mutableStateOf(true) }
    val filteredChannelGroup by remember(isCategoryExpanded) {
        derivedStateOf {
            if (isCategoryExpanded) channelGroup else channelGroup.filter { isCategoryExpanded != it.isUnread }
        }
    }

    Column {
        category?.let { nnCategory ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickableWithRipple(indication = null,
                        onClick = { isCategoryExpanded = !isCategoryExpanded }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val iconRotation by animateFloatAsState(targetValue = if (isCategoryExpanded) 360f else 270f)
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(horizontal = 4.dp)
                        .rotate(iconRotation),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
                Text(
                    text = nnCategory.uppercase(),
                    color = DiscordColorProvider.colors.onSurface.copy(alpha = 0.5f),
                    style = ChannelListTypography.button
                )
            }
        }
        Column {
            filteredChannelGroup.forEach { currentItem ->
                val isItemSelected by remember(currentSelectedItemId) {
                    mutableStateOf(currentSelectedItemId == currentItem.id)
                }
                val itemTint by animateColorAsState(
                    targetValue = DiscordColorProvider.colors.onSurface.copy(
                        alpha = if (isItemSelected || currentItem.isUnread) 1f else ContentAlpha.disabled
                    )
                )
                Box(contentAlignment = Alignment.CenterStart) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isItemSelected) {
                                    DiscordColorProvider.colors.onSurface.copy(alpha = 0.15f)
                                } else {
                                    Color.Transparent
                                }
                            )
                            .clickableWithRipple(onClick = {
                                onItemClick(currentItem.id)
                            }),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.padding(8.dp),
                                imageVector = getChannelIcon(currentItem.type),
                                contentDescription = null,
                                tint = itemTint
                            )
                            Text(
                                text = currentItem.name,
                                color = itemTint,
                                fontWeight = if (isItemSelected) FontWeight.SemiBold else FontWeight.Normal,
                            )
                        }
                        CountIndicator(
                            count = currentItem.unreadCount,
                            showCardBackground = false,
                            textSize = 10.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                    if (currentItem.isUnread) {
                        Box(
                            modifier = Modifier
                                .size(ServerIconSelectorItem.iconStartPadding, 9.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topEndPercent = 50, bottomEndPercent = 50
                                    )
                                )
                                .background(DiscordColorProvider.colors.onSurface)
                        )
                    }
                }
            }
        }
    }
}

private fun getChannelIcon(channelType: ChannelType): ImageVector {
    return when (channelType) {
        ChannelType.PUBLIC -> Icons.Default.Lock
        ChannelType.PRIVATE -> Icons.Default.Tag
        ChannelType.BROADCAST -> Icons.Default.Campaign
        ChannelType.PODCAST -> Icons.Default.Podcasts
        ChannelType.CONVERSATION -> Icons.Default.VolumeUp
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChannelListItemPreview() {
    ChannelListGroup(
        category = null,
        channelGroup = listOf(),
        onItemClick = {},
        currentSelectedItemId = ""
    )
}