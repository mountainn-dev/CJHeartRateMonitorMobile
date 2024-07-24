package com.san.heartratemonitormobile.data.entity

import com.san.heartratemonitormobile.data.exception.ExceptionMessage
import com.san.heartratemonitormobile.data.vo.Birth
import com.san.heartratemonitormobile.data.vo.Height
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.data.vo.Name
import com.san.heartratemonitormobile.data.vo.PhoneNumber
import com.san.heartratemonitormobile.data.vo.Weight
import com.san.heartratemonitormobile.domain.enums.Action.*
import com.san.heartratemonitormobile.domain.enums.Gender.*
import com.san.heartratemonitormobile.domain.model.ReportModel
import java.time.LocalDate
import java.time.LocalTime

data class ReportEntity(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val gender: Int,
    val birth: String,
    val height: String,
    val weight: String,
    val threshold: Int,
    val reportCountTotal: Int,
    val reportCountToday: Int,
    val reportHeartRate: Int,
    val reportDateTime: String,
    val action: Int?,
    val locationLatitude: Float,
    val locationLongitude: Float
) {
    fun toReportModel() = ReportModel(
        Id(id),
        Name(name),
        PhoneNumber(phoneNumber),
        gender(gender),
        Birth(birth),
        Height(height),
        Weight(weight),
        threshold,
        reportCountTotal,
        reportCountToday,
        reportHeartRate,
        reportDate(reportDateTime),
        reportTime(reportDateTime),
        action(action),
        locationLatitude,
        locationLongitude
    )

    private fun gender(gender: Int) = when (gender) {
        MALE.code -> MALE
        FEMALE.code -> FEMALE
        else -> throw NoSuchElementException(ExceptionMessage.WRONG_GENDER_VALUE_EXCEPTION)
    }

    private fun reportDate(reportDateTime: String): LocalDate {
        try {
            return LocalDate.parse(reportDateTime.split(DIVIDER_REPORT_DATE_TIME)[0])
        } catch (e: Exception) {
            throw NoSuchElementException(ExceptionMessage.WRONG_REPORT_DATE_TIME_FORMAT_EXCEPTION)
        }
    }

    private fun reportTime(reportDateTime: String): LocalTime {
        try {
            return LocalTime.parse(reportDateTime.split(DIVIDER_REPORT_DATE_TIME)[1])
        } catch (e: Exception) {
            throw NoSuchElementException(ExceptionMessage.WRONG_REPORT_DATE_TIME_FORMAT_EXCEPTION)
        }
    }

    private fun action(action: Int?) = when (action) {
        null -> NONE
        EMERGENCY.code -> EMERGENCY
        REST.code -> REST
        WORK.code -> WORK
        else -> throw NoSuchElementException(ExceptionMessage.WRONG_ACTION_VALUE_EXCEPTION)
    }

    companion object {
        private const val DIVIDER_REPORT_DATE_TIME = " "
    }
}
