package com.san.heartratemonitormobile.domain.viewmodelimpl

import androidx.lifecycle.ViewModel
import com.san.heartratemonitormobile.data.repository.ServiceRepository
import com.san.heartratemonitormobile.domain.viewmodel.WorkingViewModel

class WorkingViewModelImpl(
    private val repository: ServiceRepository
) : WorkingViewModel, ViewModel() {
}