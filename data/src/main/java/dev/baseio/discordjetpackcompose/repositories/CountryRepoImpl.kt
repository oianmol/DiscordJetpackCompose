package dev.baseio.discordjetpackcompose.repositories

import android.content.Context
import dev.baseio.discordjetpackcompose.entities.CountryEntity
import dev.baseio.discordjetpackcompose.mappers.toDomainEntity
import dev.baseio.discordjetpackcompose.models.Country
import dev.baseio.discordjetpackcompose.utils.readAssetFile

class CountryRepoImpl(private val context: Context) : CountryRepo {
    companion object {
        const val CountriesAssetFilePath = "countries.json"
    }

    override suspend fun fetchCountriesFromAssetFile(): List<CountryEntity>? =
        context.readAssetFile<List<Country>>(CountriesAssetFilePath)?.map { it.toDomainEntity() }
}