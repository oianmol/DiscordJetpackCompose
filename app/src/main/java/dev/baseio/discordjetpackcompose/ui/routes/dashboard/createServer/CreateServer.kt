package dev.baseio.discordjetpackcompose.ui.routes.dashboard.createServer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.DiscordAppBar
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.commonui.CenteredTitleSubtitle
import dev.baseio.discordjetpackcompose.ui.theme.Typography

@Composable
fun CreateServer(
    composeNavigator: ComposeNavigator
) {
    val scaffoldState = rememberScaffoldState()
    DiscordScaffold(
        scaffoldState = scaffoldState,
        topAppBar = {
            DiscordAppBar(
                navigationIcon = {
                    IconButton(onClick = { composeNavigator.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = stringResource(R.string.clear),
                            modifier = Modifier.padding(start = 8.dp),
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenteredTitleSubtitle(
                title = R.string.create_your_server,
                subtitle = R.string.create_your_server_subtitle
            )
            Spacer(modifier = Modifier.size(8.dp))
            LazyColumn(
                modifier = Modifier.fillMaxHeight(0.83f)
            ) {
                items(1) {
                    CreateServerCard(
                        textProvider = { R.string.create_your_server },
                        onClick = {},
                        iconProvider = { R.drawable.dark_app_logo }
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.start_from_a_template),
                        style = Typography.body1.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }
                itemsIndexed(ServerTemplates.type) { _, item ->
                    CreateServerCard(
                        textProvider = { item.textProvider },
                        onClick = {},
                        iconProvider = { item.iconProvider }
                    )
                }
            }
            HaveAnInviteBottomCard(onClick = {})
        }
    }
}

@Composable
fun HaveAnInviteBottomCard(
    onClick: () -> Unit
) {
    Text(
        text = stringResource(R.string.have_an_invite_already),
        style = Typography.h5.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF464646))
    ) {
        Text(
            text = stringResource(R.string.have_an_invite_already),
            style = Typography.body1.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}


