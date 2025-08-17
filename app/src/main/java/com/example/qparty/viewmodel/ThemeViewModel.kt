package com.example.qparty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qparty.data.ThemeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(private val repo: ThemeRepository) : ViewModel() {

    val isDarkTheme: StateFlow<Boolean> = repo.isDarkTheme
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleTheme(){
        viewModelScope.launch {
            repo.setDarkTheme(!isDarkTheme.value)
        }
    }
}