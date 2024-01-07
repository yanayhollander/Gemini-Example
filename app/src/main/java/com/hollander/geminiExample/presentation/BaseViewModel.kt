package com.hollander.geminiExample.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    protected open fun isLoading() {
        Timber.w("is ")
    }

    protected open fun onError(errorMessage: String) {}
    protected open fun idle() {}

    fun launchWithState(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                isLoading()
                block(this)
            } catch (e: Exception) {
                onError("${e.message}")
            }
        }
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                block(this)
            } catch (e: Exception) {
                onError("${e.message}")
            }
        }
    }
}
