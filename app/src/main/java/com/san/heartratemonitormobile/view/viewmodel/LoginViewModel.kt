package com.san.heartratemonitormobile.view.viewmodel

import androidx.lifecycle.LiveData
import com.san.heartratemonitormobile.domain.model.AccountModel

interface LoginViewModel {
    val account: LiveData<AccountModel>
    val loginFail: LiveData<Boolean>

    fun login(id: String, pw: String)
}