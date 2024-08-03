package com.san.heartratemonitormobile.view.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.Error
import com.san.heartratemonitormobile.data.Success
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.domain.enums.Action
import com.san.heartratemonitormobile.domain.model.HeartRateModel
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.view.viewmodel.ReportDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime

class ReportDetailViewModelImpl(
    private val repository: HeartRateServiceRepository,
    private val reportModel: ReportModel
) : ReportDetailViewModel, ViewModel() {
    override val state: LiveData<UiState>
        get() = viewModelState
    private val viewModelState = MutableLiveData<UiState>(UiState.Loading)
    override val report: ReportModel = reportModel
    override lateinit var heartRateData: List<Int>


    init {
         load()
    }

    private fun load() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadHeartRate()
            }
        }
    }

    private suspend fun loadHeartRate() {
        val result = repository.getHeartRate(reportModel.id, reportModel.reportDate)

        if (result is Success) {
            heartRateData = result.data
            viewModelState.postValue(UiState.Success)
        } else {
            viewModelState.postValue(UiState.ServiceError)
        }
    }

    override fun setAction(action: Action) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                changeAction(action)
            }
        }
    }

    private suspend fun changeAction(action: Action) {
        val result = repository.setAction(reportModel.id, reportModel.reportDate, reportModel.reportTime, action)

        if (result is Error) {
            viewModelState.postValue(UiState.ServiceError)
        }
    }
}