package com.san.heartratemonitormobile.domain.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
import com.san.heartratemonitormobile.domain.enums.Action
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.viewmodel.ReportDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportDetailViewModelImpl(
    private val repository: HeartRateServiceRepository,
    private val reportModel: ReportModel
) : ReportDetailViewModel, ViewModel() {
    override val state: LiveData<UiState>
        get() = viewModelState
    private val viewModelState = MutableLiveData<UiState>(UiState.Success)

    override val report: ReportModel = reportModel

    init {
        // TODO: 당일 심박수, 신고 이력 merge
        // TODO: load()
    }

    private fun load() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // TODO: 당일 심박수, 신고 이력 호출
            }
        }
    }

    override fun setAction(action: Action) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                changeAction(action)
                load()
            }
        }
    }

    private suspend fun changeAction(action: Action) {
//        val result = repository.updateAction(action)
    }
}