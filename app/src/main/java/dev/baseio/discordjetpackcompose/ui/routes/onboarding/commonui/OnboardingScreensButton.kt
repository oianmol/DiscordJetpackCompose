package dev.baseio.discordjetpackcompose.ui.routes.onboarding.commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.theme.onboarding_button_blue
import dev.baseio.discordjetpackcompose.ui.theme.onboarding_button_grey
import dev.baseio.discordjetpackcompose.ui.theme.white

@Composable
fun OnboardingScreensButton(
    modifier: Modifier = Modifier,
    buttonTextProvider: () -> Int,
    buttonBackgroundColor: Color = onboarding_button_blue,
    buttonWidthFraction: Float = 0.9f,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonBackgroundColor,
            contentColor = colorResource(id = R.color.white)
        ),
        modifier = modifier
            .fillMaxWidth(buttonWidthFraction)
    ) {
        Text(
            text = stringResource(id = buttonTextProvider()),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardingScreensButtonPreview() {
    DiscordJetpackComposeTheme {
        Column(
            modifier = Modifier.background(white),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OnboardingScreensButton(
                buttonTextProvider = { R.string.register },
                onClick = { }
            )
            OnboardingScreensButton(
                buttonTextProvider = { R.string.login },
                buttonBackgroundColor = onboarding_button_grey,
                onClick = { }
            )
        }
    }
}