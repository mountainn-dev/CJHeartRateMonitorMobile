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
import com.san.heartratemonitormobile.domain.utils.Const
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
    private val reportStartDate = MutableLiveData(LocalDate.parse(String.format(
        Const.DATE_FILTER_DEFAULT_START_DATE, LocalDate.now().year)))
    override val endDate: LiveData<LocalDate>
        get() = reportEndDate
    private val reportEndDate = MutableLiveData(LocalDate.now())
    private var idFilter = BLANK

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
            save = result.data
            filterReportsById()
            viewModelState.postValue(UiState.Success)
        } else {
            if ((result as Error).isTimeOut()) viewModelState.postValue(UiState.Timeout)
            else viewModelState.postValue(UiState.ServiceError)
        }
    }

    private fun filterReportsById() {
        reports = save.filter { it.id.get().contains(idFilter) }.sortedWith(
            compareByDescending<ReportModel> { it.reportDate }.thenByDescending { it.reportTime })
    }

    override fun setStartDate(date: LocalDate) {
        reportStartDate.value = date
    }

    override fun setEndDate(date: LocalDate) {
        reportEndDate.value = date
    }

    /**
     * setIdFilter()
     *
     * id 필터링은 api 통신 없이 로컬에서 진행되기 때문에 load() 대신 곧바로 notify
     */
    override fun setIdFilter(id: String) {
        idFilter = id

        if (viewModelState.value!! == UiState.Success) {
            filterReportsById()

            viewModelState.postValue(UiState.Success)
        }
    }

    companion object {
        private const val BLANK = ""
    }
}