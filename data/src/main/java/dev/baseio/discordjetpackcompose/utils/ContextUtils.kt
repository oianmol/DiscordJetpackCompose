package dev.baseio.discordjetpackcompose.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val TAG = "ContextUtils"

fun <T> Context.readAssetFile(filePath: String): T? {
    return try {
        val jsonDataFromFile = this.assets.open(filePath).bufferedReader().use { it.readText() }
        val returnType = object : TypeToken<T>() {}.type
        Gson().fromJson(jsonDataFromFile, returnType)
    } catch (e: Exception) {
        Log.e(TAG, "Some error occurred. Reason:", e)
        null
    }
}