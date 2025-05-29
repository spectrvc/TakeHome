package com.createfuture.domain

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class CharacterUseCaseTest{

    private lateinit var useCase: CharacterUseCase
    private val apiRepository = mock<ApiRepository>()

    @AfterEach
    fun afterEach() {
        Mockito.reset(apiRepository)
    }

    private fun createUseCase() {
        useCase = CharacterUseCase(
            apiRepository = apiRepository,
        )
    }

    @Test
    fun `(getCharactersList) should call (getCharactersList) from repository`() = runTest {
        createUseCase()
        useCase.getCharactersList()
        Mockito.verify(apiRepository, Mockito.times(1))
            .getCharactersList()
    }

}