package dev.baseio.discordjetpackcompose.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.discordjetpackcompose.entities.CountryEntity
import dev.baseio.discordjetpackcompose.usecases.FetchCountriesUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    val fetchCountriesUseCase: FetchCountriesUseCase
) : ViewModel() {
    private var countryList: List<CountryEntity>? by mutableStateOf(null)
    var filteredCountryList: List<CountryEntity>? by mutableStateOf(null)
        private set
    private var countrySearchQuery: String by mutableStateOf("")

    init {
        getCountryList()
        snapshotFlow { countrySearchQuery }.onEach { query ->
            filteredCountryList = countryList?.filter { country ->
                country.name.contains(other = query, ignoreCase = true)
            }
        }.launchIn(viewModelScope)
    }

    private fun getCountryList() {
        viewModelScope.launch {
            countryList = fetchCountriesUseCase()
        }
    }

    fun updateCountryQuery(query: String) {
        countrySearchQuery = query
    }
}