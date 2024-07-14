package com.san.heartratemonitormobile.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.data.repositoryimpl.ServiceRepositoryImpl
import com.san.heartratemonitormobile.databinding.FragmentUrgentBinding
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.viewmodel.UrgentViewModel
import com.san.heartratemonitormobile.domain.viewmodelfactory.UrgentViewModelFactory
import com.san.heartratemonitormobile.domain.viewmodelimpl.UrgentViewModelImpl

class UrgentFragment(private val account: AccountModel) : Fragment() {
    private lateinit var binding: FragmentUrgentBinding
    private lateinit var viewModel: UrgentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = ServiceRepositoryImpl()
        viewModel = ViewModelProvider(requireActivity(), UrgentViewModelFactory(repo)).get(UrgentViewModelImpl::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUrgentBinding.inflate(layoutInflater)

        return binding.root
    }
}