package dev.baseio.discordjetpackcompose.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ViewModel.ioScope(block: suspend () -> Unit) {
    viewModelScope.launch(context = Dispatchers.IO) { block() }
}

fun ViewModel.mainScope(block: suspend () -> Unit) {
    viewModelScope.launch(context = Dispatchers.Main) { block() }
}