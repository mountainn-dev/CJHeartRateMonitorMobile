package com.san.heartratemonitormobile.data.remote.retrofit

import com.san.heartratemonitormobile.data.entity.ServiceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HeartRateDataService {
    @GET("/getHeartRate")
    suspend fun getHeartRate(
        @Query("userId") id: String,
        @Query("heartRateDate") date: String
    ): ServiceResponse<List<Int>>
}