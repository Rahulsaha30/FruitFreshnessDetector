package eu.tutorials.fruitfreshness.network

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/predict")  // Ensure this matches your backend endpoint
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<PredictionResponse>
}
