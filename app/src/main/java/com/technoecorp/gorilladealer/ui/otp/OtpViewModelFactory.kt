package com.technoecorp.gorilladealer.ui.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.auth.VerifyUserUseCase

class OtpViewModelFactory(
    private var verifyUserUseCase: VerifyUserUseCase,
    private var datastore: PreferencesRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OtpViewModel::class.java)) {
            return OtpViewModel(verifyUserUseCase, datastore) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}