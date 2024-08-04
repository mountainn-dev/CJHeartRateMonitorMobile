package com.san.heartratemonitormobile.view.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
import com.san.heartratemonitormobile.view.viewmodelimpl.WorkerDetailViewModelImpl

class WorkerDetailViewModelFactory(
    private val repository: HeartRateServiceRepository,
    private val userId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkerDetailViewModelImpl::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkerDetailViewModelImpl(repository, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}