package com.prography.gwasuwon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prography.gwasuwon.ui.theme.GwasuwonaosTheme
import com.prography.usm_sample.SampleCountViewModel
import com.prography.usm_sample.compose.SampleCountRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GwasuwonaosTheme {
                Box(modifier = Modifier.padding(20.dp)) {
                    SampleCountRoute(
                        viewModel = viewModel(
                            factory = SampleCountViewModel.provideFactory(
                                saveCurrentCountUseCase = AppContainer.sampleCountUseCase
                            )
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GwasuwonaosTheme {
        Greeting("Android")
    }
}