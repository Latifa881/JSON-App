package com.example.jsonapp


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.base_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}