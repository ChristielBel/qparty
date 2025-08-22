package com.example.qparty.data

import kotlinx.coroutines.flow.Flow

interface ThemeRepositoryInterface {
    val isDarkTheme: Flow<Boolean>
    suspend fun setDarkTheme(enabled: Boolean)
}