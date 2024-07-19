package com.san.heartratemonitormobile.data.vo

import androidx.core.text.isDigitsOnly
import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import java.io.Serializable

data class Weight(
    private val value: String
) : Serializable {
    init {
        require(isNotEmpty()) { ExceptionMessage.NO_INPUT_DATA_EXCEPTION }
        require(hasOnlyNumber()) { ExceptionMessage.WRONG_WEIGHT_FORMAT_EXCEPTION }
    }

    private fun isNotEmpty() = value.isNotEmpty()

    private fun hasOnlyNumber() = value.isDigitsOnly()

    fun get() = value.toInt()
}
