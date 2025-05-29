package com.createfuture.domain

interface ApiRepository {

    suspend fun getCharactersList(): ApiResult<List<CharacterDto>>

}