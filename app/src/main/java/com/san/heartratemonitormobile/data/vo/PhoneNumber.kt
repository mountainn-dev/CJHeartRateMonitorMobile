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
        val pattern = Regex("^010\\d{8}\$")

        return pattern.matches(value)
    }

    fun first() = value.substring(0, 3)
    fun mid() = value.substring(3, 7)
    fun last() = value.substring(7, 11)

    companion object {
        private const val PHONE_NUMBER = "전화번호"
    }
}
