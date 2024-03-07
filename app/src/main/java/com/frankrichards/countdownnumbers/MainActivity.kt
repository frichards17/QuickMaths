package com.frankrichards.countdownnumbers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.frankrichards.countdownnumbers.nav.AppNavHost
import com.frankrichards.countdownnumbers.ui.theme.CountdownNumbersTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            CountdownNumbersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(modifier = Modifier.fillMaxSize())
                }
//            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    AppNavHost(
        navController = rememberNavController(),
    )
}

@Preview(widthDp = 480, heightDp = 800)
@Composable
fun AppPreview() {
    CountdownNumbersTheme {
        App()
    }
}