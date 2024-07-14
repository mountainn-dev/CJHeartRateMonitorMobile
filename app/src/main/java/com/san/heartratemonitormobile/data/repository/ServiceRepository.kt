package com.san.heartratemonitormobile.data.repository

import com.san.heartratemonitormobile.domain.model.ReportModel

interface ServiceRepository {
    suspend fun getReports(): List<ReportModel>
}