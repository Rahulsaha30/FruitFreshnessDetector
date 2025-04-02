package eu.tutorials.fruitfreshness

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.fruitfreshness.Screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Screen.UploadScreen.route) {
            val context = LocalContext.current
            UploadScreen(navController, context)
        }
        composable(Screen.PredictionScreen.route + "/{prediction}") { backStackEntry ->
            val prediction = backStackEntry.arguments?.getString("prediction") ?: "Unknown"
            val imageUriString = navController.previousBackStackEntry?.savedStateHandle?.get<String>("imageUri")
            val imageUri = imageUriString?.let { Uri.parse(it) }

            PredictionScreen(navController, prediction, imageUri)
        }
    }
}
