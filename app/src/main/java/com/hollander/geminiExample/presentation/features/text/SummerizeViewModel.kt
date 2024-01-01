package com.hollander.geminiExample.presentation.features.text

import com.google.ai.client.generativeai.GenerativeModel
import com.hollander.geminiExample.di.GeminiPro
import com.hollander.geminiExample.presentation.BaseViewModel
import com.hollander.geminiExample.presentation.features.multimodal.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SummarizeViewModel @Inject constructor(
    @GeminiPro private val generativeModel: GenerativeModel
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<SummarizeUiState> =
        MutableStateFlow(SummarizeUiState.Idle)
    val uiState: StateFlow<SummarizeUiState> =
        _uiState.asStateFlow()

    fun summarize(inputText: String) {
        launchWithState {
            val prompt = "Summarize the following text for me: $inputText"
            val response = generativeModel.generateContent(prompt)
            response.text?.let { outputContent ->
                _uiState.value = SummarizeUiState.Success(outputContent)
            }
        }
    }

    fun summarizeStreaming(inputText: String) {
        launchWithState {

            val prompt = "Summarize the following text for me: $inputText"
            var outputContent = ""
            generativeModel.generateContentStream(prompt)
                .collect { response ->
                    outputContent += response.text
                    _uiState.value = SummarizeUiState.Success(outputContent)
                }
        }
    }

    override fun isLoading() {
        _uiState.value = SummarizeUiState.Loading
    }

    override fun onError(errorMessage: String) {
        _uiState.value = SummarizeUiState.Error(errorMessage)
    }
}

sealed interface SummarizeUiState {
    data object Idle: SummarizeUiState
    data object Loading: SummarizeUiState
    data class Success(val outputText: String): SummarizeUiState
    data class Error(val errorMessage: String): SummarizeUiState
}