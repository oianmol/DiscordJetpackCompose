package dev.baseio.discordjetpackcompose.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.discordjetpackcompose.models.Country
import dev.baseio.discordjetpackcompose.utils.Constants
import dev.baseio.discordjetpackcompose.utils.ioScope
import dev.baseio.discordjetpackcompose.utils.readAssetFile
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {
    var countryList: List<Country>? by mutableStateOf(null)
        private set

    fun Context.getCountryList() {
        ioScope {
            countryList = readAssetFile(Constants.CountriesAssetFilePath)
        }
    }

    fun filterCountryList(query: String) {
        countryList = countryList?.filter { country ->
            country.name.contains(other = query, ignoreCase = true)
        }
    }
}