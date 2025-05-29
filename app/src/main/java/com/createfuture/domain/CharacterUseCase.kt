package com.createfuture.domain

class CharacterUseCase(
    private val apiRepository: ApiRepository,
) {

    suspend fun getCharactersList(): ApiResult<List<CharacterDto>> =
        apiRepository.getCharactersList()

}

