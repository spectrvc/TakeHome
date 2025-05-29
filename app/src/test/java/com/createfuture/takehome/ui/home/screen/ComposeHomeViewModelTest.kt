package com.createfuture.takehome.ui.home.screen

import app.cash.turbine.test
import com.createfuture.domain.ApiResult
import com.createfuture.domain.CharacterDto
import com.createfuture.domain.CharacterUseCase
import com.createfuture.takehome.ui.base.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ComposeHomeViewModelTest {

    private val characterUseCase = mock<CharacterUseCase>()

    @AfterEach
    fun afterEach() {
        Mockito.reset(characterUseCase)
    }

    /////////////////////////////////////////// TEST DATA

    private val errorString = "Error"
    private val searchString = "a"
    private val characterList = listOf(
        CharacterDto(
            name = "a111",
            culture = "a222",
            born = "a333",
            died = "a444",
            tvSeries = listOf()
        ),
        CharacterDto(
            name = "b111",
            culture = "b222",
            born = "b333",
            died = "b444",
            tvSeries = listOf()
        )
    )
    private val successfulApiResult: ApiResult<List<CharacterDto>> =
        ApiResult.Success(characterList)

    private val errorApiResult: ApiResult<List<CharacterDto>> = ApiResult.Error(
        exception = Throwable(message = errorString)
    )

    /////////////////////////////////////////// BUILDER

    private inner class ViewModelBuilder {

        fun build(testDispatcher: TestDispatcher): ComposeHomeViewModel {
            val dispatcherProvider = DispatcherProvider(
                io = testDispatcher,
                main = testDispatcher,
                default = testDispatcher
            )
            return ComposeHomeViewModel(
                dispatcherProvider = dispatcherProvider,
                characterUseCase = characterUseCase,
            )
        }

        suspend fun withSuccessfulApiResult(): ViewModelBuilder {
            whenever(characterUseCase.getCharactersList()).doReturn(successfulApiResult)
            return this
        }

        suspend fun withErrorApiResult(): ViewModelBuilder {
            whenever(characterUseCase.getCharactersList()).doReturn(errorApiResult)
            return this
        }
    }

    /////////////////////////////////////////// CASES

    internal inner class Cases(private val viewModel: ComposeHomeViewModel) {

        fun userOnChangeSearchString(): Cases {
            viewModel.setEvent(ComposeHomeEvent.OnChangeSearchString(searchString))
            return this
        }


    }

    /////////////////////////////////////////// VERIFIER

    private fun assertThat(viewModel: ComposeHomeViewModel): ViewModelVerifier {
        return ViewModelVerifier(viewModel)
    }

    private inner class ViewModelVerifier(private val viewModel: ComposeHomeViewModel) {

        suspend fun indicatorWasShownThenHidden(): ViewModelVerifier {
            viewModel.uiState.map { it.indicatorVisibility }
                .distinctUntilChanged()
                .test {
                    assertEquals(false, awaitItem())    //initial value
                    assertEquals(true, awaitItem())     //showIndicator
                    assertEquals(false, awaitItem())    //hideIndicator value
                    expectNoEvents()
                }
            return this
        }

        fun apiResultWasGot(): ViewModelVerifier {
            assertEquals(successfulApiResult, viewModel.uiState.value.apiResult)
            return this
        }

        fun characterListWasGot(): ViewModelVerifier {
            assertEquals(characterList, viewModel.uiState.value.characterList)
            assertEquals("", viewModel.uiState.value.errorString)
            return this
        }

        fun errorStringWasGot(): ViewModelVerifier {
            assertEquals(listOf<CharacterDto>(), viewModel.uiState.value.characterList)
            assertEquals(errorString, viewModel.uiState.value.errorString)
            return this
        }

        fun characterListWasFiltered(): ViewModelVerifier {
            val filteredCharacterList =
                characterList.filter { it.name.contains(searchString, true) }
            assertEquals(filteredCharacterList, viewModel.uiState.value.characterList)
            return this
        }

    }

    /////////////////////////////////////////// TESTS

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `(init) should cause (indicatorWasShownThenHidden)`() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        val viewModel = ViewModelBuilder()
            .withSuccessfulApiResult()
            .build(testDispatcher)
        val job = launch(testDispatcher) {
            assertThat(viewModel)
                .indicatorWasShownThenHidden()
        }
        advanceUntilIdle()
        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `(init) should cause (apiResultWasGot)`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val viewModel = ViewModelBuilder()
            .withSuccessfulApiResult()
            .build(testDispatcher)
        assertThat(viewModel)
            .apiResultWasGot()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `if (apiResult was Successful) then (init) should cause (characterListWasGot)`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val viewModel = ViewModelBuilder()
            .withSuccessfulApiResult()
            .build(testDispatcher)
        assertThat(viewModel)
            .characterListWasGot()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `if (apiResult was Error) then (init) should cause (errorStringWasGot)`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val viewModel = ViewModelBuilder()
            .withErrorApiResult()
            .build(testDispatcher)
        assertThat(viewModel)
            .errorStringWasGot()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `setEvent(OnChangeSearchString) should cause (characterListWasFiltered)`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val viewModel = ViewModelBuilder()
            .withSuccessfulApiResult()
            .build(testDispatcher)
        Cases(viewModel)
            .userOnChangeSearchString()
        assertThat(viewModel)
            .characterListWasFiltered()
    }

}
