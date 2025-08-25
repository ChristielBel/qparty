package com.example.qparty.viewmodel

import com.example.qparty.data.ThemeRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ThemeViewModelTest {

    private lateinit var fakeRepo: FakeThemeRepository
    private lateinit var viewModel: ThemeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeRepo = FakeThemeRepository()
        viewModel = ThemeViewModel(fakeRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun toggleTheme_changesIsDarkTheme() {
        assertFalse(viewModel.isDarkTheme.value)

        viewModel.toggleTheme()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.isDarkTheme.value)

        viewModel.toggleTheme()
        testDispatcher.scheduler.advanceUntilIdle()
        assertFalse(viewModel.isDarkTheme.value)
    }

    private class FakeThemeRepository : ThemeRepositoryInterface {
        private val state = MutableStateFlow(false)
        override val isDarkTheme: Flow<Boolean> = state

        override suspend fun setDarkTheme(enabled: Boolean) {
            state.value = enabled
        }
    }
}
