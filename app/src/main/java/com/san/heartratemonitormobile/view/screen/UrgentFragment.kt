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
import com.san.heartratemonitormobile.view.adapter.UserAdapter

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
        initListener()

        return binding.root
    }

    private fun initObserver(activity: Activity) {
        viewModel.reportsReady.observe(
            activity as LifecycleOwner,
            reportsReadyObserver(activity)
        )
        viewModel.workingUsersReady.observe(
            activity as LifecycleOwner,
            workingUsersReadyObserver(activity)
        )
    }

    private fun reportsReadyObserver(
        activity: Activity
    ) = Observer<Boolean> {
        if (it) {
            whenReportsReady(activity)
        } else {
            whenReportNotReady()
        }
    }

    private fun whenReportsReady(activity: Activity) {
        binding.rvReport.adapter = ReportAdapter(viewModel.reports)
        binding.rvReport.layoutManager = LinearLayoutManager(activity)
        binding.rvReport.visibility = View.VISIBLE
        binding.txtReportCount.text = String.format(REPORT_COUNT_MESSAGE, viewModel.reports.size)
    }

    private fun whenReportNotReady() {
        binding.rvReport.visibility = View.GONE
        binding.txtReportCount.text = String.format(REPORT_COUNT_MESSAGE, Const.ZERO)
    }

    private fun workingUsersReadyObserver(
        activity: Activity
    ) = Observer<Boolean> {
        if (it) {
            whenWorkingUsersReady(activity)
        } else {
            whenWorkingUsersNotReady()
        }
    }

    private fun whenWorkingUsersReady(activity: Activity) {
        binding.rvWorking.adapter = UserAdapter(viewModel.workingUsers, activity)
        binding.rvWorking.layoutManager = LinearLayoutManager(activity)
        binding.rvWorking.visibility = View.VISIBLE
        binding.txtWorkingCount.text = viewModel.workingUsers.size.toString()
    }

    private fun whenWorkingUsersNotReady() {
        binding.rvWorking.visibility = View.GONE
        binding.txtWorkingCount.text = Const.ZERO.toString()
    }
    
    private fun initListener() {
        setBtnRefreshListener()
    }

    private fun setBtnRefreshListener() {
        binding.btnRefresh.setOnClickListener {
            viewModel.load()
        }
    }

    companion object {
        private const val REPORT_COUNT_MESSAGE = "%d건"
    }
}