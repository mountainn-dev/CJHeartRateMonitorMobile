package com.san.heartratemonitormobile.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.data.repositoryimpl.ServiceRepositoryImpl
import com.san.heartratemonitormobile.databinding.FragmentWorkingBinding
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.viewmodel.WorkingViewModel
import com.san.heartratemonitormobile.domain.viewmodelfactory.WorkingViewModelFactory
import com.san.heartratemonitormobile.domain.viewmodelimpl.WorkingViewModelImpl

class WorkingFragment(private val account: AccountModel) : Fragment() {
    private lateinit var binding: FragmentWorkingBinding
    private lateinit var viewModel: WorkingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = ServiceRepositoryImpl()
        viewModel = ViewModelProvider(requireActivity(), WorkingViewModelFactory(repo)).get(WorkingViewModelImpl::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWorkingBinding.inflate(layoutInflater)

        return binding.root
    }
}