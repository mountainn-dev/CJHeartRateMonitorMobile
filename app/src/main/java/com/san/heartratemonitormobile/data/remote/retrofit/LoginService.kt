package com.san.heartratemonitormobile.data.remote.retrofit

import com.san.heartratemonitormobile.data.entity.ServiceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginService {
    @GET("/register/duplication")
    suspend fun getIdDuplication(
        @Query("userId") id: String
    ): ServiceResponse<String?>
}