package dev.baseio.discordjetpackcompose.ui.routes.dashboard.recentmentions

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.theme.Typography
import dev.baseio.discordjetpackcompose.ui.theme.contentColorFor

//TODO 11/8/2022 Add support for light/dark mode!
@Composable
fun RecentMentionsScreen() {
    val scaffoldState = rememberScaffoldState()
    val sysUiController = rememberSystemUiController()
    val colors = DiscordColorProvider.colors
    SideEffect {
        sysUiController.setSystemBarsColor(color = colors.appBarColor2)
        sysUiController.setNavigationBarColor(color = colors.background)
    }
    DiscordScaffold(
      modifier = Modifier.statusBarsPadding(),
      scaffoldState = scaffoldState,
      topAppBar = {
        RecentTopAppBar()
    }) {
        Column(Modifier.padding(it), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(32.dp))
            StartConversationsText()
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.recentlogo),
                contentDescription = null, modifier = Modifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            Banner()
        }
    }
}

@Composable
private fun Banner() {
    Surface(
        color = Color.Black.copy(alpha = 0.3f),
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(12)
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.pro_tip),
                style = Typography.subtitle1.copy(color = Color(0xff379a55)),
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = stringResource(id = R.string.whenever_mentions),
                style = Typography.subtitle2.copy(color = Color.White),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun StartConversationsText() {
    Text(
        text = stringResource(id = R.string.startup_conversations),
        modifier = Modifier
            .padding(16.dp)
            .alpha(0.6f), textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun RecentTopAppBar() {
    TopAppBar(backgroundColor = DiscordColorProvider.colors.appBarColor2,
        contentColor = DiscordColorProvider.colors.contentColorFor(DiscordColorProvider.colors.appBarColor2),
        title = {
            Column {
                Text(
                    text = stringResource(id = R.string.recent_mentions),
                    style = Typography.subtitle1.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(text = stringResource(id = R.string.all_servers), style = Typography.subtitle2)
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Sort, contentDescription = null)
            }
        })
}


@Preview
@Composable
fun PreviewRecentMentionsScreen() {
    DiscordJetpackComposeTheme {
        RecentMentionsScreen()
    }
}