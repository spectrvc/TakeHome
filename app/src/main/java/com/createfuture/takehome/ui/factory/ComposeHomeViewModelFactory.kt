package com.createfuture.takehome.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.createfuture.data.ApiRepositoryImpl
import com.createfuture.data.ApiRetrofitInstance
import com.createfuture.domain.CharacterUseCase
import com.createfuture.takehome.ui.base.DispatcherProvider
import com.createfuture.takehome.ui.home.screen.ComposeHomeViewModel

class ComposeHomeViewModelFactory: ViewModelProvider.Factory {

    private val apiRepository by lazy(LazyThreadSafetyMode.NONE) {
        ApiRepositoryImpl(ApiRetrofitInstance.service)
    }

    private val characterUseCase by lazy(LazyThreadSafetyMode.NONE) {
        CharacterUseCase(apiRepository = apiRepository)
    }

    private val dispatcherProvider by lazy(LazyThreadSafetyMode.NONE) {
        DispatcherProvider()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ComposeHomeViewModel(
            dispatcherProvider = dispatcherProvider,
            characterUseCase = characterUseCase
        ) as T
    }

}