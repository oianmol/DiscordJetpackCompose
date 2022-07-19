package dev.baseio.discordjetpackcompose.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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

fun ViewModel.ioScope(block: suspend () -> Unit): Job {
    return viewModelScope.launch(context = Dispatchers.IO) { block() }
}

fun ViewModel.mainScope(block: suspend () -> Unit): Job {
    return viewModelScope.launch(context = Dispatchers.Main) { block() }
}