package dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SectionItem(
  color: Color? = null,
  disabled: Boolean = false,
  paddingVertical: Dp = 0.dp,
  paddingHorizontal: Dp = 16.dp,
  onClick: () -> Unit,
  leadingComposable: @Composable () -> Unit = {},
  trailingComposable: @Composable () -> Unit = {},
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(color = color ?: Color.Transparent)
      .alpha(if(disabled) ContentAlpha.disabled else 1.0f)
      .clickable(onClick = onClick)
      .padding(horizontal = paddingHorizontal, vertical = paddingVertical),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    leadingComposable()
    trailingComposable()
  }
}