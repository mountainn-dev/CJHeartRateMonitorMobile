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
    fun checkId(data: String): InputValidationResult<Id> {
        return try {
            InputValidationResult.valid(Id(data))
        } catch (e: IllegalArgumentException) {
            InputValidationResult.invalid(e)
        }
    }

    fun checkPassWord(data: String): InputValidationResult<PassWord> {
        return try {
            InputValidationResult.valid(PassWord(data))
        } catch (e: IllegalArgumentException) {
            InputValidationResult.invalid(e)
        }
    }

    fun doubleCheckPassWord(data1: String, data2: String): InputValidationResult<String> {
        return if (data1 == data2) InputValidationResult.valid(data1)
        else return InputValidationResult.invalid(IllegalArgumentException(ExceptionMessage.WRONG_CHECK_PASS_WORD_EXCEPTION))
    }

    fun checkName(data: String): InputValidationResult<Name> {
        return try {
            InputValidationResult.valid(Name(data))
        } catch (e: IllegalArgumentException) {
            InputValidationResult.invalid(e)
        }
    }

    fun checkPhoneNumber(data: String): InputValidationResult<PhoneNumber> {
        return try {
            InputValidationResult.valid(PhoneNumber(data))
        } catch (e: IllegalArgumentException) {
            InputValidationResult.invalid(e)
        }
    }

    fun checkBirth(data: String): InputValidationResult<Birth> {
        return try {
            InputValidationResult.valid(Birth(data))
        } catch (e: IllegalArgumentException) {
            InputValidationResult.invalid(e)
        }
    }

    fun checkHeight(data: String): InputValidationResult<Height> {
        return try {
            InputValidationResult.valid(Height(data))
        } catch (e: IllegalArgumentException) {
            InputValidationResult.invalid(e)
        }
    }

    fun checkWeight(data: String): InputValidationResult<Weight> {
        return try {
            InputValidationResult.valid(Weight(data))
        } catch (e: IllegalArgumentException) {
            InputValidationResult.invalid(e)
        }
    }
}