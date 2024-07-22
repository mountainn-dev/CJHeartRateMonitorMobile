package com.san.heartratemonitormobile.data.repositoryimpl

import com.san.heartratemonitormobile.data.remote.retrofit.LoginService
import com.san.heartratemonitormobile.data.repository.LoginRepository
import com.san.heartratemonitormobile.domain.model.AccountModel

class LoginRepositoryImpl(
    private val service: LoginService
) : LoginRepository {
    override suspend fun loginForWorker(): AccountModel {
        return AccountModel("testToken", false)
    }

    override suspend fun loginForAdmin(): AccountModel {
        return AccountModel("testToken", true)
    }
}