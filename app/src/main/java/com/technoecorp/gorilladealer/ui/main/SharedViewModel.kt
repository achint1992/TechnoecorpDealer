package com.technoecorp.gorilladealer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.data.repository.preference.datasource.PreferenceDatastore
import com.technoecorp.domain.domainmodel.data.Dealer
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import timber.log.Timber

class SharedViewModel(private val preferencesDatastore: PreferencesRepository) : ViewModel() {

    fun shouldNavigateToDashBoard(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val loggedIn =
                preferencesDatastore.getData(PreferenceDatastore.LOGGED_IN, false).take(1).first()
            val verified =
                preferencesDatastore.getData(PreferenceDatastore.VERIFIED, false).take(1).first()

            callback(loggedIn && verified)

        }
    }

    fun dealerData(callback: (Dealer?) -> Unit) {
        viewModelScope.launch {
            val dealer =
                preferencesDatastore.getDataObject(PreferenceDatastore.DEALER, Dealer::class.java)
                    .take(1).first()
            callback(dealer)
        }
    }

     fun saveReferCode(referCode: String) {
        viewModelScope.launch {
            preferencesDatastore.saveData(PreferenceDatastore.REFER_CODE_DEEP_LINK, referCode)
        }
    }

    fun clearData() {
        viewModelScope.launch {
            preferencesDatastore.clearData()
        }
    }

}