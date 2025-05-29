package com.createfuture.takehome.ui.home.screen

import androidx.lifecycle.viewModelScope
import com.createfuture.domain.ApiResult
import com.createfuture.domain.CharacterUseCase
import com.createfuture.takehome.ui.base.BaseViewModel
import com.createfuture.takehome.ui.base.DispatcherProvider
import kotlinx.coroutines.launch

class ComposeHomeViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val characterUseCase: CharacterUseCase,
) : BaseViewModel<ComposeHomeState, ComposeHomeEffect, ComposeHomeEvent>() {

    init {
        initViewModel(dispatcherProvider)
        receiveData()
    }

    private fun receiveData() {
        viewModelScope.launch(dispatcherProvider.io) {
            showIndicator()
            val apiResult = characterUseCase.getCharactersList()
            setState { copy(apiResult = apiResult) }
            hideIndicator()
            processResult()
        }
    }

    private fun processResult() {
        when (val apiResult = uiState.value.apiResult) {
            is ApiResult.Success -> {
                val searchString = uiState.value.searchString
                val characterList = if (searchString.isEmpty())
                    apiResult.data
                else
                    apiResult.data.filter { it.name.contains(searchString, true) }
                setState {
                    copy(
                        errorString = "",
                        characterList = characterList
                    )
                }
            }

            is ApiResult.Error -> {
                setState {
                    copy(
                        errorString = apiResult.exception.message ?: "Unknown error",
                        characterList = listOf()
                    )
                }
            }
        }
    }

    private fun showIndicator() {
        setState { copy(indicatorVisibility = true) }
    }

    private fun hideIndicator() {
        setState { copy(indicatorVisibility = false) }
    }

    override fun createInitialState(): ComposeHomeState {
        return ComposeHomeState()
    }

    override fun handleEvent(event: ComposeHomeEvent) {
        when (event) {
            is ComposeHomeEvent.OnChangeSearchString -> {
                setState { copy(searchString = event.text) }
                processResult()
            }
        }
    }

}