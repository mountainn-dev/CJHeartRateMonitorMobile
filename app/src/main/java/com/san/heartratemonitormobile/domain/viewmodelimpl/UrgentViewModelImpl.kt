package com.san.heartratemonitormobile.domain.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.repository.ServiceRepository
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.viewmodel.UrgentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UrgentViewModelImpl(
    private val repository: ServiceRepository
) : UrgentViewModel, ViewModel() {
    override val state: LiveData<UiState>
        get() = viewModelState
    private val viewModelState = MediatorLiveData<UiState>()
    private val reportState = MutableLiveData<UiState>(UiState.Loading)
    private val workingUserState = MutableLiveData<UiState>(UiState.Loading)

    override lateinit var reports: List<ReportModel>
    override lateinit var workingUsers: List<UserModel>

    init {
        merge(viewModelState, reportState, workingUserState)
    }

    override fun load() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadReportContent()
                loadWorkingUserContent()
            }
        }
    }

    private suspend fun loadReportContent() {
        val result = repository.getReports()

        this.reports = result
        reportState.postValue(UiState.Success)
    }

    private suspend fun loadWorkingUserContent() {
        val result = repository.getUsers()

        this.workingUsers = result
        workingUserState.postValue(UiState.Success)
    }

    private fun merge(
        parent: MediatorLiveData<UiState>,
        child1: MutableLiveData<UiState>,
        child2: MutableLiveData<UiState>
    ) {
        parent.addSource(child1) { parent.value =  state(it, child2.value!!) }
        parent.addSource(child2) { parent.value =  state(it, child1.value!!) }
    }

    private fun state(
        state1: UiState, state2: UiState
    ): UiState {
        return if (isSuccess(state1, state2)) UiState.Success
        else if (isLoading(state1, state2)) UiState.Loading
        else if (isTimeout(state1, state2)) UiState.Timeout
        else UiState.ServiceError
    }

    private fun isSuccess(
        state1: UiState, state2: UiState
    ) = state1 is UiState.Success && state2 is UiState.Success

    private fun isLoading(
        state1: UiState, state2: UiState
    ) = !isTimeout(state1, state2)
            && state1 is UiState.Loading || state2 is UiState.Loading

    private fun isTimeout(
        state1: UiState, state2: UiState
    ) = !isServiceError(state1, state2)
            && state1 is UiState.Timeout || state2 is UiState.Timeout
    private fun isServiceError(
        state1: UiState, state2: UiState
    ) = state1 is UiState.ServiceError || state2 is UiState.ServiceError
}