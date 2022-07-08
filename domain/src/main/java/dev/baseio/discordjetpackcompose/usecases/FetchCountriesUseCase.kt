package dev.baseio.discordjetpackcompose.usecases

import dev.baseio.discordjetpackcompose.repositories.CountryRepo
import javax.inject.Inject

class FetchCountriesUseCase @Inject constructor(private val countryRepo: CountryRepo) {
    suspend operator fun invoke() = countryRepo.fetchCountriesFromAssetFile()
}