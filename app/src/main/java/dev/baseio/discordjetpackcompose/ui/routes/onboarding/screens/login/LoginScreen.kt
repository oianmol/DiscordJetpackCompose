package dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordRoute
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.commonui.CenteredTitleSubtitle
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.commonui.DiscordDialog
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.register.AuthTextField
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.utils.Strings
import dev.baseio.discordjetpackcompose.viewmodels.LoginScreenViewModel
import kotlinx.coroutines.job

@Composable
fun LoginScreen(composeNavigator: ComposeNavigator) {
    val scaffoldState = rememberScaffoldState()
    val viewModel = LoginScreenViewModel()

    val sysUiController = rememberSystemUiController()
    val colors = DiscordColorProvider.colors
    SideEffect {
        sysUiController.setSystemBarsColor(color = colors.discordBackgroundOne)
        sysUiController.setNavigationBarColor(color = colors.discordBackgroundOne)
    }

    val keyboardFocusRequester = remember { FocusRequester() }
    var emailField by remember { mutableStateOf("") }
    var passwordField by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        this.coroutineContext.job.invokeOnCompletion {
            keyboardFocusRequester.requestFocus()
        }
    }

    DiscordScaffold(scaffoldState = scaffoldState,
        navigator = composeNavigator, backgroundColor = colors.discordBackgroundOne) { paddingValues ->
        Column(
            Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenteredTitleSubtitle(title = R.string.welcome_back, subtitle = R.string.login_excited)
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
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
                LinkText(Strings.forgot_password) {}
                Spacer(modifier = Modifier.height(4.dp))
                LinkText(Strings.use_pass_manager) { viewModel.onOpenDialogClicked() }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    composeNavigator.navigate(DiscordRoute.Dashboard.name)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.brand),
                    contentColor = colorResource(
                        id = R.color.white
                    )
                ), modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text(text = stringResource(id = R.string.login))
            }

            val showDialogState: Boolean by viewModel.showDialog.collectAsState()
            DiscordDialog(
                titleTextProvider = { R.string.password_manager },
                subTitleTextProvider = { R.string.password_manager_text },
                confirmActionButtonTextProvider = { R.string.open_settings },
                onDismissRequest = {},
                onClickCancelButton = { viewModel.onDialogDismiss() },
                onClickConfirmActionButton = { viewModel.onDialogConfirm() },
                show = showDialogState
            )
        }
    }
}

@Composable
fun LinkText(
    id: Int,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable { onClick() },
        text = stringResource(id = id),
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = DiscordColorProvider.colors.linkColor
        ),
        textAlign = TextAlign.Start
    )
}


