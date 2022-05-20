package com.technoecorp.gorilladealer.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.auth.LoginDealerUseCase

class LoginViewModelFactory(
    private var loginDealerUseCase: LoginDealerUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginDealerUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}