package com.hollander.geminiExample.presentation.features.chat

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.hollander.geminiExample.presentation.viewModel.AsyncViewModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import com.hollander.geminiExample.di.GeminiPro
import com.hollander.geminiExample.di.GeminiVision
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ChatViewModel @Inject constructor(
    @GeminiPro generativeModel: GenerativeModel
) : AsyncViewModel() {

    private val chat = generativeModel.startChat(
        history = listOf(
            content(role = "user") { text("Hello, I have 2 dogs in my house.") },
            content(role = "model") { text("Great to meet you. What would you like to know?") }
        )
    )

    private val _uiState: MutableStateFlow<ChatUiState> =
        MutableStateFlow(ChatUiState(chat.history.map { content ->
            // Map the initial messages
            ChatMessage(
                text = content.parts.first().asTextOrNull() ?: "",
                participant = if (content.role == "user") Participant.USER else Participant.MODEL,
                isPending = false
            )
        }))
    val uiState: StateFlow<ChatUiState> =
        _uiState.asStateFlow()

    fun sendMessage(userMessage: String) {
        // Add a pending message
        _uiState.value.addMessage(
            ChatMessage(
                text = userMessage,
                participant = Participant.USER,
                isPending = true
            )
        )

        viewModelScope.launch {
            try {
                val response = chat.sendMessage(userMessage)

                _uiState.value.replaceLastPendingMessage()

                response.text?.let { modelResponse ->
                    _uiState.value.addMessage(
                        ChatMessage(
                            text = modelResponse,
                            participant = Participant.MODEL,
                            isPending = false
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value.replaceLastPendingMessage()
                _uiState.value.addMessage(
                    ChatMessage(
                        text = "" + e.localizedMessage,
                        participant = Participant.ERROR
                    )
                )
            }
        }
    }
}

class ChatUiState(
    messages: List<ChatMessage> = emptyList()
) {
    private val _messages: MutableList<ChatMessage> = messages.toMutableStateList()
    val messages: List<ChatMessage> = _messages

    fun addMessage(msg: ChatMessage) {
        _messages.add(msg)
    }

    fun replaceLastPendingMessage() {
        val lastMessage = _messages.lastOrNull()
        lastMessage?.let {
            val newMessage = lastMessage.apply { isPending = false }
            _messages.removeLast()
            _messages.add(newMessage)
        }
    }
}