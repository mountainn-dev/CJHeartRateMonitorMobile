package com.san.heartratemonitormobile.view.viewmodel

import androidx.lifecycle.LiveData
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.state.UiState
import java.time.LocalDate

interface ReportViewModel {
    val state: LiveData<UiState>
    val reports: List<ReportModel>
    val startDate: LiveData<LocalDate>
    val endDate: LiveData<LocalDate>

    fun load()
    fun setStartDate(date: LocalDate)
    fun setEndDate(date: LocalDate)
    fun setIdFilter(id: String)
}