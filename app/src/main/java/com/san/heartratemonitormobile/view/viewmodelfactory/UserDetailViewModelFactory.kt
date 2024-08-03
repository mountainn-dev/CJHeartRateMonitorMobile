package com.san.heartratemonitormobile.view.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.data.repository.HeartRateServiceRepository
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.view.viewmodelimpl.UserDetailViewModelImpl

class UserDetailViewModelFactory(
    private val repository: HeartRateServiceRepository,
    private val userModel: UserModel,
    private val id: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailViewModelImpl::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserDetailViewModelImpl(repository, userModel, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}