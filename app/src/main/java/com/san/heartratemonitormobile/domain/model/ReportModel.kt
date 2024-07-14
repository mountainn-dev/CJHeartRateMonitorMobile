package com.san.heartratemonitormobile.domain.model

import com.san.heartratemonitormobile.data.vo.Birth
import com.san.heartratemonitormobile.data.vo.Height
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.data.vo.Name
import com.san.heartratemonitormobile.data.vo.PhoneNumber
import com.san.heartratemonitormobile.data.vo.Weight
import com.san.heartratemonitormobile.domain.enums.Action
import com.san.heartratemonitormobile.domain.enums.Gender
import java.time.LocalDate
import java.time.LocalTime

data class ReportModel(
    val id: Id,
    val name: Name,
    val phoneNumber: PhoneNumber,
    val gender: Gender,
    val birth: Birth,
    val height: Height,
    val weight: Weight,
    val threshold: Int,
    val reportCountTotal: Int,
    val reportCountToday: Int,
    val reportHeartRate: Int,
    val reportDate: LocalDate,
    val reportTime: LocalTime,
    val action: Action,
    val locationLatitude: Float,
    val locationLongitude: Float
)
