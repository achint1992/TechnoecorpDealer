package com.technoecorp.gorilladealer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.technoecorp.data.repository.preference.PreferencesRepository

class SharedViewModelFactory(private val preferencesDatastore: PreferencesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(preferencesDatastore) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}