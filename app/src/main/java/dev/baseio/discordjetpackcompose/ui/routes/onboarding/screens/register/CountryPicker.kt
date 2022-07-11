package dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.entities.CountryEntity
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.SearchBox
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.utils.clickableWithRipple
import dev.baseio.discordjetpackcompose.ui.utils.simpleVerticalScrollbar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryPicker(
    sheetState: ModalBottomSheetState,
    backgroundContent: @Composable () -> Unit,
    onCountrySelected: (CountryEntity) -> Unit,
    countryList: List<CountryEntity>?,
    countrySearchQuery: String = "",
    onQueryUpdated: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }
    ModalBottomSheetLayout(
        sheetState = sheetState, sheetContent = {
            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    SearchBox(
                        currentValue = countrySearchQuery,
                        onValueChanged = { updatedQuery: String -> onQueryUpdated(updatedQuery) },
                    )
                    val lazyListState = rememberLazyListState()
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.simpleVerticalScrollbar(
                            state = lazyListState,
                            color = DiscordColorProvider.colors.onSurface.copy(alpha = 0.5f)
                        ),
                    ) {
                        countryList?.let { nnCountryList ->
                            items(nnCountryList.size) { index ->
                                CountryItem(
                                    countryName = nnCountryList[index].name,
                                    countryCode = nnCountryList[index].phoneCountryCode,
                                    onCountrySelected = { onCountrySelected(nnCountryList[index]) },
                                )
                            }
                        }
                    }
                }
            }
        }, sheetShape = RoundedCornerShape(0), scrimColor = Color.Black.copy(alpha = 0.32f)
    ) {
        backgroundContent()
    }
}

@Composable
private fun CountryItem(
    countryName: String,
    countryCode: String,
    onCountrySelected: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableWithRipple { onCountrySelected() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = countryName)
        Text(text = countryCode, modifier = Modifier.alpha(ContentAlpha.medium))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true)
@Composable
private fun CountryPickerPreview() {
    DiscordJetpackComposeTheme {
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()
        var currentQuery by remember { mutableStateOf("") }

        CountryPicker(sheetState = sheetState,
            backgroundContent = {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        coroutineScope.launch { sheetState.show() }
                    }) {
                    Text(text = stringResource(R.string.country_picker_preview_text))
                }
            },
            onCountrySelected = {},
            countryList = listOf(
                CountryEntity(name = "India", phoneCountryCode = "+91")
            ).filter { it.name.contains(other = currentQuery, ignoreCase = true) },
            countrySearchQuery = currentQuery,
            onQueryUpdated = { updatedQuery -> currentQuery = updatedQuery })
    }
}