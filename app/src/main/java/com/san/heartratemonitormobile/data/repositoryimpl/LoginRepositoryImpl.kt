package com.san.heartratemonitormobile.data.repositoryimpl

import android.util.Log
import com.san.heartratemonitormobile.data.remote.retrofit.LoginService
import com.san.heartratemonitormobile.data.repository.LoginRepository
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.data.Result
import com.san.heartratemonitormobile.data.entity.LoginEntity
import com.san.heartratemonitormobile.data.exception.ServiceException
import com.san.heartratemonitormobile.data.remote.retrofit.ServiceResult
import com.san.heartratemonitormobile.domain.model.SignUpModel

class LoginRepositoryImpl(
    private val service: LoginService
) : LoginRepository {
    override suspend fun getIdDuplication(id: String): Result<Boolean> {
        try {
            service.getIdDuplication(id)
            return Result.success(false)
        } catch (e: Exception) {
            return Result.error(ServiceException.SignUpException(ServiceResult.ID_DUPLICATION.message))
        }
    }

    override suspend fun signUp(model: SignUpModel): Result<Boolean> {
        try {
            service.signUp(model.toSignUpEntity())
            return Result.success(true)
        } catch (e: Exception) {
            Log.d("signUp", e.toString())
            return Result.error(e)
        }
    }

    override suspend fun login(id: String, pw: String): Result<AccountModel> {
//        try {
//            val response = service.login(LoginEntity(id, pw))
//            return Result.success(response.data.toAccountModel())
//        } catch (e: Exception) {
//            Log.d("login", e.toString())
//            return Result.error(e)
//        }
        return Result.success(
            AccountModel(
                "",
                true
            )
        )
    }
}