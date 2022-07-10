package dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.commonui.CenteredTitleSubtitle
import dev.baseio.discordjetpackcompose.ui.theme.design_default_color_background

@Composable
fun WelcomeScreen(composeNavigator: ComposeNavigator) {
    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(color = design_default_color_background)
        sysUiController.setNavigationBarColor(color = design_default_color_background)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(DiscordColorProvider.colors.background),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Header()
        Image(
            painter = painterResource(id = R.drawable.welcomelogo),
            contentDescription = null,
            modifier = Modifier.padding(32.dp)
        )

        CenteredTitleSubtitle(
          modifier = Modifier.padding(start = 16.dp, end = 16.dp),
          title = R.string.welcome_to_discord,
          subtitle = R.string.welcome_message
        )

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(8.dp))
            RegisterButton(navigator = composeNavigator)
            LoginButton(navigator = composeNavigator)
        }
    }
}

@Composable
private fun Header() {
    Text(
      modifier = Modifier.padding(top = 32.dp).fillMaxWidth(),
      text = stringResource(R.string.discord),
      style = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
      ),
      textAlign = TextAlign.Center,
      color = DiscordColorProvider.colors.onSurface
    )
}


@Composable
private fun LoginButton(navigator: ComposeNavigator) {
    Button(
        onClick = { navigator.navigate(DiscordScreen.Login.name) },
        colors = buttonColors(
            backgroundColor = colorResource(id = R.color.material_grey_600),
            contentColor = colorResource(
                id = R.color.white
            )
        ), modifier = Modifier.fillMaxWidth(0.9f)
    ) {
        Text(text = stringResource(id = R.string.login))
    }
}

@Composable
private fun RegisterButton(navigator: ComposeNavigator) {
    Button(
        onClick = { navigator.navigate(DiscordScreen.Register.name) },
        colors = buttonColors(
            backgroundColor = colorResource(id = R.color.brand), contentColor = colorResource(
                id = R.color.white
            )
        ), modifier = Modifier.fillMaxWidth(0.9f)
    ) {
        Text(text = stringResource(id = R.string.register))
    }
}