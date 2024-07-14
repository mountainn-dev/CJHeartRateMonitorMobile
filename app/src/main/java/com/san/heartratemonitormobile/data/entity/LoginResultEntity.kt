package com.san.heartratemonitormobile.data.entity

import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import com.san.heartratemonitormobile.domain.model.LoginResultModel

data class LoginResultEntity(
    val token: String,
    val isAdmin: Int
) {
    fun toLoginModel() = LoginResultModel(
        token,
        admin(isAdmin)
    )

    private fun admin(isAdmin: Int) = when (isAdmin) {
        0 -> false
        1 -> true
        else -> throw NoSuchElementException(ExceptionMessage.WRONG_ADMIN_VALUE_EXCEPTION)
    }
}
