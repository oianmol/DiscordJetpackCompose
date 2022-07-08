package dev.baseio.discordjetpackcompose.entities

data class CountryEntity(
    val alpha2: String = "",
    val currencyCode: String? = null,
    val localeForICU: String? = null,
    val name: String = "",
    val phoneCountryCode: String = ""
)