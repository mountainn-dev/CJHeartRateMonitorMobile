package com.san.heartratemonitormobile.domain.utils

abstract class InputValidationResult<T> {
    companion object {
        fun <T> valid(data: T): InputValidationResult<T> = Valid(data)
        fun <T> invalid(error: Exception): InputValidationResult<T> = Invalid(error)
    }
}

class Valid <T> (val data: T) : InputValidationResult<T>()

class Invalid <T> (private val error: Exception) : InputValidationResult<T>() {
    fun message() = error.message
}