package com.san.heartratemonitormobile.data.entity

data class ServiceResponse<T>(
    val message: String,
    val data: T
)
