package dev.baseio.discordjetpackcompose.ui.routes.onboarding.commonui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.ui.theme.Typography

@Composable
fun CenteredTitleSubtitle(title: Int, subtitle: Int) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Title(title)
        Spacer(modifier = Modifier.height(4.dp))
        SubTitle(subtitle)
    }
}

@Composable
private fun SubTitle(title: Int) {
    Text(
        text = stringResource(id = title),
        style = Typography.subtitle1, textAlign = TextAlign.Center
    )
}

@Composable
private fun Title(subtitle: Int) {
    Text(
        text = stringResource(id = subtitle),
        style = Typography.h5.copy(fontWeight = FontWeight.Bold), textAlign = TextAlign.Center
    )
}
