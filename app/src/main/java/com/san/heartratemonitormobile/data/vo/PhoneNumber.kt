package com.san.heartratemonitormobile.data.vo

import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import java.io.Serializable

data class PhoneNumber(
    private val value: String
) : Serializable {
    fun get() = value

    init {
        require(isNotEmpty()) { String.format(ExceptionMessage.NO_INPUT_DATA_EXCEPTION, PHONE_NUMBER) }
        require(isPhoneNumberFormat()) { ExceptionMessage.WRONG_PHONE_NUMBER_FORMAT_EXCEPTION }
    }

    private fun isNotEmpty() = value.isNotEmpty()

    private fun isPhoneNumberFormat(): Boolean {
        val pattern = Regex("^\\d{3}-\\d{4}-\\d{4}$")

        return pattern.matches(value)
    }

    companion object {
        private const val PHONE_NUMBER = "전화번호"
    }
}
