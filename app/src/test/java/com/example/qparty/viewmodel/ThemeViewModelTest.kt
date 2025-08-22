package com.example.qparty.viewmodel

import com.example.qparty.data.ThemeRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ThemeViewModelTest {

    private lateinit var fakeRepo: FakeThemeRepository
    private lateinit var viewModel: ThemeViewModel

    @Before
    fun setup() {
        fakeRepo = FakeThemeRepository()
        viewModel = ThemeViewModel(fakeRepo)
    }

    @Test
    fun toggleTheme_changesIsDarkTheme() {
        assertFalse(viewModel.isDarkTheme.value)

        viewModel.toggleTheme()
        assertTrue(viewModel.isDarkTheme.value)

        viewModel.toggleTheme()
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
