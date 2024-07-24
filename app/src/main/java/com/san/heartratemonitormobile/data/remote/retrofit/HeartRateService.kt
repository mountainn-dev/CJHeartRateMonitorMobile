package com.san.heartratemonitormobile.data.remote.retrofit

import com.san.heartratemonitormobile.data.entity.ReportEntity
import com.san.heartratemonitormobile.data.entity.ServiceResponse
import com.san.heartratemonitormobile.data.entity.UserEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface HeartRateService {
    @GET("/reportHistory")
    suspend fun getReportHistory(
        @Query("userId") id: String?,
        @Query("startDate") start: String,
        @Query("endDate") end: String,
        @Query("action") action: String?
    ): ServiceResponse<List<ReportEntity>>

    @GET("/workNow")
    suspend fun getWorkingUser(): ServiceResponse<List<UserEntity>>

    @GET("/member")
    suspend fun getUser(
        @Query("userId") id: String?,
        @Query("startWorkDate") start: String,
        @Query("endWorkDate") end: String
    ): ServiceResponse<List<UserEntity>>
}