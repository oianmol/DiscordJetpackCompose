package dev.baseio.discordjetpackcompose.ui.routes.dashboard.createServer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.theme.Typography
import dev.baseio.discordjetpackcompose.ui.theme.create_server_card_bg

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateServerCard(
    textProvider: () -> Int,
    iconProvider: () -> Int,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp),
        onClick = { onClick() },
        color = create_server_card_bg
    ) {
        Row(
            modifier = Modifier
                .padding(start = 6.dp, end = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(
                        id = if (isSystemInDarkTheme()) {
                            R.drawable.dark_app_logo
                        } else {
                            R.drawable.light_app_logo
                        }
                    ),
                    contentDescription = stringResource(R.string.server_type_icon),
                    modifier = Modifier.size(75.dp)
                )
                Text(
                    text = stringResource(id = textProvider()),
                    style = Typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = stringResource(R.string.arrow_forward)
            )
        }
    }
}

@Preview
@Composable
private fun CreateServerCardPreview() {
    Column {
        CreateServerCard(
            textProvider = { R.string.friends },
            iconProvider = { R.drawable.dark_app_logo },
            onClick = {}
        )
    }
}

