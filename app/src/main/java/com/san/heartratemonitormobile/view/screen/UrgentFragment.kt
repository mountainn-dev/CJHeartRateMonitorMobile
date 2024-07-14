package com.san.heartratemonitormobile.view.screen

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.san.heartratemonitormobile.data.repositoryimpl.ServiceRepositoryImpl
import com.san.heartratemonitormobile.databinding.FragmentUrgentBinding
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.utils.Const
import com.san.heartratemonitormobile.domain.viewmodel.UrgentViewModel
import com.san.heartratemonitormobile.domain.viewmodelfactory.UrgentViewModelFactory
import com.san.heartratemonitormobile.domain.viewmodelimpl.UrgentViewModelImpl
import com.san.heartratemonitormobile.view.adapter.ReportAdapter

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

        initObserver(requireActivity())

        return binding.root
    }

    private fun initObserver(activity: Activity) {
        viewModel.reportsReady.observe(
            activity as LifecycleOwner,
            reportsReadyObserver()
        )
    }

    private fun reportsReadyObserver() = Observer<Boolean> {
        if (it) {
            whenReportsReady()
        } else {
            whenReportNotReady()
        }
    }

    private fun whenReportsReady() {
        binding.rvReport.adapter = ReportAdapter(viewModel.reports)
        binding.rvReport.layoutManager = LinearLayoutManager(activity)
        binding.rvReport.visibility = View.VISIBLE
        binding.txtReportCount.text = String.format(REPORT_COUNT_MESSAGE, viewModel.reports.size)
    }

    private fun whenReportNotReady() {
        binding.rvReport.visibility = View.GONE
        binding.txtReportCount.text = String.format(REPORT_COUNT_MESSAGE, Const.ZERO)
    }

    companion object {
        private const val REPORT_COUNT_MESSAGE = "%d건"
    }
}