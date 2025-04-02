package eu.tutorials.fruitfreshness.Screens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eu.tutorials.fruitfreshness.R
import eu.tutorials.fruitfreshness.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.main_back),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate(Screen.UploadScreen.route) },
                shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.BUTTONCOL)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                border = BorderStroke(1.dp, Color.Black),

            ) {
                Text(text = "Upload Picture", fontSize = 18.sp,color = Color.White)
            }
        }
    }
}
