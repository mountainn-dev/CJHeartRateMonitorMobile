package com.san.heartratemonitormobile.view.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.Error
import com.san.heartratemonitormobile.data.Success
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.utils.Const
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
    private val workStartDate = MutableLiveData<LocalDate>()
    override val endDate: LiveData<LocalDate>
        get() = workEndDate
    private val workEndDate = MutableLiveData<LocalDate>()
    private var idFilter = BLANK

    override fun load() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadUserContent()
            }
        }
    }

    private suspend fun loadUserContent() {
        val workStartDate = if (workStartDate.isInitialized) workStartDate.value!! else null
        val workEndDate = if (workEndDate.isInitialized) workEndDate.value!! else null
        val result = repository.getAllUsers(workStartDate, workEndDate)

        if (result is Success) {
            save = result.data
            filterUsersById()
            viewModelState.postValue(UiState.Success)
        } else {
            if ((result as Error).isTimeOut()) viewModelState.postValue(UiState.Timeout)
            else viewModelState.postValue(UiState.ServiceError)
        }
    }

    private fun filterUsersById() {
        users = save.filter { it.id.get().contains(idFilter) }.sortedBy { it.name.get() }
    }

    override fun setStartDate(date: LocalDate) {
        workStartDate.value = date
    }

    override fun setEndDate(date: LocalDate) {
        workEndDate.value = date
    }

    /**
     * setIdFilter()
     *
     * id 필터링은 api 통신 없이 로컬에서 진행되기 때문에 load() 대신 곧바로 notify
     */
    override fun setIdFilter(id: String) {
        idFilter = id

        if (viewModelState.value!! == UiState.Success) {
            filterUsersById()

            viewModelState.postValue(UiState.Success)
        }
    }

    companion object {
        private const val BLANK = ""
    }
}