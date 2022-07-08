package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ServerChannelList(
    modifier: Modifier = Modifier,
    cornerSize: Dp = 8.dp,
) {
    Surface(
        modifier = modifier.fillMaxHeight(),
        elevation = 4.dp,
        shape = RoundedCornerShape(topStart = cornerSize, topEnd = cornerSize)
    ) {}
}

@Preview(showSystemUi = true)
@Composable
private fun ServerChannelListPreview() {
    ServerChannelList()
}