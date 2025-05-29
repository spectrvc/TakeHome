package com.createfuture.takehome.ui.home.screen

import com.createfuture.domain.ApiResult
import com.createfuture.domain.CharacterDto
import com.createfuture.takehome.ui.base.UiEffect
import com.createfuture.takehome.ui.base.UiEvent
import com.createfuture.takehome.ui.base.UiState

data class ComposeHomeState(
    val apiResult: ApiResult<List<CharacterDto>> = ApiResult.Success(listOf()),
    val indicatorVisibility: Boolean = false,
    val searchString: String = "",
    val errorString: String = "",
    val characterList: List<CharacterDto> = listOf()
) : UiState

sealed class ComposeHomeEffect : UiEffect

sealed class ComposeHomeEvent : UiEvent {
    class OnChangeSearchString(val text: String) : ComposeHomeEvent()
}
