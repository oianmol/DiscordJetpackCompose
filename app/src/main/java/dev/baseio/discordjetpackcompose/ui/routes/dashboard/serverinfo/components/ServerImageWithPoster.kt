package dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.theme.DiscordSurface
import dev.baseio.discordjetpackcompose.ui.utils.rememberCoilImageRequest
import dev.baseio.discordjetpackcompose.utils.Constants

@Composable
fun ServerImageWithPoster(
    imageUri: String,
    posterUri: String? = null
) {
    val posterHeight = LocalConfiguration.current.screenHeightDp.times(0.25f).dp
    if (posterUri != null) {
        ConstraintLayout {
            val (poster, image) = createRefs()
            ServerPoster(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(posterHeight)
                    .constrainAs(poster) { top.linkTo(parent.top) },
                posterUri = posterUri
            )
            ServerImage(
                modifier = Modifier.constrainAs(image) {
                    top.linkTo(poster.bottom)
                    bottom.linkTo(poster.bottom)
                },
                imageUri = imageUri
            )
        }
    } else {
        ServerImage(
            modifier = Modifier.padding(top = 16.dp),
            imageUri = imageUri
        )
    }
}

@Composable
private fun ServerImage(
    modifier: Modifier,
    imageUri: String,
    imageShape: Shape = RoundedCornerShape(25),
    imageSize: Dp = 84.dp,
    surfacePadding: Dp = 4.dp,
    startPadding: Dp = 24.dp,
) {
    DiscordSurface(
        modifier = modifier
            .padding(start = startPadding)
            .size(imageSize),
        shape = imageShape,
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(surfacePadding)
                .fillMaxSize()
                .clip(imageShape),
            model = rememberCoilImageRequest(data = imageUri),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun ServerPoster(modifier: Modifier, posterUri: String) {
    AsyncImage(
        modifier = modifier,
        model = rememberCoilImageRequest(data = posterUri),
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun ServerImageWithPosterPreview() {
    DiscordJetpackComposeTheme {
        ServerImageWithPoster(
            imageUri = Constants.MMLogoUrl,
            posterUri = Constants.MMLogoUrl,
        )
    }
}