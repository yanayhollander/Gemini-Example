package com.hollander.geminiExample.presentation.features.multimodal

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.hollander.geminiExample.di.GeminiVision
import com.hollander.geminiExample.presentation.viewModel.AsyncViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PhotoReasoningViewModel @Inject constructor(
    @GeminiVision private val generativeModel: GenerativeModel
) : AsyncViewModel() {

    private val _uiState: MutableStateFlow<PhotoReasoningUiState> =
        MutableStateFlow(PhotoReasoningUiState.Initial)
    val uiState: StateFlow<PhotoReasoningUiState> =
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
                    _uiState.value = PhotoReasoningUiState.Success(outputContent)
                }
        }
    }
}

sealed interface PhotoReasoningUiState {

    data object Initial : PhotoReasoningUiState
    data object Loading : PhotoReasoningUiState
    data class Success(val outputText: String) : PhotoReasoningUiState
    data class Error(val errorMessage: String) : PhotoReasoningUiState
}