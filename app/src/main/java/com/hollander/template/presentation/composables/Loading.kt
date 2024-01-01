package com.hollander.template.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hollander.template.Greeting
import com.hollander.template.ui.theme.AndroidTemplateTheme

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .size(48.dp)
            .padding(16.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .scale(0.5f) // Adjust the scale factor to control the size
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    AndroidTemplateTheme {
        Loading()
    }
}