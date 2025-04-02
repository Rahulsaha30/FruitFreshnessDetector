package eu.tutorials.fruitfreshness.Screens

import android.content.Context
import android.net.Uri
import android.util.Log
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
import eu.tutorials.fruitfreshness.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream

@Composable
fun UploadScreen(navController: NavController, context: Context) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.BUTTONCOL)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                border = BorderStroke(1.dp, Color.Black),
            ) {
                Text(text = "Select Image from Gallery", fontSize = 18.sp, color = Color.White)
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
                    onClick = { uploadImage(context, uri, navController) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.BUTTONCOL)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                    border = BorderStroke(1.dp, Color.Black),
                ) {
                    Text(text = "Analyze Image", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    }
}

fun uploadImage(context: Context, uri: Uri, navController: NavController) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            Log.d("UploadScreen", "Starting image upload...")

            val file = File(context.cacheDir, "upload.jpg")
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)

            if (inputStream == null) {
                Log.e("UploadScreen", "Error: inputStream is null. Unable to read image.")
                return@launch
            }

            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()

            Log.d("UploadScreen", "File created: ${file.absolutePath} (Size: ${file.length()} bytes)")

            if (!file.exists()) {
                Log.e("UploadScreen", "Error: File does not exist after creation.")
                return@launch
            }

            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val imagePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

            Log.d("UploadScreen", "Sending request to API... File size: ${file.length()} bytes")
            val response = RetrofitClient.apiService.uploadImage(imagePart).execute()

            if (response.isSuccessful) {
                val prediction = response.body()?.prediction ?: "Unknown"
                Log.d("UploadScreen", "API Success: Prediction = $prediction")

                CoroutineScope(Dispatchers.Main).launch {
                    navController.currentBackStackEntry?.savedStateHandle?.set("imageUri", uri.toString())
                    navController.navigate(Screen.PredictionScreen.route + "/$prediction")
                }
            } else {
                Log.e("UploadScreen", "API Error: ${response.errorBody()?.string()}")
            }
        } catch (e: HttpException) {
            Log.e("UploadScreen", "HTTP Error: ${e.code()} ${e.message()}")
        } catch (e: Exception) {
            Log.e("UploadScreen", "Upload failed", e)
        }
    }
}
