package com.san.heartratemonitormobile.data.repository

import com.san.heartratemonitormobile.data.Result
import com.san.heartratemonitormobile.data.entity.AccountEntity
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.model.SignUpModel

interface LoginRepository {
    suspend fun getIdDuplication(id: String): Result<Boolean>
    suspend fun signUp(model: SignUpModel): Result<Boolean>
    suspend fun login(id: String, pw: String): Result<AccountModel>
    suspend fun loginForWorker(): AccountModel   // TODO: 로그인 api 개발 이후 login() 으로 통일 및 id, pw param 추가 필요
    suspend fun loginForAdmin(): AccountModel
}