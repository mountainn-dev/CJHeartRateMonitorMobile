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
import com.san.heartratemonitormobile.domain.state.UiState
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
        viewModel.state.observe(
            activity as LifecycleOwner,
            stateObserver(activity)
        )
    }

    private fun stateObserver(
        activity: Activity
    ) = Observer<UiState> {
        when (it) {
            UiState.Success -> {
                loadReports(activity)
                loadWorkingUsers(activity)
                toggleView(binding.llUrgent)
            }
            UiState.Loading -> {
                toggleView(binding.pgbSearchRoute)
            }
            UiState.Timeout -> {
                toggleView(binding.llTimeout)
            }
            UiState.ServiceError -> {
                toggleView(binding.llServiceError)
            }
        }
    }

    private fun loadReports(activity: Activity) {
        binding.rvReport.adapter = ReportAdapter(viewModel.reports)
        binding.rvReport.layoutManager = LinearLayoutManager(activity)
        binding.txtReportCount.text = String.format(REPORT_COUNT_MESSAGE, viewModel.reports.size)
    }

    private fun loadWorkingUsers(activity: Activity) {
        binding.rvWorking.adapter = UserAdapter(viewModel.workingUsers, activity)
        binding.rvWorking.layoutManager = LinearLayoutManager(activity)
        binding.txtWorkingCount.text = viewModel.workingUsers.size.toString()
    }

    private fun initListener() {
        setBtnRefreshListener()
        setBtnRequestListener()
    }

    private fun setBtnRefreshListener() {
        binding.btnRefresh.setOnClickListener {
            viewModel.load()
        }
    }

    private fun setBtnRequestListener() {
        binding.btnTimeoutRequest.setOnClickListener {
            viewModel.load()
        }
        binding.btnServiceErrorRequest.setOnClickListener {
            viewModel.load()
        }
    }

    private fun toggleView(view: View) {
        binding.llUrgent.visibility = if (view == binding.llUrgent) View.VISIBLE else View.GONE
        binding.pgbSearchRoute.visibility = if (view == binding.pgbSearchRoute) View.VISIBLE else View.GONE
        binding.llTimeout.visibility = if (view == binding.llTimeout) View.VISIBLE else View.GONE
        binding.llServiceError.visibility = if (view == binding.llServiceError) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    companion object {
        private const val REPORT_COUNT_MESSAGE = "%d건"
    }
}