package com.example.qparty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qparty.data.ThemeRepositoryInterface
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(private val repo: ThemeRepositoryInterface) : ViewModel() {

    val isDarkTheme: StateFlow<Boolean> = repo.isDarkTheme
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleTheme() {
        viewModelScope.launch {
            repo.setDarkTheme(!isDarkTheme.value)
        }
    }
}
