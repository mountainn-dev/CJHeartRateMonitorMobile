package com.san.heartratemonitormobile.domain.enums

enum class Gender(val code: Int, val genderName: String) {
    MALE(0, "남"),
    FEMALE(1, "여"),
    OTHERS(2, "기타")
}