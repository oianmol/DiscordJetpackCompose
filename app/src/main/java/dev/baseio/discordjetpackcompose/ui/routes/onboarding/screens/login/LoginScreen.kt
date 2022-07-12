package dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.commonui.OnboardingScreensButton
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
        sysUiController.setNavigationBarColor(color = colors.background)
    }

    val keyboardFocusRequester = remember { FocusRequester() }
    var emailField by remember { mutableStateOf("") }
    var passwordField by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        this.coroutineContext.job.invokeOnCompletion {
            keyboardFocusRequester.requestFocus()
        }
    }

    DiscordScaffold(
        scaffoldState = scaffoldState,
        navigator = composeNavigator
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val backgroundImage =
                if (isSystemInDarkTheme()) R.drawable.background_image_dark else R.drawable.background_image_light
            Image(
                painter = painterResource(id = backgroundImage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                Modifier.padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CenteredTitleSubtitle(
                    title = R.string.welcome_back,
                    subtitle = R.string.login_excited,
                    subtitleFontSize = 14.sp
                )
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
                        focusRequester = keyboardFocusRequester,
                        trailingIconOnClick = { emailField = "" }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    AuthTextField(
                        modifier = Modifier
                            .height(55.dp)
                            .fillMaxWidth(),
                        isPasswordTextFieldProvider = { true },
                        value = passwordField, onValueChange = {
                            passwordField = it
                        }, label = stringResource(id = Strings.password)
                    )
                    LinkText(Strings.forgot_password) {}
                    LinkText(Strings.use_pass_manager) { viewModel.onOpenDialogClicked() }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OnboardingScreensButton(
                    buttonTextProvider = { R.string.login },
                    modifier = Modifier.padding(12.dp),
                    buttonWidthFraction = 1f,
                    onClick = { composeNavigator.navigate(DiscordRoute.Dashboard.name) }
                )

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
}

@Composable
fun LinkText(
    id: Int,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .padding(top = 10.dp, start = 8.dp)
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


