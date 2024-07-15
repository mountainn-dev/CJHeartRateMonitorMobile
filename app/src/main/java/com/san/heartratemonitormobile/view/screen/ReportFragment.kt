package com.san.heartratemonitormobile.view.screen

import android.app.Activity
import android.app.DatePickerDialog
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
import com.san.heartratemonitormobile.databinding.FragmentReportBinding
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.viewmodel.ReportViewModel
import com.san.heartratemonitormobile.domain.viewmodelfactory.ReportViewModelFactory
import com.san.heartratemonitormobile.domain.viewmodelimpl.ReportViewModelImpl
import com.san.heartratemonitormobile.view.adapter.ReportAdapter
import java.time.LocalDate

class ReportFragment : Fragment() {
    private lateinit var binding: FragmentReportBinding
    private lateinit var viewModel: ReportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = ServiceRepositoryImpl()
        viewModel = ViewModelProvider(requireActivity(), ReportViewModelFactory(repo)).get(
            ReportViewModelImpl::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReportBinding.inflate(layoutInflater)

        initObserver(requireActivity())
        initListener()

        return binding.root
    }

    private fun initObserver(activity: Activity) {
        viewModel.state.observe(
            activity as LifecycleOwner,
            stateObserver(activity)
        )
        viewModel.startDate.observe(
            activity as LifecycleOwner,
            startDateObserver()
        )
        viewModel.endDate.observe(
            activity as LifecycleOwner,
            endDateObserver()
        )
    }

    private fun stateObserver(
        activity: Activity
    ) = Observer<UiState> {
        when (it) {
            UiState.Success -> {
                loadReports(activity)
                toggleView(binding.rvReport)
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
    }

    private fun startDateObserver() = Observer<LocalDate> {
        binding.btnStartDatePick.text = it.toString()
    }

    private fun endDateObserver() = Observer<LocalDate> {
        binding.btnEndDatePick.text = it.toString()
    }

    private fun initListener() {
        setBtnRefreshListener()
        setBtnFilterDateListener()
        setBtnFilterIdListener()
    }

    private fun setBtnRefreshListener() {
        binding.btnRefresh.setOnClickListener {
            viewModel.load()
        }
    }

    private fun setBtnFilterDateListener() {
        binding.btnStartDatePick.setOnClickListener {
            val date = LocalDate.parse(binding.btnStartDatePick.text)
            val dialog = DatePickerDialog(requireActivity(), startDateSetListener(), date.year, date.monthValue, date.dayOfMonth)
            dialog.show()
        }
        binding.btnEndDatePick.setOnClickListener {
            val date = LocalDate.parse(binding.btnEndDatePick.text)
            val dialog = DatePickerDialog(requireActivity(), endDateSetListener(), date.year, date.monthValue, date.dayOfMonth)
            dialog.show()
        }
    }

    private fun startDateSetListener() =
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.setStartDate(LocalDate.of(year, month, day))
        }

    private fun endDateSetListener() =
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.setEndDate(LocalDate.of(year, month, day))
        }

    private fun setBtnFilterIdListener() {
        binding.btnFilterId.setOnClickListener {
            viewModel.filterById(binding.edtId.text.toString())
        }
    }

    private fun toggleView(view: View) {
        binding.rvReport.visibility = if (view == binding.rvReport) View.VISIBLE else View.GONE
        binding.pgbSearchRoute.visibility = if (view == binding.pgbSearchRoute) View.VISIBLE else View.GONE
        binding.llTimeout.visibility = if (view == binding.llTimeout) View.VISIBLE else View.GONE
        binding.llServiceError.visibility = if (view == binding.llServiceError) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }
}