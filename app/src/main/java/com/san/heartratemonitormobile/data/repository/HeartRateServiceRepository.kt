package com.san.heartratemonitormobile.data.repository

import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.model.UserModel
import java.time.LocalDate
import com.san.heartratemonitormobile.data.Result

interface HeartRateServiceRepository {
    // TODO: API 연동 시 Response 적용 및 api param, header 관련 설정 필요
    suspend fun getAllUserActionNeededReports(start: LocalDate, end: LocalDate): Result<List<ReportModel>>
    suspend fun getAllUserReports(start: LocalDate, end: LocalDate): Result<List<ReportModel>>
    suspend fun getSingleUserActionNeededReports(id: Id, start: LocalDate, end: LocalDate): Result<List<ReportModel>>
    suspend fun getSingleUserReports(id: Id, start: LocalDate, end: LocalDate): Result<List<ReportModel>>
    suspend fun getReports(): List<ReportModel>
    suspend fun getUsers(): List<UserModel>
    suspend fun getWorkingUsers(): Result<List<UserModel>>
    suspend fun getAllUsers(start: LocalDate, end: LocalDate): Result<List<UserModel>>
    suspend fun getSingleUser(id: Id, start: LocalDate, end: LocalDate): Result<UserModel>
}