package com.san.heartratemonitormobile.data.vo

import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import java.io.Serializable

data class Name(
    private val value: String
) : Serializable {
    fun get() = value

    init {
        require(isNotEmpty()) { String.format(ExceptionMessage.NO_INPUT_DATA_EXCEPTION, NAME) }
        require(hasNotBlank()) { ExceptionMessage.WRONG_NAME_FORMAT_EXCEPTION }
        require(hasOnlyLetter()) { ExceptionMessage.WRONG_NAME_FORMAT_EXCEPTION }
    }

    private fun isNotEmpty() = value.isNotEmpty()

    private fun hasNotBlank() = !value.contains(BLANK)

    private fun hasOnlyLetter() = value.toCharArray().all { it.isLetter() }

    companion object {
        private const val NAME = "이름"
        private const val BLANK = " "
    }
}
