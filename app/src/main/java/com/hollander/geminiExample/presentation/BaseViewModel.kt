package com.hollander.geminiExample.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    abstract fun isLoading()
    abstract fun onError(errorMessage: String)
    protected open fun idle() {}

    fun launchWithState(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                isLoading()
                block(this)
            } catch (e: Exception) {
                onError("${e.message}")
            } finally {
                idle()
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
