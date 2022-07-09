package dev.baseio.discordjetpackcompose.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.baseio.discordjetpackcompose.R

object ServerIconSelectorItem {
    val iconStartPadding = 6.dp
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ServerIconSelectorItem(
    id: Int? = null,
    @DrawableRes iconId: Int? = null,
    iconUri: String? = null,
    isSelected: Boolean,
    showIndicator: Boolean = true,
    iconColor: Color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
    iconStartPadding: Dp = ServerIconSelectorItem.iconStartPadding,
    indicatorWidth: Dp = iconStartPadding,
    unreadIndicatorCount: Int? = null,
    onClick: () -> Unit
) {
    key(id) {
        val cardShapeSize by animateIntAsState(targetValue = if (isSelected) 35 else 50)
        val indicatorHeight by animateDpAsState(targetValue = if (isSelected) 32.dp else if (id != ServerIconSelector.DMScreenId) 9.dp else 0.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            if (showIndicator) {
                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topEndPercent = 50, bottomEndPercent = 50
                            )
                        )
                        .size(width = indicatorWidth, height = indicatorHeight)
                        .background(MaterialTheme.colors.onSurface)
                        .align(Alignment.CenterStart)
                )
            }

            val cardBgColor = if (id == ServerIconSelector.DMScreenId && isSelected) {
                MaterialTheme.colors.primary
            } else {
                MaterialTheme.colors.surface
            }
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier
                        .padding(start = iconStartPadding)
                        .fillMaxWidth(0.65f)
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(cardShapeSize),
                    elevation = 8.dp,
                    color = cardBgColor,
                    onClick = onClick,
                ) {
                    AsyncImage(
                        modifier = Modifier.padding(iconId?.let { 12.dp } ?: 0.dp),
                        model = ImageRequest.Builder(LocalContext.current).data(iconId ?: iconUri)
                            .crossfade(true).placeholder(R.drawable.ic_refresh).build(),
                        contentDescription = null,
                        colorFilter = iconId?.let { ColorFilter.tint(color = iconColor) },
                        contentScale = ContentScale.Crop,
                    )
                }
                CountIndicator(count = unreadIndicatorCount)
            }
        }
    }
}