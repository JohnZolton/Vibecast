package com.podcastr2.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.podcastr2.ui.screens.DirectDownloadScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PodcastrApp()
                }
            }
        }
    }
}

@Composable
fun PodcastrApp() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "direct_download") {
        composable("direct_download") {
            DirectDownloadScreen(
                onNavigateBack = { /* Do nothing since this is the only screen */ }
            )
        }
    }
}
