package dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.entities.CountryEntity
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordRoute
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.commonui.OnboardingScreensButton
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.utils.Strings
import dev.baseio.discordjetpackcompose.viewmodels.RegistrationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegisterScreen(
    composeNavigator: ComposeNavigator,
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {

    val sysUiController = rememberSystemUiController()
    val colors = DiscordColorProvider.colors
    SideEffect {
        sysUiController.setSystemBarsColor(color = colors.discordBackgroundOne)
        sysUiController.setNavigationBarColor(color = colors.background)
    }


    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = Hidden)
    var selectedCountry: CountryEntity? by remember { mutableStateOf(null) }
    var countrySearchQuery by remember { mutableStateOf("") }

    var selectedOption: RegistrationType by remember { mutableStateOf(RegistrationType.Phone) }
    val onSelectionChange = { type: RegistrationType -> selectedOption = type }

    DiscordScaffold(
        navigator = composeNavigator,
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val backgroundImage =
                if (isSystemInDarkTheme()) R.drawable.background_image_dark else R.drawable.background_image_light
            Image(
                painter = painterResource(id = backgroundImage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(id = Strings.enter_phone_or_email),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                )

                RegistrationTypeSelector(
                    modifier = Modifier,
                    selectedOption = selectedOption,
                    onSelectionChange = onSelectionChange
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                )

                RegisterInputLayout(
                    selectedOption = selectedOption,
                    coroutineScope = coroutineScope,
                    bottomSheetState = sheetState,
                    selectedCountry = selectedCountry
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 8.dp),
                    text = stringResource(id = Strings.view_privacy_policy),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = DiscordColorProvider.colors.linkColor
                    ),
                    textAlign = TextAlign.Start
                )

                OnboardingScreensButton(
                    buttonTextProvider = { Strings.next },
                    modifier = Modifier.padding(vertical = 16.dp),
                    buttonWidthFraction = 1f,
                    onClick = { composeNavigator.navigate(DiscordRoute.Dashboard.name) }
                )
            }
        }
    }

    CountryPicker(
        sheetState = sheetState,
        backgroundContent = {},
        onCountrySelected = {
            selectedCountry = it
            coroutineScope.launch {
                sheetState.hide()
            }
        },
        countryList = registrationViewModel.filteredCountryList?.filter {
            it.name.contains(
                other = countrySearchQuery,
                ignoreCase = true
            )
        },
        countrySearchQuery = countrySearchQuery,
        onQueryUpdated = { updatedQuery -> countrySearchQuery = updatedQuery }
    )
}