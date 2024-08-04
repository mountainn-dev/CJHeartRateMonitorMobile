package com.san.heartratemonitormobile.view.screen

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.san.heartratemonitormobile.BuildConfig
import com.san.heartratemonitormobile.data.remote.retrofit.HeartRateService
import com.san.heartratemonitormobile.data.repositoryimpl.HeartRateServiceRepositoryImpl
import com.san.heartratemonitormobile.databinding.FragmentReportBinding
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.utils.Const
import com.san.heartratemonitormobile.domain.utils.Utils
import com.san.heartratemonitormobile.view.viewmodel.ReportViewModel
import com.san.heartratemonitormobile.view.viewmodelfactory.ReportViewModelFactory
import com.san.heartratemonitormobile.view.viewmodelimpl.ReportViewModelImpl
import com.san.heartratemonitormobile.view.adapter.ReportAdapter
import com.san.heartratemonitormobile.view.listener.ItemClickEventListener
import java.time.LocalDate

class ReportFragment(private val account: AccountModel, private val id: String) : Fragment() {
    private lateinit var binding: FragmentReportBinding
    private lateinit var viewModel: ReportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preference = requireActivity().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
        val repo = HeartRateServiceRepositoryImpl(Utils.getRetrofit(preference.getString(Const.TAG_ID_TOKEN, "")!!).create(HeartRateService::class.java))
        viewModel = ViewModelProvider(requireActivity(), ReportViewModelFactory(repo, account, id)).get(
            ReportViewModelImpl::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReportBinding.inflate(layoutInflater)

        initAdminOrNot()
        initObserver(requireActivity())
        initListener()

        return binding.root
    }

    private fun initAdminOrNot() {
        binding.llFilterId.visibility = if (account.admin) View.VISIBLE else View.GONE
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
        binding.rvReport.adapter = ReportAdapter(
            viewModel.reports,
            reportItemClickEventListener(viewModel.reports, activity)
        )
        binding.rvReport.layoutManager = LinearLayoutManager(activity)
    }

    private fun reportItemClickEventListener(
        items: List<ReportModel>,
        activity: Activity
    ) = object : ItemClickEventListener {
        override fun onItemClickListener(position: Int) {
            val intent = Intent(activity, ReportDetailActivity::class.java)
            intent.putExtra(Const.TAG_REPORT, items[position])
            intent.putExtra(Const.TAG_ID, id)
            intent.putExtra(Const.TAG_ADMIN, account.admin)

            activity.startActivity(intent)
        }
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
        binding.btnRefresh.setOnClickListener { load() }
        binding.btnTimeoutRequest.setOnClickListener { load() }
        binding.btnServiceErrorRequest.setOnClickListener { load() }
    }

    private fun setBtnFilterDateListener() {
        binding.btnStartDatePick.setOnClickListener {
            val date = LocalDate.parse(binding.btnStartDatePick.text)
            val dialog = DatePickerDialog(requireActivity(), startDateSetListener(), date.year, date.monthValue-1, date.dayOfMonth)
            dialog.show()
        }
        binding.btnEndDatePick.setOnClickListener {
            val date = LocalDate.parse(binding.btnEndDatePick.text)
            val dialog = DatePickerDialog(requireActivity(), endDateSetListener(), date.year, date.monthValue-1, date.dayOfMonth)
            dialog.show()
        }
    }

    private fun startDateSetListener() =
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.setStartDate(LocalDate.of(year, month+1, day))
            load()
        }

    private fun endDateSetListener() =
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.setEndDate(LocalDate.of(year, month+1, day))
            load()
        }

    private fun setBtnFilterIdListener() {
        binding.edtId.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.setIdFilter(binding.edtId.text.toString())
                load()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
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
        load()
    }

    private fun load() {
        viewModel.load()
    }
}