package com.technoecorp.data.repository.preference

import androidx.datastore.preferences.core.Preferences
import com.technoecorp.data.repository.preference.datasource.IPreferencesDatastore
import kotlinx.coroutines.flow.Flow

class PreferencesRepository(private var preferenceDatastore: IPreferencesDatastore) {
    suspend fun <T> saveData(key: Preferences.Key<T>, value: T) {
        preferenceDatastore.saveData(key, value)
    }

    suspend fun <V> saveObjectData(key: Preferences.Key<String>, value: V) {
        preferenceDatastore.saveObjectData(key, value)
    }

    suspend fun <T> getData(key: Preferences.Key<T>, default: T): Flow<T> {
        return preferenceDatastore.getData(key, default)
    }

    suspend fun <V> getDataObject(key: Preferences.Key<String>, cls: Class<V>): Flow<V?> {
        return preferenceDatastore.getDataObject(key, cls)
    }

    suspend fun<V> getArrayObject(key: Preferences.Key<String>): Flow<ArrayList<V>> {
        return preferenceDatastore.getArrayObject(key)
    }


    suspend fun clearData() {
        preferenceDatastore.clearAll()
    }
}