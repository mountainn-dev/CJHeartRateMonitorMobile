package com.san.heartratemonitormobile.view.viewmodel

import androidx.lifecycle.LiveData
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState
import java.time.LocalDate

interface UserViewModel {
    val state: LiveData<UiState>
    val users: List<UserModel>
    val startDate: LiveData<LocalDate>
    val endDate: LiveData<LocalDate>

    fun load()
    fun setStartDate(date: LocalDate)
    fun setEndDate(date: LocalDate)
    fun setIdFilter(id: String)
}