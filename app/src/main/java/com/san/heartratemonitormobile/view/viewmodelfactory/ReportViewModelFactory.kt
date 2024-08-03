package com.san.heartratemonitormobile.view.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.view.viewmodelimpl.ReportViewModelImpl

class ReportViewModelFactory(
    private val repository: HeartRateServiceRepository,
    private val account: AccountModel,
    private val id: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportViewModelImpl::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReportViewModelImpl(repository, account, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}