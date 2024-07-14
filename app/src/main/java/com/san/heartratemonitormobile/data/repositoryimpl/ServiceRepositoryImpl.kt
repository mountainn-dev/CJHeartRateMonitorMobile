package com.san.heartratemonitormobile.data.repositoryimpl

import com.san.heartratemonitormobile.data.repository.ServiceRepository
import com.san.heartratemonitormobile.domain.model.LoginResultModel

class ServiceRepositoryImpl : ServiceRepository {
    override suspend fun loginForWorker(): LoginResultModel {
        return LoginResultModel("testToken", false)
    }

    override suspend fun loginForAdmin(): LoginResultModel {
        return LoginResultModel("testToken", true)
    }
}