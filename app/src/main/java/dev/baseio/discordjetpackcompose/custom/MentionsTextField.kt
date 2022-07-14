package dev.baseio.discordjetpackcompose.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    val hashTagPattern: Pattern = Pattern.compile("\\B(\\#[a-zA-Z0-9._]+\\b)(?!;)")

    val mentionTagPattern: Pattern = Pattern.compile("\\B(\\@[a-zA-Z0-9._]+\\b)(?!;)")
}


@Composable
fun MentionsTextField(
    onSpanUpdate: (String, List<SpanInfos>, TextRange) -> Unit,
) {
    var mentionText by remember {
        mutableStateOf(TextFieldValue())
    }
    val spans =
        extractSpans(mentionText.text, listOf(urlPattern, mentionTagPattern, hashTagPattern))
    val annotatedString = buildAnnotatedString(mentionText.text, spans)
    onSpanUpdate(mentionText.text, spans, mentionText.selection)
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
        ), modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@Composable
fun MentionsText(mentionText: String) {
    val spans =
        extractSpans(mentionText, listOf(urlPattern, mentionTagPattern, hashTagPattern))
    val annotatedString = buildAnnotatedString(mentionText, spans)

    ClickableText(text = annotatedString,
        modifier = Modifier
            .padding(16.dp), onClick = { offset ->
            annotatedString.getStringAnnotations(
                start = offset,
                end = offset
            )
                .firstOrNull()?.let { annotation ->
                    // If yes, we log its value
                    Timber.e("Clicked ${annotation.item}")
                }

        })
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
) {
    var range = TextRange(start, end)
}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewMentionsTF() {
    DiscordJetpackComposeTheme {
        DiscordScaffold(scaffoldState = rememberScaffoldState()) {
            var mentionText by remember {
                mutableStateOf("")
            }
            var spanInfoList by remember {
                mutableStateOf(listOf<SpanInfos>())
            }
            var currentlyEditing by remember {
                mutableStateOf<SpanInfos?>(null)
            }

            Column(Modifier.padding(it)) {
                LazyColumn(content = {
                    items(spanInfoList) { span ->
                        ListItem(text = {
                            Text(text = span.spanText)
                        }, secondaryText = {
                            if (currentlyEditing?.spanText == span.spanText) {
                                Text(text = "Currently Editing")
                            } else {
                                Text(text = span.tag)
                            }
                        })
                    }
                })

                MentionsTextField { text, spans, range ->
                    spanInfoList = spans
                    mentionText = text
                    spanInfoList.firstOrNull { infos ->
                        range.intersects(infos.range) || range.end == infos.range.end
                    }?.let { infos ->
                        currentlyEditing = infos
                    } ?: kotlin.run {
                        currentlyEditing = null
                    }
                }

                MentionsText(mentionText = mentionText)
            }
        }
    }
}