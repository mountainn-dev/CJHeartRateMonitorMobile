package com.san.heartratemonitormobile.domain.viewmodel

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
    fun setStartDateAndLoad(date: LocalDate)
    fun setEndDateAndLoad(date: LocalDate)
    fun filterById(id: String)
}