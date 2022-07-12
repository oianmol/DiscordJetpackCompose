package dev.baseio.discordjetpackcompose.custom

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.AT_THE_RATE
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.HASH_TAG
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.URL_TAG
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.hashTagPattern
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.mentionTagPattern
import dev.baseio.discordjetpackcompose.custom.MentionsPatterns.urlPattern
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
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

    val hashTagPattern: Pattern = Pattern.compile(".*?\\s(#\\w+).*?")

    val mentionTagPattern: Pattern = Pattern.compile(".*?\\s(@\\w+).*?")
}


@Composable
fun MentionsTextField() {
    var mentionText by remember {
        mutableStateOf("")
    }
    var layoutResult by remember {
        mutableStateOf<TextLayoutResult?>(null)
    }

    val spans =
        extractSpans(mentionText, listOf<Pattern>(urlPattern, mentionTagPattern, hashTagPattern))

    val annotatedString = buildAnnotatedString(mentionText, spans)

    BasicTextField(value = annotatedString.text, onValueChange = { update ->
        mentionText = update
    }, onTextLayout = { textLayoutResult ->
        layoutResult = textLayoutResult
    }, modifier = Modifier.pointerInput(Unit) {
        detectTapGestures { offsetPosition ->
            layoutResult?.let { textLayoutResult ->
                val position = textLayoutResult.getOffsetForPosition(offsetPosition)
                annotatedString.getStringAnnotations(position, position).firstOrNull()
                    ?.let { result ->
                        when (result.tag) {
                            URL_TAG -> {
                                // handle uri
                                result.item
                            }
                            HASH_TAG -> {
                                //handle click on hashtag
                                result.item
                            }
                            AT_THE_RATE -> {
                                //handle click on mention
                                result.item
                            }
                            else -> {
                                //handle text click
                            }
                        }
                    }
            }
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
                textDecoration = TextDecoration.Underline
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