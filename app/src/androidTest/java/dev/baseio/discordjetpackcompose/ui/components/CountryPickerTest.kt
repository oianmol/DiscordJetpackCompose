package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dev.baseio.discordjetpackcompose.entities.CountryEntity
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.utils.readAssetFile
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterialApi::class)
class CountryPickerTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var sheetState: ModalBottomSheetState
    private var searchQuery: String by mutableStateOf("")

    @Before
    fun setUp() {
        sheetState = ModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        searchQuery = ""
    }

    @Test
    fun countryPicker_OnCountrySelect_UpdatesBackgroundContent() {
        // Start the app
        composeTestRule.setContent {
            DiscordJetpackComposeTheme {
                var currentCountry: CountryEntity? by remember { mutableStateOf(null) }
                val coroutineScope = rememberCoroutineScope()
                val context = LocalContext.current

                CountryPicker(sheetState = sheetState,
                    backgroundContent = {
                        Box(modifier = Modifier
                            .testTag(tag = "bgContent")
                            .clickable {
                                coroutineScope.launch { sheetState.show() }
                            }) {
                            Text(
                                text = "Selected country is: ${currentCountry?.name}",
                                modifier = Modifier.testTag(tag = "countryText")
                            )
                        }
                    },
                    onCountrySelected = { newCountry ->
                        currentCountry = newCountry
                    },
                    countryList = context.readAssetFile("countries.json"),
                    countrySearchQuery = searchQuery,
                    onQueryUpdated = { updatedQuery -> searchQuery = updatedQuery })
            }
        }

        composeTestRule.apply {
            // Expand/Open CountryPicker
            onNodeWithTag(testTag = "bgContent").performClick()

            // Click on item
            onNodeWithText(text = "Afghanistan").performClick()

            // Check if backgroundContent was updated
            onNodeWithTag(
                testTag = "countryText", useUnmergedTree = true
            ).assertTextContains(value = "Afghanistan", substring = true)
        }
    }

    @Test
    fun countryPicker_OnQueryUpdate_TogglesTrailingIconVisibility() {
        composeTestRule.setContent {
            DiscordJetpackComposeTheme {
                CountryPicker(sheetState = sheetState,
                    backgroundContent = {},
                    onCountrySelected = {},
                    countryList = listOf(),
                    countrySearchQuery = searchQuery,
                    onQueryUpdated = { updatedQuery -> searchQuery = updatedQuery })

                LaunchedEffect(Unit) {
                    sheetState.show()
                }
            }
        }
        composeTestRule.apply {
            val trailingIcon = onNodeWithContentDescription("countryPickerSearchBoxTrailingIcon")
            val searchBox = onNodeWithTag("countryPickerSearchBox")

            trailingIcon.assertDoesNotExist()
            searchBox.performTextInput("India")
            trailingIcon.assertExists()
            searchBox.performTextInput("")
            trailingIcon.assertDoesNotExist()
        }
    }
}