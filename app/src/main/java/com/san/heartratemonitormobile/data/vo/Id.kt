package com.san.heartratemonitormobile.data.vo

import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import java.io.Serializable

data class Id(
    private val value: String
) : Serializable {
    fun get() = value

    init {
        require(isNotEmpty()) { String.format(ExceptionMessage.NO_INPUT_DATA_EXCEPTION, ID) }
        require(hasOnlyLimitedCount()) { ExceptionMessage.WRONG_ID_FORMAT_EXCEPTION }
        require(hasOnlyNumberAndAlphabet()) { ExceptionMessage.WRONG_ID_FORMAT_EXCEPTION }
    }

    private fun isNotEmpty() = value.isNotEmpty()

    private fun hasOnlyLimitedCount() = value.length in MIN_ID_LENGTH..MAX_ID_LENGTH

    private fun hasOnlyNumberAndAlphabet(): Boolean {
        var hasNumber = false
        var hasAlphabet = false
        var hasOther = false

        for (c in value) {
            if (isNumber(c)) hasNumber = true
            else if (isAlphabet(c)) hasAlphabet = true
            else {
                hasOther = true
                break
            }
        }

        return hasNumber && hasAlphabet && !hasOther
    }

    private fun isNumber(data: Char) = data.isDigit()

    private fun isAlphabet(data: Char) =
        data in FIRST_UPPER_ALPHABET..LAST_UPPER_ALPHABET || data in FIRST_LOWER_ALPHABET..LAST_LOWER_ALPHABET

    companion object {
        private const val ID = "아이디"
        private const val MIN_ID_LENGTH = 4
        private const val MAX_ID_LENGTH = 12
        private const val FIRST_UPPER_ALPHABET = 'A'
        private const val LAST_UPPER_ALPHABET = 'Z'
        private const val FIRST_LOWER_ALPHABET = 'a'
        private const val LAST_LOWER_ALPHABET = 'z'
    }
}
