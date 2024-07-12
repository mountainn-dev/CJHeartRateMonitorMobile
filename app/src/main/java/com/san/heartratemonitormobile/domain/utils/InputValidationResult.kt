package com.san.heartratemonitormobile.domain.utils

abstract class InputValidationResult {
    companion object {
        fun valid(data: String): InputValidationResult = Valid(data)
        fun invalid(error: Exception): InputValidationResult = Invalid(error)
    }
}

class Valid(val data: String) : InputValidationResult()

class Invalid(private val error: Exception) : InputValidationResult() {
    fun message() = error.message
}