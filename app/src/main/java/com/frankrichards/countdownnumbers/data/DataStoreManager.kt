package com.frankrichards.countdownnumbers.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

const val SETTINGS = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS)

class DataStoreManager(val context: Context) {

    companion object {
        val TIMER = intPreferencesKey("TIMER")
    }

    suspend fun saveToDataStore(settings: Settings) {
        context.dataStore.edit {
            it[TIMER] = settings.timer
        }

    }

    fun getFromDataStore() = context.dataStore.data.map {
        Settings(
            timer = it[TIMER]?:30
        )
    }

}

data class Settings(
    val timer: Int = 30
)