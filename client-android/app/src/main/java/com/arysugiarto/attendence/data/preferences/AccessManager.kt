package com.arysugiarto.attendence.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.arysugiarto.attendence.util.Const.Access.AUTH_PREFIX
import com.arysugiarto.attendence.util.emptyString
import com.arysugiarto.attendence.util.getRandomCharacters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class AccessManager(private val context: Context) {
    suspend fun setAccess(tokenAccess: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.accessKey] = "$AUTH_PREFIX $tokenAccess"
        }

        clearSessionId()
    }

    fun setAccess(tokenAccess: String, scope: CoroutineScope) = scope.launch {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.accessKey] = "$AUTH_PREFIX $tokenAccess"
        }

        clearSessionId()
    }

    suspend fun clearAccess() {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.accessKey] = emptyString
            preferences[PreferencesKey.sessionIdKey] = 35.getRandomCharacters
        }
    }

    fun clearAccess(scope: CoroutineScope) = scope.launch {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.accessKey] = emptyString
            preferences[PreferencesKey.sessionIdKey] = 35.getRandomCharacters
        }
    }

    val access: Flow<String> = context.dataStore.data
            .catch { throwable ->
                emit(emptyPreferences())
                Timber.e(throwable)
            }.map { preferences ->
                preferences[PreferencesKey.accessKey] ?: emptyString
            }

    suspend fun setSessionId(sessionId: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.sessionIdKey] = sessionId
            preferences[PreferencesKey.accessKey] = emptyString
        }
    }

    fun setSessionId(sessionId: String, scope: CoroutineScope) = scope.launch {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.sessionIdKey] = sessionId
            preferences[PreferencesKey.accessKey] = emptyString
        }
    }

    suspend fun clearSessionId() {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.sessionIdKey] = emptyString
        }
    }

    fun clearSessionId(scope: CoroutineScope) = scope.launch {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.sessionIdKey] = emptyString
        }
    }

    val sessionId: Flow<String> = context.dataStore.data
        .catch { throwable ->
            emit(emptyPreferences())
            Timber.e(throwable)
        }.map { preferences ->
            preferences[PreferencesKey.sessionIdKey] ?: emptyString
        }


    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = PreferencesKey.AUTH_PREFERENCES_KEY.toUpperCase(Locale.ROOT)
        )
    }

    private object PreferencesKey {
        const val AUTH_PREFERENCES_KEY = "auth_preferences"
        const val TOKEN_ACCESS_REF = "token_access_key"
        const val SESSION_ID_REF = "session_id_reference_key"

        val accessKey = stringPreferencesKey(TOKEN_ACCESS_REF)
        val sessionIdKey = stringPreferencesKey(SESSION_ID_REF)
    }
}
