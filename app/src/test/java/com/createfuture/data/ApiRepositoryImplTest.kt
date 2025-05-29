package com.createfuture.data


import com.createfuture.domain.ApiResult
import kotlinx.coroutines.test.runTest

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ApiRepositoryImplTest {

    private lateinit var repository: ApiRepositoryImpl
    private val apiService = mock<ApiService>()

    @AfterEach
    fun afterEach() {
        Mockito.reset(apiService)
    }

    private fun createRepository() {
        repository = ApiRepositoryImpl(
            apiService = apiService
        )
    }

    private val apiCharacter = ApiCharacter(
        name = "111",
        gender = "222",
        culture = "333",
        born = "444",
        died = "555",
        aliases = listOf(),
        tvSeries = listOf(),
        playedBy = listOf()
    )
    private val apiCharacterList = listOf(apiCharacter)
    private val characterDtoList = apiCharacterList.map { it.toCharacterDto() }
    private val successfulApiResult = ApiResult.Success(characterDtoList)

    @Test
    fun `if (no error) then (getCharacters) should return (ApiResult Success)`() = runTest {

        whenever (apiService.getCharacters(any())).doReturn(apiCharacterList)
        createRepository()
        assertEquals(successfulApiResult, repository.getCharactersList())
        Mockito.verify(apiService, Mockito.times(1))
            .getCharacters(any())
    }

    @Test
    fun `if (error) then (getCharacters) should return (ApiResult Error)`() =
        runTest {
            val exception = RuntimeException()
            whenever (apiService.getCharacters(any())).thenThrow(exception)
            createRepository()
            assertEquals(true, repository.getCharactersList() is ApiResult.Error)
        }


}