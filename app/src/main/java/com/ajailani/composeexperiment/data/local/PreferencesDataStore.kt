package com.ajailani.composeexperiment.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class PreferencesDataStore(
    private val context: Context
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("lukman666")
        private val TIMER = longPreferencesKey("timerLukman")
    }

    suspend fun saveTimer(timer: Long) {
        context.dataStore.edit {
            it[TIMER] = timer
        }
    }

    fun getTimer() = context.dataStore.data.map { it[TIMER] ?: 0L }
}