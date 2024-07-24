package com.san.heartratemonitormobile.domain.viewmodel

import androidx.lifecycle.LiveData
import com.san.heartratemonitormobile.domain.model.AccountModel

interface LoginViewModel {
    val account: LiveData<AccountModel>
    val loginFail: LiveData<Boolean>

    fun login(id: String, pw: String)
    fun loginForWorker()
    fun loginForAdmin()
}