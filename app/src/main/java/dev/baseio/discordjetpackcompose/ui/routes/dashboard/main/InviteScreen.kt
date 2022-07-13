package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.register.AuthTextField
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.Typography


@Composable
fun InviteScreen(composeNavigator: ComposeNavigator) {

    val scaffoldState = rememberScaffoldState()

    val sysUiController = rememberSystemUiController()
    val colors = DiscordColorProvider.colors
    SideEffect {
        sysUiController.setSystemBarsColor(color = colors.discordBackgroundOne)
        sysUiController.setNavigationBarColor(color = colors.discordBackgroundOne)
    }

    var inviteLink by remember {
        mutableStateOf("")
    }

    DiscordScaffold(
        modifier = Modifier.statusBarsPadding(),
        scaffoldState = scaffoldState,
        topAppBar = {

        },
        backgroundColor = colors.discordBackgroundOne,
        navigator = composeNavigator
    ) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = stringResource(id = R.string.invite_heading),
                style = Typography.h2.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                ),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.invite_subheading),
                style = Typography.subtitle1.copy(
                    fontSize = 14.sp
                ), textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            AuthTextField(
                value = inviteLink, onValueChange = {
                    inviteLink = it
                },
                label =
                stringResource(id = R.string.invite_link),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )
            InviteFormatComponent()
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.brand),
                    contentColor = colorResource(
                        id = R.color.white
                    )
                ), modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.invite_button))
            }
        }
    }
}

@Composable
fun InviteFormatComponent() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.invite_format_heading),
            style = Typography.body2.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.invite_format_1))
        Text(text = stringResource(id = R.string.invite_format_2))
        Text(text = stringResource(id = R.string.invite_format_3))
        Spacer(modifier = Modifier.height(24.dp))

    }
}