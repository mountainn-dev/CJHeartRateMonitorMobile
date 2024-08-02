package com.san.heartratemonitormobile.data.repositoryimpl

import android.util.Log
import com.san.heartratemonitormobile.data.Result
import com.san.heartratemonitormobile.data.entity.ActionEntity
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

class HeartRateServiceRepositoryImpl(private val service: HeartRateService) : HeartRateServiceRepository {
    override suspend fun getAllUserActionNeededReports(
        start: LocalDate,
        end: LocalDate,
    ): Result<List<ReportModel>> {
//        try {
//            val response = service.getReportHistory(null, start.toString(), end.toString(), Action.NONE.code.toString())
//            return Result.success(response.data.map { it.toReportModel() })
//        } catch (e: Exception) {
//            Log.e("reportException", e.toString())
//            return Result.error(e)
//        }
        return Result.success(
            listOf(
                ReportModel(
                    Id("worker11"),
                    Name("ABC"),
                    PhoneNumber("01012345678"),
                    Gender.MALE,
                    Birth("2000-01-01"),
                    Height("180"),
                    Weight("80"),
                    140,
                    2,
                    2,
                    150,
                    LocalDate.now(),
                    LocalTime.now(),
                    Action.NONE,
                    127.0286f,
                    37.263573f
                ),
                ReportModel(
                    Id("worker12"),
                    Name("DEF"),
                    PhoneNumber("01012345678"),
                    Gender.MALE,
                    Birth("1999-01-01"),
                    Height("170"),
                    Weight("70"),
                    150,
                    1,
                    1,
                    168,
                    LocalDate.now(),
                    LocalTime.now(),
                    Action.NONE,
                    127.0286f,
                    37.263573f
                )
            )
        )
    }

    override suspend fun getAllUserReports(
        start: LocalDate,
        end: LocalDate,
    ): Result<List<ReportModel>> {
//        try {
//            val response = service.getReportHistory(null, start.toString(), end.toString(), null)
//            return Result.success(response.data.map { it.toReportModel() })
//        } catch (e: Exception) {
//            Log.e("reportException", e.toString())
//            return Result.error(e)
//        }
        return Result.success(
            listOf(
                ReportModel(
                    Id("worker13"),
                    Name("GHI"),
                    PhoneNumber("01012345678"),
                    Gender.MALE,
                    Birth("2000-01-01"),
                    Height("180"),
                    Weight("80"),
                    140,
                    1,
                    0,
                    150,
                    LocalDate.now(),
                    LocalTime.now(),
                    Action.REST,
                    127.0286f,
                    37.263573f
                ),
                ReportModel(
                    Id("worker11"),
                    Name("ABC"),
                    PhoneNumber("01012345678"),
                    Gender.MALE,
                    Birth("2000-01-01"),
                    Height("180"),
                    Weight("80"),
                    140,
                    2,
                    2,
                    150,
                    LocalDate.now(),
                    LocalTime.now(),
                    Action.NONE,
                    127.0286f,
                    37.263573f
                ),
                ReportModel(
                    Id("worker12"),
                    Name("DEF"),
                    PhoneNumber("01012345678"),
                    Gender.MALE,
                    Birth("1999-01-01"),
                    Height("170"),
                    Weight("70"),
                    150,
                    1,
                    1,
                    168,
                    LocalDate.now(),
                    LocalTime.now(),
                    Action.NONE,
                    127.0286f,
                    37.263573f
                )
            )
        )
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
//        try {
//            val response = service.getWorkingUser()
//            return Result.success(response.data.map { it.toUserModel() })
//        } catch (e: Exception) {
//            Log.d("workingUser", e.toString())
//            return Result.error(e)
//        }
        return Result.success(
            listOf(
                UserModel(
                    Id("worker11"),
                    Name("workerABC"),
                    PhoneNumber("01012345678"),
                    Gender.MALE,
                    Birth("2000-01-01"),
                    Height("180"),
                    Weight("80"),
                    2,
                    1,
                    2,
                    130,
                )
            )
        )
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

    override suspend fun getHeartRate(
        id: Id,
        heartRateDate: LocalDate,
    ): Result<List<Int>> {
//        try {
//            val response = service.getHeartRate(id.get(), heartRateDate.toString())
//            return Result.success(response.data)
//        } catch (e: Exception) {
//            Log.d("HeartRate", e.toString())
//            return Result.error(e)
//        }
        return Result.success(
            listOf(
                0, 80, 81, 82, 84, 87, 90, 88, 92, 100, 120
            )
        )
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
}