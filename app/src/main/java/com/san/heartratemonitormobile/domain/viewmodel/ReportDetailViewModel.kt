package com.san.heartratemonitormobile.domain.viewmodel

import androidx.lifecycle.LiveData
import com.san.heartratemonitormobile.domain.enums.Action
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.state.UiState

interface ReportDetailViewModel {
    val state: LiveData<UiState>
    val report: ReportModel

    fun setAction(action: Action)
}