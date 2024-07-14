package com.san.heartratemonitormobile.data.entity

import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import com.san.heartratemonitormobile.data.vo.Birth
import com.san.heartratemonitormobile.data.vo.Height
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.data.vo.Name
import com.san.heartratemonitormobile.data.vo.PhoneNumber
import com.san.heartratemonitormobile.data.vo.Weight
import com.san.heartratemonitormobile.domain.enums.Gender
import com.san.heartratemonitormobile.domain.model.UserModel

data class UserEntity(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val gender: Int,
    val birth: String,
    val height: String,
    val weight: String,
    val reportCountTotal: Int,
    val reportCountToday: Int,
    val threshold: Int,
    val lastHeartRate: Int
) {
    fun toUserModel() = UserModel(
        Id(id),
        Name(name),
        PhoneNumber(phoneNumber),
        gender(gender),
        Birth(birth),
        Height(height),
        Weight(weight),
        reportCountTotal,
        reportCountToday,
        threshold,
        lastHeartRate
    )

    private fun gender(gender: Int) = when (gender) {
        Gender.MALE.code -> Gender.MALE
        Gender.FEMALE.code -> Gender.FEMALE
        else -> throw NoSuchElementException(ExceptionMessage.WRONG_GENDER_VALUE_EXCEPTION)
    }
}
