package com.san.heartratemonitormobile.domain.viewmodel

import androidx.lifecycle.LiveData
import com.san.heartratemonitormobile.domain.model.AccountModel

interface LoginViewModel {
    val account: LiveData<AccountModel>

    fun loginForWorker()
    fun loginForAdmin()
}