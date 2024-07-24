package com.san.heartratemonitormobile.data.entity

import com.google.gson.annotations.SerializedName

data class SignUpEntity(
    @SerializedName("userId") val id: String,
    val password: String,
    val name: String,
    @SerializedName("phoneNum") val phoneNumber: String,
    val gender: Int,
    val birth: String,
    val height: Int,
    val weight: Int,
//    val serviceTerm: Int,
//    val privacyTerm: Int
)
