package com.san.heartratemonitormobile.data.vo

import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import java.io.Serializable

data class PassWord(
    private val value: String
) : Serializable {
    fun get() = value

    init {
        require(isNotEmpty()) { String.format(ExceptionMessage.NO_INPUT_DATA_EXCEPTION, PASSWORD) }
        require(hasOnlyLimitedCount()) { ExceptionMessage.WRONG_PASS_WORD_FORMAT_EXCEPTION }
        require(hasOnlyNumberAndAlphabetAndSymbol()) { ExceptionMessage.WRONG_PASS_WORD_FORMAT_EXCEPTION }
    }

    private fun isNotEmpty() = value.isNotEmpty()

    private fun hasOnlyLimitedCount() = value.length >= MIN_PASSWORD_LENGTH

    private fun hasOnlyNumberAndAlphabetAndSymbol(): Boolean {
        var hasNumber = false
        var hasAlphabet = false
        var hasSymbol = false
        var hasOther = false

        for (c in value) {
            if (isNumber(c)) hasNumber = true
            else if (isAlphabet(c)) hasAlphabet = true
            else if (isSymbol(c)) hasSymbol = true
            else {
                hasOther = true
                break
            }
        }

        return hasNumber && hasAlphabet && hasSymbol && !hasOther
    }

    private fun isNumber(data: Char) = data.isDigit()

    private fun isAlphabet(data: Char) =
        data in FIRST_UPPER_ALPHABET..LAST_UPPER_ALPHABET || data in FIRST_LOWER_ALPHABET..LAST_LOWER_ALPHABET

    private fun isSymbol(data: Char): Boolean {
        val type = Character.getType(data)

        return when (type) {
            Character.MATH_SYMBOL.toInt(),
            Character.CURRENCY_SYMBOL.toInt(),
            Character.MODIFIER_SYMBOL.toInt(),
            Character.OTHER_SYMBOL.toInt() -> true
            else -> false
        }
    }

    companion object {
        private const val PASSWORD = "비밀번호"
        private const val MIN_PASSWORD_LENGTH = 8
        private const val FIRST_UPPER_ALPHABET = 'A'
        private const val LAST_UPPER_ALPHABET = 'Z'
        private const val FIRST_LOWER_ALPHABET = 'a'
        private const val LAST_LOWER_ALPHABET = 'z'
    }
}
