package com.createfuture.data

import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("/characters")
    suspend fun getCharacters(@Header("Authorization") token: String): List<ApiCharacter>

}

