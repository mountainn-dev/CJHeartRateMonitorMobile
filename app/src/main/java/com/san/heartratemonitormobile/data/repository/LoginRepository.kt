package com.san.heartratemonitormobile.data.repository

import com.san.heartratemonitormobile.data.Result
import com.san.heartratemonitormobile.data.entity.AccountEntity
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.model.SignUpModel

interface LoginRepository {
    suspend fun getIdDuplication(id: String): Result<Boolean>
    suspend fun signUp(model: SignUpModel): Result<Boolean>
    suspend fun login(id: String, pw: String): Result<AccountModel>
}