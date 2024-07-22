package com.san.heartratemonitormobile.domain.model

import com.san.heartratemonitormobile.data.entity.SignUpEntity
import com.san.heartratemonitormobile.data.vo.Birth
import com.san.heartratemonitormobile.data.vo.Height
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.data.vo.Name
import com.san.heartratemonitormobile.data.vo.PassWord
import com.san.heartratemonitormobile.data.vo.PhoneNumber
import com.san.heartratemonitormobile.data.vo.Weight
import com.san.heartratemonitormobile.domain.enums.Gender

data class SignUpModel(
    val id: Id,
    val password: PassWord,
    val name: Name,
    val phoneNumber: PhoneNumber,
    val gender: Gender,
    val birth: Birth,
    val height: Height,
    val weight: Weight,
//    val serviceTerm: Boolean,
//    val privacyTerm: Boolean
) {
    fun toSignUpEntity() = SignUpEntity(
        id.get(),
        password.get(),
        name.get(),
        phoneNumber.get(),
        gender.code,
        birth.get().toString(),
        height.get(),
        weight.get(),
//        serviceTerm,
//        privacyTerm
    )
}
