package com.technoecorp.gorilladealer.ui.kyc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.dealer.UpdateKYCUseCase

class KycViewModelFactory(
    private var updateKYCUseCase: UpdateKYCUseCase,
    private var preferencesRepository: PreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KycViewModel::class.java)) {
            return KycViewModel(updateKYCUseCase, preferencesRepository) as T
        }
        throw IllegalArgumentException("Invalid View Model")
    }
}