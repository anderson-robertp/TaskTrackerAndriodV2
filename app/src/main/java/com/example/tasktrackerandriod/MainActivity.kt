package com.example.tasktrackerandriod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tasktrackerandriod.ui.screens.TaskScreen
import com.example.tasktrackerandriod.ui.theme.TaskTrackerAndriodV2Theme
import com.example.tasktrackerandriod.viewmodel.TaskViewModel
import androidx.navigation.compose.rememberNavController
import com.example.tasktrackerandriod.navigation.AppNavHost

class MainActivity : ComponentActivity() {

    // Activity-scoped ViewModel
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskTrackerAndriodV2Theme {
                Surface(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        val navController = rememberNavController()
                        AppNavHost(
                            navController = navController,
                            viewModel = taskViewModel
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
    TaskTrackerAndriodV2Theme {
        Greeting("Android")
    }
}