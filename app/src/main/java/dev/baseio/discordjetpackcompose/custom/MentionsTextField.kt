package dev.baseio.discordjetpackcompose.custom

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.AT_THE_RATE
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.HASH_TAG
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.URL_TAG
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.hashTagPattern
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.mentionTagPattern
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.urlPattern
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.register.contentColor
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import timber.log.Timber
import java.util.regex.Pattern

object MentionsPatterns {

    const val HASH_TAG = "HASH"
    const val URL_TAG = "URL"
    const val AT_THE_RATE = "AT_RATE"

    val urlPattern: Pattern = Pattern.compile(
        "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
        Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
    )

    val hashTagPattern: Pattern = Pattern.compile("\\B(\\#[a-zA-Z0-9]+\\b)(?!;)")

    val mentionTagPattern: Pattern = Pattern.compile("\\B(\\@[a-zA-Z0-9]+\\b)(?!;)")
}


@Composable
fun MentionsTextField() {
    var mentionText by remember {
        mutableStateOf(TextFieldValue())
    }
    var layoutResult by remember {
        mutableStateOf<TextLayoutResult?>(null)
    }
    val spans =
        extractSpans(mentionText.text, listOf(urlPattern, mentionTagPattern, hashTagPattern))
    Timber.e(spans.toString())
    val annotatedString = buildAnnotatedString(mentionText.text, spans)

    TextField(
        value = mentionText.copy(annotatedString = annotatedString),
        onValueChange = { update ->
            mentionText = update
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = contentColor(),
            disabledTextColor = contentColor(),
            cursorColor = DiscordColorProvider.colors.primary,
            backgroundColor = DiscordColorProvider.colors.secondaryBackground,
            focusedIndicatorColor = Color.Transparent, // hide the indicator
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            placeholderColor = contentColor()
        ), modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun buildAnnotatedString(
    mentionText: String,
    spans: List<SpanInfos>
) = buildAnnotatedString {
    append(mentionText)
    spans.forEach {
        addStyle(
            style = SpanStyle(
                color = DiscordColorProvider.colors.linkColor,
                textDecoration = if (it.tag == URL_TAG) TextDecoration.Underline else TextDecoration.None
            ),
            start = it.start,
            end = it.end
        )
        addStringAnnotation(
            tag = it.tag,
            annotation = it.spanText,
            start = it.start,
            end = it.end
        )
    }
}


fun extractSpans(text: String, patterns: List<Pattern>): List<SpanInfos> {
    val spans = arrayListOf<SpanInfos>()
    patterns.forEach { pattern ->
        val matcher = pattern.matcher(text)
        var matchStart: Int
        var matchEnd: Int

        while (matcher.find()) {
            matchStart = matcher.start(1)
            matchEnd = matcher.end()

            var checkText = text.substring(matchStart, matchEnd)

            when {
                checkText.startsWith("#") -> {
                    spans.add(SpanInfos(checkText, matchStart, matchEnd, HASH_TAG))
                }
                checkText.startsWith("@") -> {
                    spans.add(SpanInfos(checkText, matchStart, matchEnd, AT_THE_RATE))
                }
                else -> {
                    if (!checkText.startsWith("http://") && !checkText.startsWith("https://")) {
                        checkText = "https://$checkText"
                    }
                    spans.add(SpanInfos(checkText, matchStart, matchEnd, URL_TAG))
                }
            }
        }
    }

    return spans
}

data class SpanInfos(
    val spanText: String,
    val start: Int,
    val end: Int,
    val tag: String,
)


@Preview
@Composable
fun PreviewMentionsTF() {
    DiscordJetpackComposeTheme {
        DiscordScaffold(scaffoldState = rememberScaffoldState()) {
            Column(Modifier.padding(it)) {
                MentionsTextField()
            }
        }
    }
}