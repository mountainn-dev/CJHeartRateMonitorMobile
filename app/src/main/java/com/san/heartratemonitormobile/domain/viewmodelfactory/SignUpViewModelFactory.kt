package com.san.heartratemonitormobile.domain.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.domain.viewmodelimpl.SignUpViewModelImpl

class SignUpViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModelImpl::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModelImpl() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}