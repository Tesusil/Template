package com.tesusil.template.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun <State> MutableStateFlow<State>.setState(reduce: State.() -> State) {
    value = value.reduce()
}

fun ViewModel.launchWithIO(block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) { block() }
}

fun ViewModel.launchWithMain(block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.Main) { block() }
} 