package com.example.samurairoad.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.samurairoad.hiltModules.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// sub class for managing token
class RefreshTokenManager(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val REFRESH_KEY = stringPreferencesKey("refresh_token")
    }

//      create flow
    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            // acts like map but with preferences key
            preferences[TOKEN_KEY] // emit token data
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }


}
