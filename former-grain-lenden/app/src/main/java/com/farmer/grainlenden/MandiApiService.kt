package com.farmer.grainlenden
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MandiApiService {
    @GET("resource/9ef84268-d588-465a-a308-a864a43d0070")
    suspend fun getLiveMandiPrices(@Query("api-key") apiKey: String, @Query("format") format: String = "json", @Query("limit") limit: Int = 50): MandiApiResponse
    companion object {
        fun create(): MandiApiService = Retrofit.Builder().baseUrl("https://api.data.gov.in/").addConverterFactory(GsonConverterFactory.create()).build().create(MandiApiService::class.java)
    }
}