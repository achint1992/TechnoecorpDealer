package com.technoecorp.data.repository.preference.datasource

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


const val Data_Store_Name = "dealer_app"

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = Data_Store_Name)

class PreferenceDatastore(var context: Context) : IPreferencesDatastore {

    companion object {
        var DEALER = stringPreferencesKey("DEALER")
        var DISTRIBUTOR_ID = intPreferencesKey("DISTRIBUTOR_ID")
        var DASHBOARD_RESPONSE = stringPreferencesKey("DASHBOARD_RESPONSE")
        var RECENT_LIST = stringPreferencesKey("RECENT_LIST")
        var LOGGED_IN = booleanPreferencesKey("LOGGED_IN")
        var VERIFIED = booleanPreferencesKey("VERIFIED")
        var COUNTRY_CODE = stringPreferencesKey("COUNTRY_CODE")
        var DEALER_ID = intPreferencesKey("DEALER_ID")
        var REFER_CODE_DEEP_LINK = stringPreferencesKey("REFER_CODE_DEEP_LINK")
    }

    override suspend fun <T> saveData(key: Preferences.Key<T>, value: T) {
        context.datastore.edit {
            it[key] = value
        }
    }

    override suspend fun <V> saveObjectData(key: Preferences.Key<String>, value: V) {
        val gson = Gson()
        val data = gson.toJson(value)
        context.datastore.edit {
            it[key] = data
        }
    }

    override suspend fun <T> getData(key: Preferences.Key<T>, default: T): Flow<T> {
        return context.datastore.data.catch { ex ->
            if (ex is IOException) {
                Log.d("Datastore", ex.message.toString())
                emit(emptyPreferences())
            } else {
                throw ex
            }
        }.map {
            val data = it[key] ?: default
            data
        }
    }

    override suspend fun <V> getDataObject(key: Preferences.Key<String>, cls: Class<V>): Flow<V?> {
        val gson = Gson()
        return context.datastore.data.catch { ex ->
            if (ex is IOException) {
                Log.d("Datastore", ex.message.toString())
                emit(emptyPreferences())
            } else {
                throw ex
            }
        }.map {
            val data = it[key]
            val obj = data?.let { str ->
                gson.fromJson(str, cls)
            }
            obj
        }
    }

    override suspend fun <V> getArrayObject(key: Preferences.Key<String>): Flow<ArrayList<V>> {
        val gson = Gson()
        return context.datastore.data.catch { ex ->
            if (ex is IOException) {
                Log.d("Datastore", ex.message.toString())
                emit(emptyPreferences())
            } else {
                throw ex
            }
        }.map {
            val data = it[key]
            val obj = data?.let { str ->
                Log.e("Str", str)
                val convert =
                    gson.fromJson<ArrayList<V>>(str, object : TypeToken<ArrayList<V>>() {}.type)
                convert
            }
            obj ?: ArrayList()
        }
    }

    override suspend fun clearAll() {
        context.datastore.edit {
            it.clear()
        }
    }


}