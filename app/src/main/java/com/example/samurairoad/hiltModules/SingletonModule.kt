package com.example.samurairoad.hiltModules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// create datastore, singleton obj
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

class SingletonModule {
}