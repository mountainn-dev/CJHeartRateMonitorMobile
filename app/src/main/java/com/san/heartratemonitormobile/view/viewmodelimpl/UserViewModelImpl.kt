package com.san.heartratemonitormobile.view.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.Error
import com.san.heartratemonitormobile.data.Success
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
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
    private lateinit var save: List<UserModel>
    override val startDate: LiveData<LocalDate>
        get() = workStartDate
    private val workStartDate = MutableLiveData(LocalDate.now())
    override val endDate: LiveData<LocalDate>
        get() = workEndDate
    private val workEndDate = MutableLiveData(LocalDate.now())
    private var idFilter = BLANK

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
            save = result.data
            users = save.filter { it.id.get().contains(idFilter) }
            viewModelState.postValue(UiState.Success)
        } else {
            if ((result as Error).isTimeOut()) viewModelState.postValue(UiState.Timeout)
            else viewModelState.postValue(UiState.ServiceError)
        }
    }

    override fun setStartDate(date: LocalDate) {
        workStartDate.value = date
    }

    override fun setEndDate(date: LocalDate) {
        workEndDate.value = date
    }

    override fun setIdFilter(id: String) {
        idFilter = id
    }

    companion object {
        private const val BLANK = ""
    }
}