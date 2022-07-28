package dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.entities.search.SearchFilter
import dev.baseio.discordjetpackcompose.ui.components.DiscordDivider
import dev.baseio.discordjetpackcompose.ui.components.DiscordIcon
import dev.baseio.discordjetpackcompose.ui.components.DiscordText
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.theme.DiscordSurface
import dev.baseio.discordjetpackcompose.ui.theme.SearchSheetDialogTypography
import dev.baseio.discordjetpackcompose.ui.theme.contentColorFor

@Composable
fun SearchSheetTextField(
    modifier: Modifier,
    query: TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit,
    filters: List<SearchFilter>,
    onFilterChanged: (SearchFilter) -> Unit,
    placeholder: String,
    onNavigateUp: () -> Unit,
) {
    var isFilterDialogDisplayed by remember { mutableStateOf(false) }

    DiscordSurface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        elevation = 1.dp
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = onQueryChanged,
            trailingIcon = {
                IconButton(onClick = {
                    isFilterDialogDisplayed = true
                }) {
                    DiscordIcon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = null
                    )
                }
            },
            placeholder = {
                DiscordText(
                    text = placeholder,
                    color = DiscordColorProvider.colors.onSurface.copy(alpha = LocalContentAlpha.current)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = DiscordColorProvider.colors.onSurface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = DiscordColorProvider.colors.primary
            ),
            leadingIcon = {
                IconButton(onClick = onNavigateUp) {
                    DiscordIcon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
    }

    if (isFilterDialogDisplayed) {
        Dialog(onDismissRequest = {
            isFilterDialogDisplayed = false
        }) {
            DiscordSurface(
                shape = RoundedCornerShape(4.dp),
                contentColor = DiscordColorProvider.colors.contentColorFor(DiscordColorProvider.colors.surface)
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.search_sheet_filter_dialog_title),
                        style = SearchSheetDialogTypography.h1,
                        modifier = Modifier.padding(16.dp),
                        color = DiscordColorProvider.colors.onSurface.copy(alpha = 0.5f)
                    )
                    DiscordDivider()
                    filters.forEachIndexed { index, filter ->
                        Column {
                            Text(
                                text = filter.title,
                                style = SearchSheetDialogTypography.body1,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onFilterChanged(filter)
                                        isFilterDialogDisplayed = false
                                    }
                                    .padding(16.dp)
                            )
                            if (filters.lastIndex != index) {
                                DiscordDivider()
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(0.15f)),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {
                            isFilterDialogDisplayed = false
                        }, modifier = Modifier.padding(vertical = 4.dp)) {
                            DiscordText(
                                text = stringResource(R.string.search_sheet_filter_dialog_cancel_btn_txt),
                                style = SearchSheetDialogTypography.body1,
                                modifier = Modifier.padding(horizontal = 8.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SearchSheetTextFieldPreview() {
    DiscordJetpackComposeTheme {
        SearchSheetTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .wrapContentSize(),
            query = TextFieldValue(),
            onQueryChanged = {},
            filters = SearchFilter.values().toList(),
            onFilterChanged = {},
            placeholder = stringResource(R.string.search_sheet_topbar_placeholder),
            onNavigateUp = {}
        )
    }
}