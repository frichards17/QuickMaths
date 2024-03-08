package com.frankrichards.quickmaths.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.frankrichards.quickmaths.screens.Difficulty
import kotlinx.coroutines.flow.Flow

const val SETTINGS = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS)

class DataStoreManager(private val context: Context) {

    companion object {
        val DIFFICULTY = stringPreferencesKey("difficulty")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val MUSIC = booleanPreferencesKey("music")
        val SFX = booleanPreferencesKey("sfx")
    }

    //region DARK MODE
    suspend fun storeDarkMode(b: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE] = b
        }
    }

    val darkModeFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[DARK_MODE] ?: false
    }
    //endregion

    //region DIFFICULTY
    suspend fun storeDifficulty(difficulty: String) {
        context.dataStore.edit { prefs ->
            prefs[DIFFICULTY] = difficulty
        }
    }

    val difficultyFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[DIFFICULTY] ?: Difficulty.S_MEDIUM
    }
    //endregion

    //region MUSIC
    suspend fun storeMusic(b: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[MUSIC] = b
        }
    }

    val musicFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[MUSIC] ?: true
    }
    //endregion

    //region SFX
    suspend fun storeSFX(b: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[SFX] = b
        }
    }

    val SFXFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[SFX] ?: true
    }
    //endregion



}

data class Settings(
    val timer: Int = 30
)