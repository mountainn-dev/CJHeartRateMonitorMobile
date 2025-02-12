package com.san.heartratemonitormobile.data.entity

import com.google.gson.annotations.SerializedName
import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import com.san.heartratemonitormobile.domain.model.AccountModel

data class AccountEntity(
    @SerializedName("accessToken") val token: String,
    @SerializedName("level") val isAdmin: Int
) {
    fun toAccountModel() = AccountModel(
        token,
        admin(isAdmin)
    )

    private fun admin(isAdmin: Int) = when (isAdmin) {
        WORKER -> false
        ADMIN -> true
        else -> throw NoSuchElementException(ExceptionMessage.WRONG_ADMIN_VALUE_EXCEPTION)
    }

    companion object {
        private const val WORKER = 0
        private const val ADMIN = 1
    }
}
