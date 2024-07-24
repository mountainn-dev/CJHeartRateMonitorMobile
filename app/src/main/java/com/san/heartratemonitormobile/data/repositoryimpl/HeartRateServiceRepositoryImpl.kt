package com.san.heartratemonitormobile.data.repositoryimpl

import android.util.Log
import com.san.heartratemonitormobile.data.Result
import com.san.heartratemonitormobile.data.remote.retrofit.HeartRateService
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
import com.san.heartratemonitormobile.data.vo.Birth
import com.san.heartratemonitormobile.data.vo.Height
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.data.vo.Name
import com.san.heartratemonitormobile.data.vo.PhoneNumber
import com.san.heartratemonitormobile.data.vo.Weight
import com.san.heartratemonitormobile.domain.enums.Action
import com.san.heartratemonitormobile.domain.enums.Gender
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.model.UserModel
import java.time.LocalDate
import java.time.LocalTime

class HeartRateServiceRepositoryImpl(private val service: HeartRateService) : HeartRateServiceRepository {
    override suspend fun getAllUserActionNeededReports(
        start: LocalDate,
        end: LocalDate,
    ): Result<List<ReportModel>> {
        try {
            val response = service.getReportHistory(null, start.toString(), end.toString(), Action.NONE.code.toString())
            return Result.success(response.data.map { it.toReportModel() })
        } catch (e: Exception) {
            Log.e("reportException", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun getAllUserReports(
        start: LocalDate,
        end: LocalDate,
    ): Result<List<ReportModel>> {
        try {
            val response = service.getReportHistory(null, start.toString(), end.toString(), null)
            return Result.success(response.data.map { it.toReportModel() })
        } catch (e: Exception) {
            Log.e("reportException", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun getSingleUserActionNeededReports(
        id: Id,
        start: LocalDate,
        end: LocalDate,
    ): Result<List<ReportModel>> {
        try {
            val response = service.getReportHistory(id.get(), start.toString(), end.toString(), Action.NONE.code.toString())
            return Result.success(response.data.map { it.toReportModel() })
        } catch (e: Exception) {
            Log.e("reportException", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun getSingleUserReports(
        id: Id,
        start: LocalDate,
        end: LocalDate,
    ): Result<List<ReportModel>> {
        try {
            val response = service.getReportHistory(id.get(), start.toString(), end.toString(), null)
            return Result.success(response.data.map { it.toReportModel() })
        } catch (e: Exception) {
            Log.e("reportException", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun getWorkingUsers(): Result<List<UserModel>> {
        try {
            val response = service.getWorkingUser()
            return Result.success(response.data.map { it.toUserModel() })
        } catch (e: Exception) {
            Log.d("workingUser", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun getAllUsers(start: LocalDate, end: LocalDate): Result<List<UserModel>> {
        try {
            val response = service.getUser(null, start.toString(), end.toString())
            return Result.success(response.data.map { it.toUserModel() })
        } catch (e: Exception) {
            Log.d("User", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun getSingleUser(id: Id, start: LocalDate, end: LocalDate): Result<UserModel> {
        try {
            val response = service.getUser(id.get(), start.toString(), end.toString())
            return Result.success(response.data[0].toUserModel())
        } catch (e: Exception) {
            Log.d("User", e.toString())
            return Result.error(e)
        }
    }
}