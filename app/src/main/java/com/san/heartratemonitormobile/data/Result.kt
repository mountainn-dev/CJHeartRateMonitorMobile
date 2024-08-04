package com.san.heartratemonitormobile.data

import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import com.san.heartratemonitormobile.data.exception.ServiceException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class Result<T> {
    companion object {
        fun <T> success(data: T): Result<T> = Success(data)
        fun <T> error(error: Exception): Result<T> = Error(error)
    }
}

class Success <T> (val data: T) : Result<T>()

class Error <T> (private val error: Exception) : Result<T>() {
    fun message(): String {
        return when(error) {
            is UnknownHostException -> ExceptionMessage.UNSTABLE_INTERNET_CONNECTION
            is ServiceException.ServerException -> ExceptionMessage.UNSTABLE_SERVER_EXCEPTION
            else -> error.message ?: error.toString()
        }
    }

    fun isCritical() = error is UnknownHostException || error is ServiceException.ServerException   // 인터넷 연결 혹은 서버 에러
    fun isTimeOut() = error is SocketTimeoutException   // 요청 시간 초과
}