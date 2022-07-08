package dev.baseio.discordjetpackcompose.mappers

import dev.baseio.discordjetpackcompose.entities.CountryEntity
import dev.baseio.discordjetpackcompose.models.Country

fun Country.toDomainEntity() = CountryEntity(
    alpha2 = alpha2,
    currencyCode = currencyCode,
    localeForICU = localeForICU,
    name = name,
    phoneCountryCode = phoneCountryCode
)

fun CountryEntity.toDataModel() = Country(
    alpha2 = alpha2,
    currencyCode = currencyCode,
    localeForICU = localeForICU,
    name = name,
    phoneCountryCode = phoneCountryCode
)