package dev.baseio.discordjetpackcompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.theme.Typography

@Composable
@Preview
fun WelcomeScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Image(painter = painterResource(id = R.drawable.welcomelogo), contentDescription = null)

        Column(Modifier.fillMaxWidth()) {
            WelcomeToDiscord()
            WelcomeDiscordMessage()
        }

    }
}

@Composable
fun WelcomeDiscordMessage() {
    Text(
        text = stringResource(id = R.string.welcome_message),
        style = Typography.h6
    )
}

@Composable
fun WelcomeToDiscord() {
    Text(
        text = stringResource(id = R.string.welcome_to_discord),
        style = Typography.h4.copy(fontWeight = FontWeight.Bold)
    )
}
