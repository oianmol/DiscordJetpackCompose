package dev.baseio.discordjetpackcompose.repositories

import dev.baseio.discordjetpackcompose.entities.CountryEntity

interface CountryRepo {
    suspend fun fetchCountriesFromAssetFile(): List<CountryEntity>?
}