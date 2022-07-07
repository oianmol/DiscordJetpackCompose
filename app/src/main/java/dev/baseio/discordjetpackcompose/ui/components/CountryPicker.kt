package dev.baseio.discordjetpackcompose.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.models.Country
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.utils.simpleVerticalScrollbar
import dev.baseio.discordjetpackcompose.utils.Constants
import dev.baseio.discordjetpackcompose.utils.readAssetFile
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryPicker(
    bottomSheetScaffoldState: ModalBottomSheetState,
    backgroundContent: @Composable () -> Unit,
    onCountrySelected: (Country) -> Unit,
    countryList: List<Country>? = LocalContext.current.readAssetFile(Constants.CountriesAssetFilePath)
) {
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = bottomSheetScaffoldState.isVisible) {
        coroutineScope.launch { bottomSheetScaffoldState.hide() }
    }
    ModalBottomSheetLayout(
        sheetState = bottomSheetScaffoldState, sheetContent = {
            Box(modifier = Modifier.fillMaxSize()) {
                var searchQuery by remember { mutableStateOf("") }
                Column {
                    SearchBox(
                        currentValue = searchQuery,
                        onValueChanged = { updatedQuery: String -> searchQuery = updatedQuery },
                    )
                    val lazyListState = rememberLazyListState()
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.simpleVerticalScrollbar(
                            state = lazyListState,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                        ),
                    ) {
                        countryList?.filter { country ->
                            country.name.contains(
                                searchQuery, ignoreCase = true
                            )
                        }?.let { nnCountryList ->
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
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { onCountrySelected() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = countryName)
        Text(text = countryCode, modifier = Modifier.alpha(ContentAlpha.medium))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchBox(currentValue: String, onValueChanged: (String) -> Unit) {
    val keyboardManager = LocalSoftwareKeyboardController.current
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.05f)),
        value = currentValue,
        onValueChange = onValueChanged,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
            )
        },
        placeholder = { Text(text = stringResource(id = R.string.country_picker_hint)) },
        trailingIcon = {
            AnimatedVisibility(visible = currentValue.isNotBlank()) {
                IconButton(onClick = { onValueChanged("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { keyboardManager?.hide() }),
        shape = RoundedCornerShape(6.dp)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true)
@Composable
private fun CountryPickerPreview() {
    DiscordJetpackComposeTheme {
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()
        CountryPicker(bottomSheetScaffoldState = sheetState, backgroundContent = {
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable {
                    coroutineScope.launch { sheetState.show() }
                }) {
                Text(text = "Click anywhere to open the country picker...")
            }
        }, onCountrySelected = {})
    }
}