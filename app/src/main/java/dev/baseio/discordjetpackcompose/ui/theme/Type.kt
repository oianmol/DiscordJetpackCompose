package dev.baseio.discordjetpackcompose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.baseio.discordjetpackcompose.R

val UniSansFontFamily = FontFamily(
    Font(R.font.unisans_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(R.font.unisans_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.unisans_book, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(R.font.unisans_book_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.unisans_heavy, weight = FontWeight.Black, style = FontStyle.Normal),
    Font(R.font.unisans_heavy_italic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.unisans_light, weight = FontWeight.Light, style = FontStyle.Normal),
    Font(R.font.unisans_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.unisans_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(R.font.unisans_regular_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.unisans_semibold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
    Font(R.font.unisans_semibold_italic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(R.font.unisans_thin, weight = FontWeight.Thin, style = FontStyle.Normal),
    Font(R.font.unisans_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = UniSansFontFamily
)

val DirectMessageListTypography = Typography(
    defaultFontFamily = UniSansFontFamily,
    h5 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontSize = 12.sp
    ),
)

val ChannelListTypography = Typography(
    defaultFontFamily = UniSansFontFamily,
    h1 = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = 20.sp,
        shadow = Shadow(
            offset = Offset(x = 2f, y = 4f),
            blurRadius = 4f
        ),
        letterSpacing = 2.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
)

val ServerInfoTypography = Typography(
    defaultFontFamily = UniSansFontFamily,
    h1 = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = 28.sp,
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontSize = 14.sp,
    ),
    caption = TextStyle(
        fontSize = 12.sp,
    ),
    overline = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Color.Magenta
    ),
)

val DiscordDialogTypography = Typography(
    defaultFontFamily = UniSansFontFamily,
    h6 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)