package com.san.heartratemonitormobile.data.entity

data class SignUpEntity(
    val id: String,
    val password: String,
    val name: String,
    val phoneNumber: String,
    val gender: Int,
    val birth: String,
    val height: Int,
    val weight: Int,
//    val serviceTerm: Int,
//    val privacyTerm: Int
)
