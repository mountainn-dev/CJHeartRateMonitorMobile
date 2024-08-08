package com.san.heartratemonitormobile.view.viewmodel

import androidx.lifecycle.LiveData
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState
import java.time.LocalDate

interface UserDetailViewModel {
    val state: LiveData<UiState>
    val user: UserModel
    val heartRateData: List<Int>
    val dateFilter: LiveData<LocalDate>
    val heartRateAverage: Int
    val heartRateMax: Int

    fun load()
    fun setThreshold(threshold: Int)
    fun setDateFilter(date: LocalDate)
}