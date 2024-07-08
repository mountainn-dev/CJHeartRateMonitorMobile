package com.san.heartratemonitormobile.domain.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.domain.viewmodelimpl.LoginViewModelImpl

class LoginViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModelImpl::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModelImpl() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}