package com.tesusil.template.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel<State, Event> : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(createInitialState())
    val state: StateFlow<State> = _state

    protected open fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
    protected open fun mainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    protected fun setState(reduce: State.() -> State) {
        _state.value = _state.value.reduce()
    }

    protected fun launchWithIO(block: suspend () -> Unit) {
        viewModelScope.launch(ioDispatcher()) {
            block()
        }
    }

    protected fun launchWithMain(block: suspend () -> Unit) {
        viewModelScope.launch(mainDispatcher()) {
            block()
        }
    }

    abstract fun createInitialState(): State
    abstract fun onEvent(event: Event)
}