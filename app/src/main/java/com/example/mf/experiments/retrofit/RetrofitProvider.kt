package com.example.mf.experiments.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitProvider {
    val client = OkHttpClient.Builder()
    val retrofit =
        Retrofit.Builder().baseUrl("https://59cb3b9f7e21980011956392.mockapi.io")
            .addCallAdapterFactory(CoroutineCallAdapterFactory()).addConverterFactory(MoshiConverterFactory.create())
            .build()
    val apiService = retrofit.create(APIService::class.java)
}