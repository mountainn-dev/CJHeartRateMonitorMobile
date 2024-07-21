package com.san.heartratemonitormobile.view.screen

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
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
import java.time.LocalTime

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
        initHeartRateGraph()
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
        binding.txtHeight.text = "${report.height.get()}$HEIGHT_UNIT"
        binding.txtWeight.text = "${report.weight.get()}$WEIGHT_UNIT"
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

    private fun initHeartRateGraph() {
        binding.chartDayHeartRate.setBackgroundColor(Color.WHITE)
        binding.chartDayHeartRate.description.isEnabled = false
        binding.chartDayHeartRate.setDrawGridBackground(false)
        binding.chartDayHeartRate.setTouchEnabled(false)
        binding.chartDayHeartRate.axisRight.isEnabled = false
        binding.chartDayHeartRate.axisLeft.isEnabled = true
        binding.chartDayHeartRate.xAxis.isEnabled = true
        binding.chartDayHeartRate.setExtraOffsets(12f, 12f, 12f, 16f)

        binding.chartDayHeartRate.legend.textColor = ContextCompat.getColor(this, R.color.text_sub)

        binding.chartDayHeartRate.xAxis.setDrawGridLines(false)
        binding.chartDayHeartRate.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chartDayHeartRate.xAxis.valueFormatter = xAxisValueFormatter()
        binding.chartDayHeartRate.xAxis.axisMinimum = 0f
        binding.chartDayHeartRate.xAxis.axisMaximum = 1440f
        binding.chartDayHeartRate.xAxis.setLabelCount(4)
        binding.chartDayHeartRate.xAxis.setAvoidFirstLastClipping(true)
        binding.chartDayHeartRate.xAxis.textColor = ContextCompat.getColor(this, R.color.text_sub)

        binding.chartDayHeartRate.axisLeft.setDrawGridLines(false)
        binding.chartDayHeartRate.axisLeft.axisMaximum = 150f
        binding.chartDayHeartRate.axisLeft.axisMinimum = 0f
        binding.chartDayHeartRate.axisLeft.setLabelCount(8)
        binding.chartDayHeartRate.axisLeft.textColor = ContextCompat.getColor(this, R.color.text_sub)

        // TODO: data 처리를 uistate observer 에서 처리
        val values = arrayListOf(Entry(1f, 100f), Entry(320f, 120f), Entry(840f, 80f), Entry(1000f, 100f), Entry(1100f, 100f))
        val set = LineDataSet(values, HEART_RATE_GRAPH_LEGEND)
        set.color = ContextCompat.getColor(this, R.color.orange)
        set.setDrawCircles(false)
        set.valueTextSize = 0f
        val dataset = arrayListOf<ILineDataSet>(set)
        val data = LineData(dataset)
        binding.chartDayHeartRate.data = data

        binding.txtAvgHeartRate.text = String.format(HEART_RATE_MESSAGE, 100)
        binding.txtMaxHeartRate.text = String.format(HEART_RATE_MESSAGE, 120)
    }

    private fun xAxisValueFormatter() = object : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val hour = (value / 60).toInt()
            val minute = (value % 60).toInt()
            val a = LocalTime.of(hour, minute, 0)
            return a.toString()
        }
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
        private const val HEIGHT_UNIT = "cm"
        private const val WEIGHT_UNIT = "kg"
        private const val TODAY_REPORT_COUNT_MESSAGE = "%d건"
        private const val HEART_RATE_MESSAGE = "%d bpm"
        private const val HEART_RATE_GRAPH_LEGEND = "심박수 구간 안내"
    }
}