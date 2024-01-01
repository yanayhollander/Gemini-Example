package com.hollander.template

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hollander.template.presentation.DotaViewModel
import com.hollander.template.presentation.composables.ErrorComposable
import com.hollander.template.presentation.composables.Loading
import com.hollander.template.ui.theme.AndroidTemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTemplateTheme {
                val dotaViewModel = hiltViewModel<DotaViewModel>()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingScreen(dotaViewModel)
                }
            }
        }
    }
}

@Composable
fun GreetingScreen(dotaViewModel: DotaViewModel, modifier: Modifier = Modifier) {

    val action by dotaViewModel.action.collectAsState()
    val isLoading = dotaViewModel.isLoading.observeAsState(false)
    val error = dotaViewModel.error.observeAsState()

    if (isLoading.value) {
        Loading()
        return
    }

    error.value?.let {
        ErrorComposable(message = it)
        return
    }

    when (action) {
        is DotaViewModel.Action.ShowHeroes -> {
            val heroes = (action as DotaViewModel.Action.ShowHeroes).heroes
            val content = buildString {
                append("Heroes:\n")
                for (hero in heroes) {
                    append("${hero.localized_name}\n")
                }
            }

            BoxWithConstraints {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    item {
                        Text(
                            text = content,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            maxLines = Int.MAX_VALUE,
                            overflow = TextOverflow.Clip
                        )
                    }
                }
            }
        }

        null -> {}
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidTemplateTheme {
        Greeting("Hello Android", Modifier)
    }
}