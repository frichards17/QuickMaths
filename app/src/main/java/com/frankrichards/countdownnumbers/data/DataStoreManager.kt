package com.frankrichards.countdownnumbers.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow

const val SETTINGS = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS)

class DataStoreManager(private val context: Context) {

    companion object {
        val TIMER = intPreferencesKey("timer")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    suspend fun storeDarkMode(b: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE] = b
        }
    }

    val darkModeFlow: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE] ?: false
    }

}

data class Settings(
    val timer: Int = 30
)