package com.training.newsapp.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "language_prefs")

class LanguagePreferences(private val context: Context) {

    companion object {
        val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
    }

    suspend fun saveLanguage(languageCode: String) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_LANGUAGE] = languageCode
        }
    }

    val selectedLanguage: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[SELECTED_LANGUAGE] ?: "en"
        }
}
