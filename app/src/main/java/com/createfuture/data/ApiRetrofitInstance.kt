package com.createfuture.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://yj8ke8qonl.execute-api.eu-west-1.amazonaws.com"

object ApiRetrofitInstance {

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(OkHttpClient.Builder().build()).build()
    val service: ApiService = retrofit.create(ApiService::class.java)

}