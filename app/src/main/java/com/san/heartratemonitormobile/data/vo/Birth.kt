package com.san.heartratemonitormobile.data.vo

import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import java.time.LocalDate

data class Birth(
    private val value: String
) {
    fun get() = LocalDate.parse(value)

    init {
        require(isNotEmpty()) { String.format(ExceptionMessage.NO_INPUT_DATA_EXCEPTION, BIRTH) }
        require(isDateFormat()) { ExceptionMessage.WRONG_DATE_FORMAT_EXCEPTION }
    }

    private fun isNotEmpty() = value.isNotEmpty()

    private fun isDateFormat(): Boolean {
        val pattern = Regex("^\\d{4}-\\d{2}-\\d{2}\$")

        return pattern.matches(value)
    }

    companion object {
        private const val BIRTH = "생년월일"
    }
}
