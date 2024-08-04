package com.san.heartratemonitormobile.data.entity

import com.san.heartratemonitormobile.domain.model.HeartRateModel
import java.time.LocalTime

data class HeartRateEntity(
    val dateTime: String,
    val heartRate: Int
) {
    fun toHeartRateModel() = HeartRateModel(
        parseTime(dateTime),
        heartRate
    )

    private fun parseTime(dateTime: String) = LocalTime.parse(dateTime.split(DIVIDER).get(1))

    companion object {
        private const val DIVIDER = " "
    }
}
