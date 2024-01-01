package com.hollander.geminiExample.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hollander.geminiExample.ui.theme.GeminiExampleTheme

@Composable
fun ErrorComposable(message: String) {
    Text(
        text = "Error\n${message}",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun ErrorComposablePreview() {
    GeminiExampleTheme {
        ErrorComposable("Illegal error")
    }
}