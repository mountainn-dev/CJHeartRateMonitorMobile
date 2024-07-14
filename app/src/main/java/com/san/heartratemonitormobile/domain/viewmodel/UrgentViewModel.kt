package com.san.heartratemonitormobile.domain.viewmodel

import androidx.lifecycle.LiveData
import com.san.heartratemonitormobile.domain.model.ReportModel

interface UrgentViewModel {
    val reportsReady: LiveData<Boolean>
    var reports: List<ReportModel>

    fun load()
}