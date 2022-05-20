package com.technoecorp.gorilladealer.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.auth.LoginDealerUseCase
import com.technoecorp.domain.usecase.auth.RegisterDealerUseCase

class RegisterViewModelFactory(
    private var loginDealerUseCase: LoginDealerUseCase,
    private var registerDealerUseCase: RegisterDealerUseCase,
    private var preferencesDatastore: PreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                loginDealerUseCase,
                registerDealerUseCase,
                preferencesDatastore
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}