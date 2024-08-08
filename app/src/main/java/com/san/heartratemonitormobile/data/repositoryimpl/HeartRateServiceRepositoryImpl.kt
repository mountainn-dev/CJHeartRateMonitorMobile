package com.san.heartratemonitormobile.data.repositoryimpl

import android.util.Log
import com.san.heartratemonitormobile.data.Result
import com.san.heartratemonitormobile.data.entity.ActionEntity
import com.san.heartratemonitormobile.data.entity.ThresholdEntity
import com.san.heartratemonitormobile.data.exception.ServiceException
import com.san.heartratemonitormobile.data.remote.retrofit.HeartRateDataService
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
import com.san.heartratemonitormobile.domain.model.HeartRateModel
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.model.UserModel
import java.time.LocalDate
import java.time.LocalTime

class HeartRateServiceRepositoryImpl(
    private val service: HeartRateService,
    private val dataService: HeartRateDataService
) : HeartRateServiceRepository {
    override suspend fun getAllUserActionNeededReports(
        start: LocalDate,
        end: LocalDate,
    ): Result<List<ReportModel>> {
        try {
            val response = service.getReportHistory(NO_PARAM, start.toString(), end.toString(), Action.NONE.code.toString())
            return Result.success(response.data.map { it.toReportModel() })
        } catch (e: ServiceException.NoResultException) {
            return Result.success(emptyList())
        } catch (e: Exception) {
            Log.e("reportException", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun getAllUserReports(
        start: LocalDate?,
        end: LocalDate?,
    ): Result<List<ReportModel>> {
        try {
            val response = service.getReportHistory(NO_PARAM,
                start?.toString() ?: NO_PARAM,
                end?.toString() ?: NO_PARAM,
                NO_PARAM)
            return Result.success(response.data.map { it.toReportModel() })
        } catch (e: ServiceException.NoResultException) {
            return Result.success(emptyList())
        }  catch (e: Exception) {
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
        } catch (e: ServiceException.NoResultException) {
            return Result.success(emptyList())
        }  catch (e: Exception) {
            Log.e("reportException", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun getSingleUserReports(
        id: Id,
        start: LocalDate?,
        end: LocalDate?,
    ): Result<List<ReportModel>> {
        try {
            val response = service.getReportHistory(
                id.get(),
                start?.toString() ?: NO_PARAM,
                end?.toString() ?: NO_PARAM,
                NO_PARAM)
            return Result.success(response.data.map { it.toReportModel() })
        } catch (e: ServiceException.NoResultException) {
            return Result.success(emptyList())
        }  catch (e: Exception) {
            Log.e("reportException", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun getWorkingUsers(): Result<List<UserModel>> {
        try {
            val response = service.getWorkingUser()
            return Result.success(response.data.map { it.toUserModel() })
        } catch (e: ServiceException.NoResultException) {
            return Result.success(emptyList())
        }  catch (e: Exception) {
            Log.d("workingUser", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun getAllUsers(start: LocalDate?, end: LocalDate?): Result<List<UserModel>> {
        try {
            val response = service.getUser(
                NO_PARAM,
                start?.toString() ?: NO_PARAM,
                end?.toString() ?: NO_PARAM,
            )
            return Result.success(response.data.map { it.toUserModel() })
        } catch (e: ServiceException.NoResultException) {
            return Result.success(emptyList())
        }  catch (e: Exception) {
            Log.d("User", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun getSingleUser(id: Id): Result<UserModel> {
        try {
            val response = service.getUser(id.get(), NO_PARAM, NO_PARAM)
            return Result.success(response.data[0].toUserModel())
        } catch (e: Exception) {
            Log.d("User", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun getHeartRate(
        id: Id,
        heartRateDate: LocalDate,
    ): Result<List<Int>> {
        try {
            val response = dataService.getHeartRate(id.get(), heartRateDate.toString())
            return Result.success(response.data)
        } catch (e: ServiceException.NoResultException) {
            return Result.success(emptyList())
        }  catch (e: Exception) {
            Log.d("HeartRate", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun setAction(
        id: Id,
        reportDate: LocalDate,
        reportTime: LocalTime,
        action: Action,
    ): Result<Boolean> {
        try {
            service.setAction(ActionEntity(id.get(), "$reportDate $reportTime", action.code.toString()))
            return Result.success(true)
        } catch (e: Exception) {
            Log.d("setAction", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun setThreshold(
        id: Id, threshold: Int
    ): Result<Boolean> {
        try {
            service.setThreshold(ThresholdEntity(id.get(), threshold.toString()))
            return Result.success(true)
        } catch (e: Exception) {
            Log.d("setThreshold", e.toString())
            return Result.error(e)
        }
    }

    companion object {
        private const val NO_PARAM = "NULL"
    }
}