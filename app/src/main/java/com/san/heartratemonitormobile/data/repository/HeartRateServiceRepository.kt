package com.san.heartratemonitormobile.data.repository

import com.san.heartratemonitormobile.data.Result
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.domain.enums.Action
import com.san.heartratemonitormobile.domain.model.HeartRateModel
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.model.UserModel
import java.time.LocalDate
import java.time.LocalTime

interface HeartRateServiceRepository {
    // TODO: API 연동 시 Response 적용 및 api param, header 관련 설정 필요
    suspend fun getAllUserActionNeededReports(start: LocalDate, end: LocalDate): Result<List<ReportModel>>
    suspend fun getAllUserReports(start: LocalDate, end: LocalDate): Result<List<ReportModel>>
    suspend fun getSingleUserActionNeededReports(id: Id, start: LocalDate, end: LocalDate): Result<List<ReportModel>>
    suspend fun getSingleUserReports(id: Id, start: LocalDate, end: LocalDate): Result<List<ReportModel>>
    suspend fun getWorkingUsers(): Result<List<UserModel>>
    suspend fun getAllUsers(start: LocalDate, end: LocalDate): Result<List<UserModel>>
    suspend fun getSingleUser(id: Id): Result<UserModel>
    suspend fun getHeartRate(id: Id, heartRateDate: LocalDate): Result<List<Int>>
    suspend fun setAction(id: Id, reportDate: LocalDate, reportTime: LocalTime, action: Action): Result<Boolean>
    suspend fun setThreshold(id: Id, threshold: Int): Result<Boolean>
}