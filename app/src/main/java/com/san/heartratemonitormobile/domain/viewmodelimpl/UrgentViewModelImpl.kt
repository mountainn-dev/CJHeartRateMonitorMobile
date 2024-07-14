package com.san.heartratemonitormobile.domain.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.repository.ServiceRepository
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.viewmodel.UrgentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UrgentViewModelImpl(
    private val repository: ServiceRepository
) : UrgentViewModel, ViewModel() {
    override val reportsReady: LiveData<Boolean>
        get() = reportContentReady
    private val reportContentReady = MutableLiveData<Boolean>()
    override lateinit var reports: List<ReportModel>

    init {
        load()
    }

    override fun load() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadReportContent()
            }
        }
    }

    private suspend fun loadReportContent() {
        val result = repository.getReports()

        this.reports = result
        reportContentReady.postValue(true)
    }
}