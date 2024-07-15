package com.san.heartratemonitormobile.domain.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.repository.ServiceRepository
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.viewmodel.ReportViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class ReportViewModelImpl(
    private val repository: ServiceRepository
) : ReportViewModel, ViewModel() {
    override val state: LiveData<UiState>
        get() = viewModelState
    private val viewModelState = MutableLiveData<UiState>(UiState.Loading)

    override lateinit var reports: List<ReportModel>
    private lateinit var temp: List<ReportModel>
    override val startDate: LiveData<LocalDate>
        get() = reportStartDate
    private val reportStartDate = MutableLiveData(LocalDate.now())
    override val endDate: LiveData<LocalDate>
        get() = reportEndDate
    private val reportEndDate = MutableLiveData(LocalDate.now())

    override fun load() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadReportContent()
            }
        }
    }

    private suspend fun loadReportContent() {
        // api 개발 이후 getReports 에 start, end date param 추가 필요
        val result = repository.getReports()

        reports = result
        temp = result
        viewModelState.postValue(UiState.Success)
    }

    override fun setStartDate(date: LocalDate) {
        reportStartDate.postValue(date)
        load()
    }

    override fun setEndDate(date: LocalDate) {
        reportEndDate.postValue(date)
        load()
    }

    override fun filterById(id: String) {
        reports = temp.filter { it.id.get().contains(id) }

        viewModelState.postValue(UiState.Success)
    }
}