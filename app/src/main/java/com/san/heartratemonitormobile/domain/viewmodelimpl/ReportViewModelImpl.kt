package com.san.heartratemonitormobile.domain.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.repository.ServiceRepository
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.viewmodel.ReportViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class ReportViewModelImpl(
    private val repository: ServiceRepository,
    private val account: AccountModel
) : ReportViewModel, ViewModel() {
    override val state: LiveData<UiState>
        get() = viewModelState
    private val viewModelState = MutableLiveData<UiState>(UiState.Loading)

    override lateinit var reports: List<ReportModel>
    private lateinit var save: List<ReportModel>   // Id 필터 적용을 위한 전체 신고 이력 세이브용 변수
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
        // TODO: api 개발 이후 getReports 에 start, end date param 추가 필요
        // TODO: 레포 getAllReports, getMyReports 나누어지면 admin 따라서 분기
        val result = repository.getReports()

        reports = result
        save = result
        viewModelState.postValue(UiState.Success)
    }

    override fun setStartDateAndLoad(date: LocalDate) {
        reportStartDate.postValue(date)
        load()
    }

    override fun setEndDateAndLoad(date: LocalDate) {
        reportEndDate.postValue(date)
        load()
    }

    override fun filterById(id: String) {
        reports = save.filter { it.id.get().contains(id) }

        viewModelState.postValue(UiState.Success)
    }
}