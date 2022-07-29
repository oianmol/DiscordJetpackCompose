package dev.baseio.discordjetpackcompose.ui.routes.onboarding.commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordDialogTypography
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.theme.dialog_text_grey
import dev.baseio.discordjetpackcompose.ui.theme.onboarding_button_blue
import dev.baseio.discordjetpackcompose.ui.theme.white

@Composable
fun DiscordDialog(
    modifier: Modifier = Modifier,
    show: Boolean,
    shape: Shape = RectangleShape,
    backgroundColor: Color = DiscordColorProvider.colors.background,
    titleTextProvider: () -> Int,
    subTitleTextProvider: () -> Int,
    properties: DialogProperties = DialogProperties(),
    onDismissRequest: () -> Unit,
    buttonRowBackgroundColor: Color = DiscordColorProvider.colors.surface,
    confirmActionButtonTextProvider: () -> Int,
    onClickCancelButton: () -> Unit,
    onClickConfirmActionButton: () -> Unit
) {
    if (show) {
        Dialog(
            onDismissRequest = { onDismissRequest() },
            properties = properties
        ) {
            Surface(
                modifier = modifier,
                shape = shape,
                color = backgroundColor
            ) {
                Column(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    DialogTitle(titleTextProvider = { titleTextProvider() })
                    Divider(color = Color.LightGray, thickness = 0.15.dp)
                    DialogSubtitle(subTitleTextProvider = { subTitleTextProvider() })
                    Divider(color = Color.LightGray, thickness = 0.15.dp)
                    DialogOptions(
                        buttonRowBackgroundColor = buttonRowBackgroundColor,
                        confirmActionButtonTextProvider = { confirmActionButtonTextProvider() },
                        onClickCancelButton = { onClickCancelButton() },
                        onClickConfirmActionButton = { onClickConfirmActionButton() }
                    )
                }
            }
        }
    }
}


@Composable
fun DialogTitle(
    titleTextProvider: () -> Int
) {
    Text(
        text = stringResource(id = titleTextProvider()),
        style = DiscordDialogTypography.h6,
        modifier = Modifier
            .padding(bottom = 16.dp, top = 10.dp)
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun DialogSubtitle(
    subTitleTextProvider: () -> Int
) {
    Text(
        text = stringResource(id = subTitleTextProvider()),
        style = DiscordDialogTypography.subtitle1,
        modifier = Modifier.padding(
            top = 12.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 24.dp
        )
    )
}

@Composable
fun DialogOptions(
    buttonRowBackgroundColor: Color,
    confirmActionButtonTextProvider: () -> Int,
    onClickCancelButton: () -> Unit,
    onClickConfirmActionButton: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .background(buttonRowBackgroundColor)
            .fillMaxWidth()
    ) {
        DialogNegativeAction(
            onClickCancelButton = { onClickCancelButton() }
        )
        DialogPositiveAction(
            confirmActionButtonTextProvider = { confirmActionButtonTextProvider() },
            onClickConfirmActionButton = { onClickConfirmActionButton() }
        )
    }
}

@Composable
fun DialogNegativeAction(
    onClickCancelButton: () -> Unit
) {
    Button(
        onClick = { onClickCancelButton() },
        elevation = ButtonDefaults.elevation(0.dp),
        modifier = Modifier.padding(end = 16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
    ) {
        Text(
            text = stringResource(R.string.cancel),
            style = TextStyle(
                fontWeight = FontWeight.Black,
                fontSize = 14.sp,
                color = dialog_text_grey
            )
        )
    }
}

@Composable
fun DialogPositiveAction(
    confirmActionButtonTextProvider: () -> Int,
    onClickConfirmActionButton: () -> Unit
) {
    Button(
        onClick = { onClickConfirmActionButton() },
        elevation = ButtonDefaults.elevation(2.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = onboarding_button_blue),
        modifier = Modifier
            .padding(end = 10.dp)
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = stringResource(id = confirmActionButtonTextProvider()),
            style = TextStyle(
                fontWeight = FontWeight.Black,
                fontSize = 14.sp,
                color = white
            )
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun DiscordDialogPreview() {
    DiscordJetpackComposeTheme {
        DiscordDialog(
            titleTextProvider = { R.string.password_manager },
            subTitleTextProvider = { R.string.password_manager_text },
            confirmActionButtonTextProvider = { R.string.open_settings },
            onDismissRequest = {},
            onClickCancelButton = { },
            onClickConfirmActionButton = { },
            show = true
        )
    }
}


