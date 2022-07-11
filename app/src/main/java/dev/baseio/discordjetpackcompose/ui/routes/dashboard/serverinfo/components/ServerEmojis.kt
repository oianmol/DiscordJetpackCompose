package dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordSurface
import dev.baseio.discordjetpackcompose.ui.theme.ServerInfoTypography
import dev.baseio.discordjetpackcompose.ui.utils.rememberCoilImageRequest

@Composable
fun ServerEmojis(
    modifier: Modifier = Modifier,
    emojiUris: List<Any?>,
    emojiSize: Dp = 24.dp,
    listShape: Shape = RoundedCornerShape(8.dp)
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${emojiUris.size} EMOJIS", style = ServerInfoTypography.overline.copy(
                    color = DiscordColorProvider.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                )
            )
            DotDivider(modifier = Modifier.padding(horizontal = 8.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_nitro),
                contentDescription = null,
                tint = Color.Magenta,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(10.dp),
            )
            Text(text = "Use anywhere with nitro", style = ServerInfoTypography.overline)
        }
        DiscordSurface(
            elevation = 1.dp,
            shape = listShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            FlowRow(modifier = Modifier.padding(12.dp), crossAxisSpacing = 4.dp) {
                emojiUris.forEach { emojiUri ->
                    AsyncImage(
                        model = rememberCoilImageRequest(data = emojiUri),
                        contentDescription = null,
                        modifier = Modifier.size(emojiSize),
                    )
                }
            }
        }
    }
}

@Composable
private fun DotDivider(
    modifier: Modifier = Modifier,
    size: Dp = 4.dp,
    color: Color = DiscordColorProvider.colors.onSurface.copy(alpha = 0.5f)
) {
    Canvas(modifier = modifier.size(size), onDraw = {
        drawCircle(color = color)
    })
}

@Preview(showSystemUi = true)
@Composable
private fun ServerEmojisPreview() {
    ServerEmojis(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp), emojiUris = listOf(
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
            R.drawable.ic_nitro,
        )
    )
}