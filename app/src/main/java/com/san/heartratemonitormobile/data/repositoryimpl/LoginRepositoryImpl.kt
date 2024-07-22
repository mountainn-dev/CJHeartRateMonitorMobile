package com.san.heartratemonitormobile.data.repositoryimpl

import com.san.heartratemonitormobile.data.remote.retrofit.LoginService
import com.san.heartratemonitormobile.data.repository.LoginRepository
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.data.Result
import com.san.heartratemonitormobile.data.exception.ServiceException
import com.san.heartratemonitormobile.data.remote.retrofit.ServiceResult
import com.san.heartratemonitormobile.domain.model.SignUpModel
import java.io.IOException

class LoginRepositoryImpl(
    private val service: LoginService
) : LoginRepository {
    override suspend fun getIdDuplication(id: String): Result<Boolean> {
        try {
            val response = service.getIdDuplication(id)

            if (response.message == "데이터 없음") return Result.success(false)
            else throw ServiceException.SignUpException(ServiceResult.ID_DUPLICATION.message)
        } catch (e: Exception) {
            return Result.error(e)
        }
    }

    override suspend fun signUp(model: SignUpModel): Result<Boolean> {
        try {
            service.signUp(model.toSignUpEntity())

            return Result.success(true)
        } catch (e: Exception) {
            return Result.error(e)
        }
    }

    override suspend fun loginForWorker(): AccountModel {
        return AccountModel("testToken", false)
    }

    override suspend fun loginForAdmin(): AccountModel {
        return AccountModel("testToken", true)
    }
}