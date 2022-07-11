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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.theme.discord_dialog_bg
import dev.baseio.discordjetpackcompose.ui.theme.discord_dialog_button_bg
import dev.baseio.discordjetpackcompose.ui.theme.discord_dialog_button_row_bg
import dev.baseio.discordjetpackcompose.ui.theme.discord_dialog_cancel_button_text
import dev.baseio.discordjetpackcompose.ui.theme.white

@Composable
fun DiscordDialog(
    modifier: Modifier = Modifier,
    show: Boolean,
    shape: Shape = RectangleShape,
    backgroundColor: Color = discord_dialog_bg,
    buttonRowBackgroundColor: Color = discord_dialog_button_row_bg,
    titleTextProvider: () -> Int,
    subTitleTextProvider: () -> Int,
    confirmActionButtonTextProvider: () -> Int,
    properties: DialogProperties = DialogProperties(),
    onDismissRequest: () -> Unit,
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
                    Text(
                        text = stringResource(id = titleTextProvider()),
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier
                            .padding(bottom = 16.dp, top = 10.dp)
                            .padding(horizontal = 16.dp)
                    )
                    Divider(color = Color.LightGray, thickness = 0.15.dp)
                    Text(
                        text = stringResource(id = subTitleTextProvider()),
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.padding(
                            top = 12.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 24.dp
                        )
                    )
                    Divider(color = Color.LightGray, thickness = 0.15.dp)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .background(buttonRowBackgroundColor)
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = { onClickCancelButton() },
                            elevation = ButtonDefaults.elevation(0.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                        ) {
                            Text(
                                text = stringResource(R.string.cancel),
                                style = TextStyle(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp,
                                    color = discord_dialog_cancel_button_text
                                )
                            )
                        }
                        Button(
                            onClick = { onClickConfirmActionButton() },
                            elevation = ButtonDefaults.elevation(2.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = discord_dialog_button_bg),
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
                }
            }
        }
    }
}


