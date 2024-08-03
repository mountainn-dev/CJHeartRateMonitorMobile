package com.san.heartratemonitormobile.view.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.Success
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.view.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class UserViewModelImpl(
    private val repository: HeartRateServiceRepository,
) : UserViewModel, ViewModel() {
    override val state: LiveData<UiState>
        get() = viewModelState
    private val viewModelState = MutableLiveData<UiState>(UiState.Loading)

    override lateinit var users: List<UserModel>
    private lateinit var temp: List<UserModel>
    override val startDate: LiveData<LocalDate>
        get() = workStartDate
    private val workStartDate = MutableLiveData(LocalDate.now())
    override val endDate: LiveData<LocalDate>
        get() = workEndDate
    private val workEndDate = MutableLiveData(LocalDate.now())

    override fun load() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadUserContent()
            }
        }
    }

    private suspend fun loadUserContent() {
        val result = repository.getAllUsers(workStartDate.value!!, workEndDate.value!!)

        if (result is Success) {
            users = result.data
            temp = result.data
            viewModelState.postValue(UiState.Success)
        } else {
            viewModelState.postValue(UiState.ServiceError)
        }
    }

    override fun setStartDateAndLoad(date: LocalDate) {
        workStartDate.value = date
        load()
    }

    override fun setEndDateAndLoad(date: LocalDate) {
        workEndDate.value = date
        load()
    }

    override fun filterById(id: String) {
        users = temp.filter { it.id.get().contains(id) }

        viewModelState.postValue(UiState.Success)
    }
}