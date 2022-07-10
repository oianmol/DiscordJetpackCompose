package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.insets.navigationBarsPadding
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.ui.theme.ChannelListTypography
import dev.baseio.discordjetpackcompose.utils.UIState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChannelList(
    modifier: Modifier = Modifier,
    serverState: UIState<ServerEntity>,
    onItemSelection: () -> Unit,
    onInviteButtonClick: (String) -> Unit,
) {
    AnimatedContent(
        targetState = serverState,
        transitionSpec = { fadeIn() with fadeOut() }
    ) { serverUIState ->
        when (serverUIState) {
            is UIState.Empty -> Unit
            is UIState.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
            )
            is UIState.Failure -> Text(text = "Failure")
            is UIState.Success -> Column(modifier = modifier) {
                val server = serverUIState.data

                val headerModifier = if (server.posterUri == null) {
                    Modifier
                } else {
                    Modifier.fillMaxHeight(0.25f)
                }

                val lazyState = rememberLazyListState()

                val shouldLiftCard by remember {
                    derivedStateOf {
                        lazyState.firstVisibleItemIndex > 0 || lazyState.firstVisibleItemScrollOffset > 0
                    }
                }

                val cardElevation by animateDpAsState(targetValue = if (shouldLiftCard) 4.dp else 0.dp)

                Surface(elevation = cardElevation) {
                    Column {
                        BoxWithConstraints(
                            modifier = headerModifier.fillMaxWidth(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            server.posterUri?.let { serverPosterUri ->
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(serverPosterUri)
                                        .placeholder(R.drawable.light_app_logo).crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(maxHeight.times(0.3f))
                                        .background(
                                            brush = Brush.verticalGradient(colors = listOf(
                                                Color.Black, Color.Transparent
                                            ), startY = with(LocalDensity.current) {
                                                -maxHeight
                                                    .times(
                                                        0.1f
                                                    )
                                                    .toPx()
                                            }),
                                        )
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, start = 16.dp, end = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (server.hasNitroSubscription) {
                                        // TODO: Show real Discord Nitro Icon here
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_nitro_verified),
                                            contentDescription = null,
                                            colorFilter = ColorFilter.tint(color = Color.White)
                                        )
                                    }
                                    Text(
                                        text = server.name,
                                        style = ChannelListTypography.h1.copy(color = Color.White),
                                        modifier = Modifier.padding(start = 8.dp),
                                    )
                                }
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                val channelGroups by remember(server.channels) {
                    mutableStateOf(server.channels.sortedBy { it.category }.groupBy { it.category })
                }
                var currentSelectedItemId: String? by remember { mutableStateOf(null) }
                LazyColumn(state = lazyState) {
                    item {
                        ServerInviteButton(onInviteButtonClick = {
                            onInviteButtonClick(server.id)
                        })
                    }
                    channelGroups.forEach { (category, channelList) ->
                        item {
                            ChannelListGroup(category = category,
                                channelGroup = channelList,
                                currentSelectedItemId = currentSelectedItemId,
                                onItemClick = { newId ->
                                    currentSelectedItemId = newId
                                    onItemSelection()
                                })
                        }
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun ServerInviteButton(onInviteButtonClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
        shape = RoundedCornerShape(4.dp),
        onClick = onInviteButtonClick,
    ) {
        Text(
            text = stringResource(R.string.channel_list_header_btn_txt),
            style = ChannelListTypography.button,
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChannelScreenPreview() {
    ChannelList(onItemSelection = {}, serverState = UIState.Loading, onInviteButtonClick = {})
}