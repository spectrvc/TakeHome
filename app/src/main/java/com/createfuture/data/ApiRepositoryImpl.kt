package com.createfuture.data

import com.createfuture.domain.ApiRepository
import com.createfuture.domain.ApiResult
import com.createfuture.domain.CharacterDto

private const val TOKEN = "Bearer 754t!si@glcE2qmOFEcN"

class ApiRepositoryImpl(private val apiService: ApiService) : ApiRepository {

    override suspend fun getCharactersList(): ApiResult<List<CharacterDto>> {
        return apiResultWrapper {
            apiService
                .getCharacters(TOKEN)
                .map { it.toCharacterDto() }
        }
    }

    private suspend fun <T> apiResultWrapper(call: suspend () -> T): ApiResult<T> {
        return try {
            ApiResult.Success(call())
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }

}