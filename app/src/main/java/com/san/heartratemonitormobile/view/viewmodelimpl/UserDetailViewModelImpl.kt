package com.san.heartratemonitormobile.view.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.Error
import com.san.heartratemonitormobile.data.Success
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.view.viewmodel.UserDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class UserDetailViewModelImpl(
    private val repository: HeartRateServiceRepository,
    private val userModel: UserModel
) : UserDetailViewModel, ViewModel() {
    override val state: LiveData<UiState>
        get() = viewModelState
    private val viewModelState = MutableLiveData<UiState>(UiState.Loading)
    override val user: UserModel = userModel
    override lateinit var heartRateData: List<Int>
    override val dateFilter: LiveData<LocalDate>
        get() = heartRateDate
    private val heartRateDate = MutableLiveData(LocalDate.now())

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadHeartRate()
            }
        }
    }

    private suspend fun loadHeartRate() {
        val result = repository.getHeartRate(userModel.id, heartRateDate.value!!)

        if (result is Success) {
            heartRateData = result.data
            viewModelState.postValue(UiState.Success)
        } else {
            if ((result as Error).isTimeOut()) viewModelState.postValue(UiState.Timeout)
            else viewModelState.postValue(UiState.ServiceError)
        }
    }

    override fun setThreshold(threshold: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                changeThreshold(threshold)
            }
        }
    }

    private suspend fun changeThreshold(threshold: Int) {
        val result = repository.setThreshold(userModel.id, threshold)

        if (result is Error) {
            if (result.isTimeOut()) viewModelState.postValue(UiState.Timeout)
            else viewModelState.postValue(UiState.ServiceError)
        }
    }

    override fun setDateFilter(date: LocalDate) {
        viewModelScope.launch {
            heartRateDate.value = date
            withContext(Dispatchers.IO) {
                loadHeartRate()
            }
        }
    }
}