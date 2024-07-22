package com.san.heartratemonitormobile.data.vo

import androidx.core.text.isDigitsOnly
import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import java.io.Serializable

data class Height(
    private val value: String
) : Serializable {
    fun get() = value.toInt()

    init {
        require(isNotEmpty()) { String.format(ExceptionMessage.NO_INPUT_DATA_EXCEPTION, HEIGHT) }
        require(hasOnlyNumber()) { ExceptionMessage.WRONG_HEIGHT_FORMAT_EXCEPTION }
    }

    private fun isNotEmpty() = value.isNotEmpty()

    private fun hasOnlyNumber() = value.isDigitsOnly()

    companion object {
        private const val HEIGHT = "신장"
    }
}
