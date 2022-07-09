package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ChannelList(
    modifier: Modifier = Modifier,
    onItemSelection: () -> Unit,
) {

}

@Preview(showSystemUi = true)
@Composable
private fun ChannelScreenPreview() {
    ChannelList(
        onItemSelection = {}
    )
}