package eu.tutorials.fruitfreshness.Screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import coil.compose.rememberAsyncImagePainter
import eu.tutorials.fruitfreshness.R
import eu.tutorials.fruitfreshness.Screen

@Composable
fun PredictionScreen(navController: NavController, prediction: String, imageUri: Uri?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.main_back),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Uploaded Image",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(10.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = "Prediction Result",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = prediction,
                fontSize = 30.sp,
                color = colorResource(id = R.color.BUTTONCOL),
                modifier = Modifier.padding(top = 5.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate(Screen.UploadScreen.route) },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.BUTTONCOL)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            ) {
                Text(text = "Upload Another Image", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}
