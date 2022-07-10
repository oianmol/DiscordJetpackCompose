package dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.commonui.CenteredTitleSubtitle
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.register.AuthTextField
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.utils.Strings
import kotlinx.coroutines.job

@Composable
fun LoginScreen(composeNavigator: ComposeNavigator) {
    val scaffoldState = rememberScaffoldState()

    val keyboardFocusRequester = remember { FocusRequester() }
    var emailField by remember { mutableStateOf("") }
    var passwordField by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        this.coroutineContext.job.invokeOnCompletion {
            keyboardFocusRequester.requestFocus()
        }
    }

    DiscordScaffold(scaffoldState = scaffoldState, navigator = composeNavigator) { paddingValues ->
        Column(
            Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenteredTitleSubtitle(title = R.string.welcome_back, subtitle = R.string.login_excited)
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)) {
                AuthTextField(
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(),
                    value = emailField,
                    onValueChange = {
                        emailField = it
                    },
                    label = stringResource(id = Strings.email_or_phone),
                    focusRequester = keyboardFocusRequester
                )
                Spacer(modifier = Modifier.height(12.dp))
                AuthTextField(
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(),
                    value = passwordField, onValueChange = {
                        passwordField = it
                    }, label = stringResource(id = Strings.password)
                )
                LinkText(Strings.forgot_password)
                Spacer(modifier = Modifier.height(4.dp))
                LinkText(Strings.use_pass_manager)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.brand),
                    contentColor = colorResource(
                        id = R.color.white
                    )
                ), modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }
}

@Composable
fun LinkText(id: Int) {
    Text(
        modifier = Modifier
            .padding(top = 8.dp),
        text = stringResource(id = id),
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = DiscordColorProvider.colors.linkColor
        ),
        textAlign = TextAlign.Start
    )
}
