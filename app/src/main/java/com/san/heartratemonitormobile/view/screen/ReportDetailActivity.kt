package com.san.heartratemonitormobile.view.screen

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.R
import com.san.heartratemonitormobile.data.repositoryimpl.ServiceRepositoryImpl
import com.san.heartratemonitormobile.databinding.ActivityReportDetailBinding
import com.san.heartratemonitormobile.domain.enums.Action
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.utils.Const
import com.san.heartratemonitormobile.domain.viewmodel.ReportDetailViewModel
import com.san.heartratemonitormobile.domain.viewmodelfactory.ReportDetailViewModelFactory
import com.san.heartratemonitormobile.domain.viewmodelimpl.ReportDetailViewModelImpl
import java.time.LocalDate

class ReportDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportDetailBinding
    private lateinit var viewModel: ReportDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reportModel = intent.getSerializableExtra(Const.TAG_REPORT) as ReportModel
        val repo = ServiceRepositoryImpl()
        viewModel = ViewModelProvider(this, ReportDetailViewModelFactory(repo, reportModel)).get(
            ReportDetailViewModelImpl::class.java
        )

        initObserver(this)
        initListener()
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
                loadSummary()
                toggleView(binding.svReportDetail)
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

    private fun loadSummary() {
        val report = viewModel.report

        loadReportSummary(report)
        loadReportAction(report)
    }

    private fun loadReportSummary(report: ReportModel) {
        binding.txtReportDate.text = report.reportDate.toString()
        binding.txtReportTime.text = report.reportTime.toString()
        binding.txtThreshold.text = report.threshold.toString()
        binding.txtThresholdOver.text = String.format(
            THRESHOLD_OVER_MESSAGE,
            report.reportHeartRate - report.threshold)
        binding.txtName.text = report.name.get()
        binding.txtGender.text = report.gender.genderName
        binding.txtAge.text = String.format(
            AGE_MESSAGE,
            LocalDate.now().year - report.birth.get().year + 1)
        binding.txtHeight.text = report.height.get().toString()
        binding.txtWeight.text = report.weight.get().toString()
        binding.txtId.text = report.id.get()
        binding.txtTodayReportCount.text = String.format(
            TODAY_REPORT_COUNT_MESSAGE, report.reportCountToday)
    }

    private fun loadReportAction(report: ReportModel) {
        toggleActionImage(report.action)
    }

    private fun initListener() {
        setBtnBackListener()
        setBtnActionListener()
    }

    private fun setBtnBackListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setBtnActionListener() {
        binding.btnEmergency.setOnClickListener { viewModel.setAction(Action.EMERGENCY) }
        binding.btnRest.setOnClickListener { viewModel.setAction(Action.REST) }
        binding.btnWork.setOnClickListener { viewModel.setAction(Action.WORK) }
    }

    private fun toggleView(view: View) {
        binding.svReportDetail.visibility = if (view == binding.svReportDetail) View.VISIBLE else View.GONE
        binding.pgbSearchRoute.visibility = if (view == binding.pgbSearchRoute) View.VISIBLE else View.GONE
        binding.llTimeout.visibility = if (view == binding.llTimeout) View.VISIBLE else View.GONE
        binding.llServiceError.visibility = if (view == binding.llServiceError) View.VISIBLE else View.GONE
    }

    private fun toggleActionImage(action: Action) {
        when (action.code) {
            Action.NONE.code -> {
                binding.icEmergency.setImageResource(R.drawable.ic_unchecked_emergency)
                binding.icRest.setImageResource(R.drawable.ic_unchecked_rest)
                binding.icWork.setImageResource(R.drawable.ic_unchecked_work)
            }
            Action.EMERGENCY.code -> {
                binding.icEmergency.setImageResource(R.drawable.ic_checked_emergency)
                binding.icRest.setImageResource(R.drawable.ic_unchecked_rest)
                binding.icWork.setImageResource(R.drawable.ic_unchecked_work)
            }
            Action.REST.code -> {
                binding.icEmergency.setImageResource(R.drawable.ic_unchecked_emergency)
                binding.icRest.setImageResource(R.drawable.ic_checked_rest)
                binding.icWork.setImageResource(R.drawable.ic_unchecked_work)
            }
            Action.WORK.code -> {
                binding.icEmergency.setImageResource(R.drawable.ic_unchecked_emergency)
                binding.icRest.setImageResource(R.drawable.ic_unchecked_rest)
                binding.icWork.setImageResource(R.drawable.ic_checked_work)
            }
        }
    }

    companion object {
        private const val THRESHOLD_OVER_MESSAGE = "+%d"
        private const val AGE_MESSAGE = "%d세"
        private const val TODAY_REPORT_COUNT_MESSAGE = "%d건"
    }
}