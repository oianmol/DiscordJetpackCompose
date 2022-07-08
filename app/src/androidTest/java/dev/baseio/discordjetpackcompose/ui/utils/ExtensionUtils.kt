package dev.baseio.discordjetpackcompose.ui.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber

inline fun <reified T> Context.readAssetFile(filePath: String): T? {
    return try {
        val jsonDataFromFile = this.assets.open(filePath).bufferedReader().use { it.readText() }
        val returnType = object : TypeToken<T>() {}.type
        Gson().fromJson(jsonDataFromFile, returnType)
    } catch (ex: Exception) {
        Timber.e(ex, "Some error occurred. Reason:")
        null
    }
}