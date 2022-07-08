package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.theme.DirectMessageListTypography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DirectMessageList(
    modifier: Modifier = Modifier,
    openNewDMScreen: () -> Unit,
    openSearchScreen: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        val lazyState = rememberLazyListState()
        val shouldLiftCard by remember {
            derivedStateOf {
                lazyState.firstVisibleItemIndex > 0 || lazyState.firstVisibleItemScrollOffset > 0
            }
        }
        val cardElevation by animateDpAsState(targetValue = if (shouldLiftCard) 1.dp else 0.dp)
        Card(elevation = cardElevation) {
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp, end = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.dm_list_screen_title),
                        style = DirectMessageListTypography.h6
                    )
                    IconButton(onClick = openNewDMScreen) {
                        Icon(
                            imageVector = Icons.Default.AddComment, contentDescription = null
                        )
                    }
                }
                Surface(
                    modifier = Modifier.padding(bottom = 16.dp, end = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colors.surface.copy(alpha = 0.5f),
                    onClick = openSearchScreen,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.dm_list_screen_search_hint),
                            style = DirectMessageListTypography.body1,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                        )
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
        LazyColumn(state = lazyState) {}
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DirectMessageScreenPreview() {
    DirectMessageList(openNewDMScreen = {}, openSearchScreen = {})
}