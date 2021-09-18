package com.example.clientapp.data.repository.localsource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

    val accessToken: Flow<Int?>
        get() = context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }

    val avatarString: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[USER_AVATAR]
        }

    suspend fun saveAccessTokens(accessToken: Int) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun saveAvatar(avatarString: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_AVATAR] = avatarString
        }
    }

    suspend fun removeTokenUser(){
        context.dataStore.edit { preferences ->
            if(preferences.contains(ACCESS_TOKEN)){
                preferences.remove(ACCESS_TOKEN)
            }
        }
    }

    suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val ACCESS_TOKEN = intPreferencesKey("key_access_token")
        private val USER_AVATAR = stringPreferencesKey("key_user_avatar")
    }
}