package dev.baseio.discordjetpackcompose.models


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Country(
    @SerializedName("alpha2")
    val alpha2: String = "",
    @SerializedName("currencyCode")
    val currencyCode: String? = null,
    @SerializedName("localeForICU")
    val localeForICU: String? = null,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("phoneCountryCode")
    val phoneCountryCode: String = ""
)