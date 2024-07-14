package com.san.heartratemonitormobile.domain.viewmodel

import androidx.lifecycle.LiveData
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.model.UserModel

interface UrgentViewModel {
    val reportsReady: LiveData<Boolean>
    var reports: List<ReportModel>
    val workingUsersReady: LiveData<Boolean>
    var workingUsers: List<UserModel>

    fun load()
}