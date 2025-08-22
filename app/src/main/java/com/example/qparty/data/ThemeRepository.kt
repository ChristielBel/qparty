package com.example.qparty.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore("settings")

class ThemeRepository(private val context: Context) : ThemeRepositoryInterface {
    companion object {
        val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    }

    override val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[DARK_THEME_KEY] ?: false }

    override suspend fun setDarkTheme(enabled: Boolean){
        context.dataStore.edit { prefs ->
            prefs[DARK_THEME_KEY] = enabled
        }
    }
}