package com.san.heartratemonitormobile.domain.model

import com.san.heartratemonitormobile.data.vo.Birth
import com.san.heartratemonitormobile.data.vo.Height
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.data.vo.Name
import com.san.heartratemonitormobile.data.vo.PhoneNumber
import com.san.heartratemonitormobile.data.vo.Weight
import com.san.heartratemonitormobile.domain.enums.Gender

data class UserModel(
    val id: Id,
    val name: Name,
    val phoneNumber: PhoneNumber,
    val gender: Gender,
    val birth: Birth,
    val height: Height,
    val weight: Weight,
    val reportCountTotal: Int,
    val reportCountToday: Int,
    val threshold: Int,
    val lastHeartRate: Int
)
