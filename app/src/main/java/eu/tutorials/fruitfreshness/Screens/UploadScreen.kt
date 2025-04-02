package eu.tutorials.fruitfreshness.Screens
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import eu.tutorials.fruitfreshness.R
import eu.tutorials.fruitfreshness.Screen

@Composable
fun UploadScreen(navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

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
                onClick = { imagePickerLauncher.launch("image/*") },
                shape = RoundedCornerShape(12.dp),
               colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.BUTTONCOL)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                border = BorderStroke(1.dp, Color.Black),
            ) {
                Text(text = "Select Image from Gallery", fontSize = 18.sp,color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            imageUri?.let { uri ->
                Image(
                    painter = rememberImagePainter(uri),
                    contentDescription = "Selected Image",
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { navController.navigate(Screen.PredictionScreen.route) },
                    shape = RoundedCornerShape(12.dp),
                   colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.BUTTONCOL)
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                    border = BorderStroke(1.dp, Color.Black),
                ) {
                    Text(text = "Analyze Image", fontSize = 18.sp,color = Color.White)
                }
            }
        }
    }
}
