package com.technoecorp.data.repository.preference.datasource

import androidx.datastore.preferences.core.Preferences
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import java.lang.reflect.Type
import kotlin.reflect.KClass

interface IPreferencesDatastore {

    suspend fun <T> saveData(key: Preferences.Key<T>, value: T)

    suspend fun <V> saveObjectData(key: Preferences.Key<String>, value: V)

    suspend fun <T> getData(key: Preferences.Key<T>, default: T): Flow<T>

    suspend fun <V> getDataObject(key: Preferences.Key<String>, cls: Class<V>): Flow<V?>

    suspend fun <V> getArrayObject(key: Preferences.Key<String>): Flow<ArrayList<V>>

    suspend fun clearAll()

}

