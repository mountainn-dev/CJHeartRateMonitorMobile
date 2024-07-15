package com.san.heartratemonitormobile.domain.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.repository.ServiceRepository
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class UserViewModelImpl(
    private val repository: ServiceRepository
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
        // TODO: api 개발 이후 근무중 인원이 아닌 전체 회원 호출하는 api 로 수정
        val result = repository.getUsers()

        users = result
        temp = result
        viewModelState.postValue(UiState.Success)
    }

    override fun setStartDateAndLoad(date: LocalDate) {
        workStartDate.postValue(date)
        load()
    }

    override fun setEndDateAndLoad(date: LocalDate) {
        workEndDate.postValue(date)
        load()
    }

    override fun filterById(id: String) {
        users = temp.filter { it.id.get().contains(id) }

        viewModelState.postValue(UiState.Success)
    }
}