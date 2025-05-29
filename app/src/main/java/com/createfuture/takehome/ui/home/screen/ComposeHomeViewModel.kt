package com.createfuture.takehome.ui.home.screen

import com.createfuture.domain.CharacterUseCase
import com.createfuture.takehome.ui.base.BaseViewModel
import com.createfuture.takehome.ui.base.DispatcherProvider

class ComposeHomeViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val characterUseCase: CharacterUseCase,
) : BaseViewModel<ComposeHomeState, ComposeHomeEffect, ComposeHomeEvent>() {

    init {
        initViewModel(dispatcherProvider)
    }

    override fun createInitialState(): ComposeHomeState {
        return ComposeHomeState()
    }

    override fun handleEvent(event: ComposeHomeEvent) {
        when (event) {
            is ComposeHomeEvent.OnChangeSearchString -> {}
        }
    }

}