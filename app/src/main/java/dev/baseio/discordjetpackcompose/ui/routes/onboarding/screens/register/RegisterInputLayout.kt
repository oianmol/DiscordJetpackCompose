package dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.entities.CountryEntity
import dev.baseio.discordjetpackcompose.ui.utils.Strings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

private const val DEFAULT_COUNTRY_CODE = "+91"

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun RegisterInputLayout(
  selectedOption: RegistrationType,
  coroutineScope: CoroutineScope,
  bottomSheetState: ModalBottomSheetState,
  selectedCountry: CountryEntity?
) {
  val keyboardFocusRequester = remember { FocusRequester() }
  val keyboardController = LocalSoftwareKeyboardController.current
  var textFieldValue by remember { mutableStateOf("") }

  LaunchedEffect(Unit) {
    this.coroutineContext.job.invokeOnCompletion {
      keyboardFocusRequester.requestFocus()
    }
  }

  Row(
    modifier = Modifier
      .height(55.dp)
      .fillMaxWidth()
  ) {

    AnimatedVisibility(visible = selectedOption == RegistrationType.Phone) {
      RegistrationTextField(
        modifier = Modifier
          .width(120.dp)
          .padding(end = 8.dp),
        onClick = {
          coroutineScope.launch {
            keyboardController?.hide()
            bottomSheetState.show()
          }
        },
        isEnabled = false,
        value = selectedCountry?.phoneCountryCode ?: DEFAULT_COUNTRY_CODE,
        onValueChange = {},
        label = stringResource(id = Strings.country_code)
      )
    }

    RegistrationTextField(
      modifier = Modifier
        .fillMaxWidth(),
      value = textFieldValue,
      onValueChange = { textFieldValue = it },
      label = if (selectedOption == RegistrationType.Phone) {
        stringResource(
          id = Strings.phone_number
        )
      } else {
        stringResource(id = Strings.email)
      },
      focusRequester = keyboardFocusRequester
    )
  }
}