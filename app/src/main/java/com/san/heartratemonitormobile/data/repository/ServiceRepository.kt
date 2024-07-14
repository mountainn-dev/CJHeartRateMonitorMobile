package com.san.heartratemonitormobile.data.repository

import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.model.UserModel

interface ServiceRepository {
    // TODO: API 연동 시 Response 적용 및 api param, header 관련 설정 필요
    suspend fun getReports(): List<ReportModel>
    suspend fun getUsers(): List<UserModel>
}