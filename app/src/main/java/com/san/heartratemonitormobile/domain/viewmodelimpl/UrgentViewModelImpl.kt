package com.san.heartratemonitormobile.domain.viewmodelimpl

import androidx.lifecycle.ViewModel
import com.san.heartratemonitormobile.data.repository.ServiceRepository
import com.san.heartratemonitormobile.domain.viewmodel.UrgentViewModel

class UrgentViewModelImpl(
    private val repository: ServiceRepository
) : UrgentViewModel, ViewModel() {
}