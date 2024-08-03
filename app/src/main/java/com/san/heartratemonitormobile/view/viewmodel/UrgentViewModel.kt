package com.san.heartratemonitormobile.view.viewmodel

import androidx.lifecycle.LiveData
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState

interface UrgentViewModel {
    val state: LiveData<UiState>
    var reports: List<ReportModel>
    var workingUsers: List<UserModel>

    fun load()
}