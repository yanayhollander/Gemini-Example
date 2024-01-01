package com.hollander.geminiExample.presentation.features.multimodal

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.hollander.geminiExample.di.GeminiVision
import com.hollander.geminiExample.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PhotoReasoningViewModel @Inject constructor(
    @GeminiVision private val generativeModel: GenerativeModel
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    fun reason(
        userInput: String,
        selectedImages: List<Bitmap>
    ) {
        launchWithState {
            val prompt = "Look at the image(s), and then answer the following question: $userInput"
            val inputContent = content {
                for (bitmap in selectedImages) {
                    image(bitmap)
                }
                text(prompt)
            }

            var outputContent = ""

            generativeModel.generateContentStream(inputContent)
                .collect { response ->
                    outputContent += response.text
                    _uiState.value = UiState.Success(outputContent)
                }
        }
    }

    override fun isLoading() {
        _uiState.value = UiState.Loading
    }

    override fun onError(message: String) {
        _uiState.value = UiState.Error(message)
    }

    override fun idle() {
        _uiState.value = UiState.Idle
    }
}

sealed interface UiState {
    data object Idle : UiState
    data object Loading : UiState
    data class Error(val errorMessage: String) : UiState
    data class Success(val outputText: String) : UiState
}


