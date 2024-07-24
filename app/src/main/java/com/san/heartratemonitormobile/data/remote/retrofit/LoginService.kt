package com.san.heartratemonitormobile.data.remote.retrofit

import com.san.heartratemonitormobile.data.entity.AccountEntity
import com.san.heartratemonitormobile.data.entity.LoginEntity
import com.san.heartratemonitormobile.data.entity.ServiceResponse
import com.san.heartratemonitormobile.data.entity.SignUpEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @GET("/register/duplication")
    suspend fun getIdDuplication(
        @Query("userId") id: String
    ): ServiceResponse<String?>

    @POST("/register")
    suspend fun signUp(
        @Body data: SignUpEntity
    ): ServiceResponse<String?>

    @POST("/login")
    suspend fun login(
        @Body data: LoginEntity
    ): ServiceResponse<AccountEntity>
}