package eu.tutorials.fruitfreshness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.fruitfreshness.Screens.HomeScreen
import eu.tutorials.fruitfreshness.Screens.PredictionScreen
import eu.tutorials.fruitfreshness.Screens.SplashScreen
import eu.tutorials.fruitfreshness.Screens.UploadScreen
import eu.tutorials.fruitfreshness.ui.theme.FruitfreshnessTheme
sealed class Screen(val route: String) {
    object SplashScreen : Screen("Splash_screen")
    object HomeScreen : Screen("home_screen")
    object UploadScreen : Screen("upload_screen")
    object PredictionScreen : Screen("prediction_screen")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FruitfreshnessTheme {
                Navigation()
            }
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
            UploadScreen(navController)
        }
        composable(Screen.PredictionScreen.route) {
            PredictionScreen(navController)
        }
    }
}
