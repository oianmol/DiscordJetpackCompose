package dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.theme.Typography

@Composable
fun WelcomeScreen(composeNavigator: ComposeNavigator) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Image(
            painter = painterResource(id = R.drawable.welcomelogo),
            contentDescription = null,
            modifier = Modifier.padding(8.dp)
        )

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            WelcomeToDiscord()
            Spacer(modifier = Modifier.height(4.dp))
            WelcomeDiscordMessage()
            Spacer(modifier = Modifier.height(8.dp))
            RegisterButton()
            LoginButton()
        }

    }
}


@Composable
private fun LoginButton() {
    Button(
        onClick = { },
        colors = buttonColors(
            backgroundColor = colorResource(id = R.color.material_grey_600),
            contentColor = colorResource(
                id = R.color.white
            )
        ), modifier = Modifier.fillMaxWidth(0.7f)
    ) {
        Text(text = stringResource(id = R.string.login))
    }
}

@Composable
private fun RegisterButton() {
    Button(
        onClick = { },
        colors = buttonColors(
            backgroundColor = colorResource(id = R.color.brand), contentColor = colorResource(
                id = R.color.white
            )
        ), modifier = Modifier.fillMaxWidth(0.7f)
    ) {
        Text(text = stringResource(id = R.string.register))
    }
}

@Composable
private fun WelcomeDiscordMessage() {
    Text(
        text = stringResource(id = R.string.welcome_message),
        style = Typography.subtitle1, textAlign = TextAlign.Center
    )
}

@Composable
private fun WelcomeToDiscord() {
    Text(
        text = stringResource(id = R.string.welcome_to_discord),
        style = Typography.h5.copy(fontWeight = FontWeight.Bold), textAlign = TextAlign.Center
    )
}
