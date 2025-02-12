package com.san.heartratemonitormobile.view.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.Success
import com.san.heartratemonitormobile.data.repository.LoginRepository
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.view.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModelImpl(
    private val repository: LoginRepository
) : LoginViewModel, ViewModel() {

    override val account: LiveData<AccountModel>
        get() = userAccount
    private val userAccount = MutableLiveData<AccountModel>()
    override val loginFail: LiveData<Boolean>
        get() = loginError
    private val loginError = MutableLiveData<Boolean>()
    private var loginProcess = false

    override fun login(id: String, pw: String) {
        if (!loginProcess) {
            loginProcess = true
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    serviceLogin(id, pw)
                    loginProcess = false
                }
            }
        }
    }

    private suspend fun serviceLogin(id: String, pw: String) {
        val result = repository.login(id, pw)

        if (result is Success) userAccount.postValue(result.data)
        else loginError.postValue(true)
    }
}