package com.san.heartratemonitormobile.domain.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.data.repository.ServiceRepository
import com.san.heartratemonitormobile.domain.viewmodelimpl.SignUpViewModelImpl
import com.san.heartratemonitormobile.domain.viewmodelimpl.WorkingViewModelImpl

class WorkingViewModelFactory(private val repository: ServiceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkingViewModelImpl::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkingViewModelImpl(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}