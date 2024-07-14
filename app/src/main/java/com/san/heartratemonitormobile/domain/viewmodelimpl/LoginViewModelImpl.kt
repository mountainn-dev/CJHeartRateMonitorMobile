package com.san.heartratemonitormobile.domain.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.repository.LoginRepository
import com.san.heartratemonitormobile.data.repository.ServiceRepository
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModelImpl(
    private val repository: LoginRepository
) : LoginViewModel, ViewModel() {

    override val account: LiveData<AccountModel>
        get() = userAccount
    private val userAccount = MutableLiveData<AccountModel>()

    override fun loginForWorker() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loginAsWorker()
            }
        }
    }

    override fun loginForAdmin() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loginAsAdmin()
            }
        }
    }

    private suspend fun loginAsWorker() {
        val result = repository.loginForWorker()

        userAccount.postValue(result)
    }

    private suspend fun loginAsAdmin() {
        val result = repository.loginForAdmin()

        userAccount.postValue(result)
    }
}