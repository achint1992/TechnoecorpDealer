package com.technoecorp.gorilladealer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.data.repository.preference.datasource.PreferenceDatastore
import com.technoecorp.domain.domainmodel.data.Dealer
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import timber.log.Timber

class SharedViewModel(private val preferencesDatastore: PreferencesRepository) : ViewModel() {

    suspend fun shouldNavigateToDashBoard(): Boolean {
        val data = viewModelScope.async {
            val loggedIn =
                preferencesDatastore.getData(PreferenceDatastore.LOGGED_IN, false).take(1).first()
            val verified =
                preferencesDatastore.getData(PreferenceDatastore.VERIFIED, false).take(1).first()

            loggedIn && verified
        }
        return data.await()
    }

    suspend fun dealerData(): Dealer? {
        val data = viewModelScope.async {
            val dealer =
                preferencesDatastore.getDataObject(PreferenceDatastore.DEALER, Dealer::class.java)
                    .take(1).first()
            Timber.e("verified == $dealer")

            dealer
        }.await()
        return data
    }

    suspend fun saveReferCode(referCode: String) {
        preferencesDatastore.saveData(PreferenceDatastore.REFER_CODE_DEEP_LINK, referCode)
    }

    suspend fun clearData() {
        preferencesDatastore.clearData()
    }

}