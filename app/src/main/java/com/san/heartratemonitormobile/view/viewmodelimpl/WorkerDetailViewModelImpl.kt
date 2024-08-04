package com.san.heartratemonitormobile.view.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.Error
import com.san.heartratemonitormobile.data.Success
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.utils.Const
import com.san.heartratemonitormobile.view.viewmodel.UserDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class WorkerDetailViewModelImpl(
    private val repository: HeartRateServiceRepository,
    private val userId: String
) : UserDetailViewModel, ViewModel() {
    override val state: LiveData<UiState>
        get() = viewModelState
    private val viewModelState = MediatorLiveData<UiState>()
    private val userState = MutableLiveData<UiState>(UiState.Loading)
    private val heartRateState = MutableLiveData<UiState>(UiState.Loading)
    override lateinit var user: UserModel
    override lateinit var heartRateData: List<Int>

    init {
        merge(viewModelState, userState, heartRateState)
        load()
    }

    private fun load() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                awaitAll(
                    async { loadUser() },
                    async { loadHeartRate() }
                )
            }
        }
    }

    private suspend fun loadUser() {
        val result = repository.getSingleUser(
            Id(userId), LocalDate.parse(
                String.format(Const.DATE_FILTER_DEFAULT_START_DATE, LocalDate.now().year)
            ), LocalDate.now()
        )

        if (result is Success) {
            user = result.data
            userState.postValue(UiState.Success)
        } else {
            if ((result as Error).isTimeOut()) userState.postValue(UiState.Timeout)
            else userState.postValue(UiState.ServiceError)
        }
    }

    private suspend fun loadHeartRate() {
        val result = repository.getHeartRate(Id(userId), LocalDate.now())

        if (result is Success) {
            heartRateData = result.data
            viewModelState.postValue(UiState.Success)
        } else {
            if ((result as Error).isTimeOut()) viewModelState.postValue(UiState.Timeout)
            else viewModelState.postValue(UiState.ServiceError)
        }
    }

    override fun setThreshold(threshold: Int) {}

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