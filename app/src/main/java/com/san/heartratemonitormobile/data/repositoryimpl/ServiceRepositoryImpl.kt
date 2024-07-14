package com.san.heartratemonitormobile.data.repositoryimpl

import com.san.heartratemonitormobile.data.repository.ServiceRepository
import com.san.heartratemonitormobile.domain.model.AccountModel

class ServiceRepositoryImpl : ServiceRepository {
    override suspend fun loginForWorker(): AccountModel {
        return AccountModel("testToken", false)
    }

    override suspend fun loginForAdmin(): AccountModel {
        return AccountModel("testToken", true)
    }
}