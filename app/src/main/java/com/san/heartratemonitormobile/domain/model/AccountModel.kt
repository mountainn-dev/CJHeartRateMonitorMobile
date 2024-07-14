package com.san.heartratemonitormobile.domain.model

import java.io.Serializable

data class AccountModel(
    val idToken: String,
    val admin: Boolean
) : Serializable
