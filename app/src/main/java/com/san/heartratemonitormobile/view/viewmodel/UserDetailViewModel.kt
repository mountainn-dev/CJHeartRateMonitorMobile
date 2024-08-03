package com.san.heartratemonitormobile.view.viewmodel

import androidx.lifecycle.LiveData
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState

interface UserDetailViewModel {
    val state: LiveData<UiState>
    val user: UserModel
    val heartRateData: List<Int>

    fun setThreshold(threshold: Int)
}