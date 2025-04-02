package eu.tutorials.fruitfreshness

sealed class Screen(val route: String) {
    object SplashScreen : Screen("Splash_screen")
    object HomeScreen : Screen("home_screen")
    object UploadScreen : Screen("upload_screen")
    object PredictionScreen : Screen("prediction_screen/{prediction}") {
        fun createRoute(prediction: String) = "prediction_screen/$prediction"
    }
}
