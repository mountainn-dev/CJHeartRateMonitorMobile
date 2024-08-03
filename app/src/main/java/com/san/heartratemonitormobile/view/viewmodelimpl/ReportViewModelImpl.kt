package com.san.heartratemonitormobile.view.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.Error
import com.san.heartratemonitormobile.data.Success
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.view.viewmodel.ReportViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class ReportViewModelImpl(
    private val repository: HeartRateServiceRepository,
    private val account: AccountModel,
    private val id: String
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
        val result = if (account.admin) repository.getAllUserReports(reportStartDate.value!!, reportEndDate.value!!)
        else repository.getSingleUserReports(Id(id), reportStartDate.value!!, reportEndDate.value!!)

        if (result is Success) {
            reports = result.data
            save = result.data
            viewModelState.postValue(UiState.Success)
        } else {
            if ((result as Error).isTimeOut()) viewModelState.postValue(UiState.Timeout)
            else viewModelState.postValue(UiState.ServiceError)
        }
    }

    // postValue 이후 해당 value 를 곧바로 load 기능에서 param 으로 사용하는 경우, postValue 가 아닌
    // setValue 방식으로 value 를 수정. postValue 의 경우 load() 직전에 수정 완료가 되지 않아 param 이 잘못 전달되는 상황이 발생한다.
    override fun setStartDateAndLoad(date: LocalDate) {
        reportStartDate.value = date
        load()
    }

    override fun setEndDateAndLoad(date: LocalDate) {
        reportEndDate.value = date
        load()
    }

    override fun filterById(id: String) {
        reports = save.filter { it.id.get().contains(id) }

        viewModelState.postValue(UiState.Success)
    }
}