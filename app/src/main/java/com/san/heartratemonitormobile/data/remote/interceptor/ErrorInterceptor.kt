package com.san.heartratemonitormobile.data.remote.interceptor

import com.san.heartratemonitormobile.data.exception.ServiceException
import com.san.heartratemonitormobile.data.remote.retrofit.ServiceResult.*
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val resultCode = response.code()

        parseServiceResult(resultCode)
        return chain.proceed(request)
    }

    private fun parseServiceResult(code: Int) {
        when (code) {
            SUCCESS.code -> {}
            WRONG_ID_PASSWORD.code -> throw ServiceException.LoginException(WRONG_ID_PASSWORD.message)
            NO_RESULT.code -> throw ServiceException.NoResultException(NO_RESULT.message)
            ID_DUPLICATION.code -> throw ServiceException.SignUpException(ID_DUPLICATION.message)
            else -> throw IOException(code.toString())
        }
    }
}