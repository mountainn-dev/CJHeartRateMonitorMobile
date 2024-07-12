package com.san.heartratemonitormobile.domain.utils

import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import com.san.heartratemonitormobile.data.vo.Birth
import com.san.heartratemonitormobile.data.vo.Height
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.data.vo.Name
import com.san.heartratemonitormobile.data.vo.PassWord
import com.san.heartratemonitormobile.data.vo.PhoneNumber
import com.san.heartratemonitormobile.data.vo.Weight

object InputValidator {
    fun checkId(data: String): InputValidationResult {
        try {
            Id(data)
            return InputValidationResult.valid(data)
        } catch (e: IllegalArgumentException) {
            return InputValidationResult.invalid(e)
        }
    }

    fun checkPassWord(data: String): InputValidationResult {
        try {
            PassWord(data)
            return InputValidationResult.valid(data)
        } catch (e: IllegalArgumentException) {
            return InputValidationResult.invalid(e)
        }
    }

    fun doubleCheckPassWord(data1: String, data2: String): InputValidationResult {
        return if (data1 == data2) InputValidationResult.valid(data1)
        else return InputValidationResult.invalid(IllegalArgumentException(ExceptionMessage.WRONG_CHECK_PASS_WORD_EXCEPTION))
    }

    fun checkName(data: String): InputValidationResult {
        try {
            Name(data)
            return InputValidationResult.valid(data)
        } catch (e: IllegalArgumentException) {
            return InputValidationResult.invalid(e)
        }
    }

    fun checkPhoneNumber(data: String): InputValidationResult {
        try {
            PhoneNumber(data)
            return InputValidationResult.valid(data)
        } catch (e: IllegalArgumentException) {
            return InputValidationResult.invalid(e)
        }
    }

    fun checkBirth(data: String): InputValidationResult {
        try {
            Birth(data)
            return InputValidationResult.valid(data)
        } catch (e: IllegalArgumentException) {
            return InputValidationResult.invalid(e)
        }
    }

    fun checkHeight(data: String): InputValidationResult {
        try {
            Height(data)
            return InputValidationResult.valid(data)
        } catch (e: IllegalArgumentException) {
            return InputValidationResult.invalid(e)
        }
    }

    fun checkWeight(data: String): InputValidationResult {
        try {
            Weight(data)
            return InputValidationResult.valid(data)
        } catch (e: IllegalArgumentException) {
            return InputValidationResult.invalid(e)
        }
    }
}