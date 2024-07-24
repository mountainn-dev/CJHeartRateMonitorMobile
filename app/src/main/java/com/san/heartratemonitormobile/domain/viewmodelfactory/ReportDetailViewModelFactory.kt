package com.san.heartratemonitormobile.domain.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.viewmodelimpl.ReportDetailViewModelImpl

class ReportDetailViewModelFactory(
    private val repository: HeartRateServiceRepository,
    private val reportModel: ReportModel,
    private val id: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportDetailViewModelImpl::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReportDetailViewModelImpl(repository, reportModel, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}