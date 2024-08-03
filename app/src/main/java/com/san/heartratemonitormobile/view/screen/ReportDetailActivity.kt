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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.san.heartratemonitormobile.BuildConfig
import com.san.heartratemonitormobile.R
import com.san.heartratemonitormobile.data.remote.retrofit.HeartRateService
import com.san.heartratemonitormobile.data.repositoryimpl.HeartRateServiceRepositoryImpl
import com.san.heartratemonitormobile.databinding.ActivityReportDetailBinding
import com.san.heartratemonitormobile.domain.enums.Action
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.utils.Const
import com.san.heartratemonitormobile.domain.utils.Utils
import com.san.heartratemonitormobile.view.viewmodel.ReportDetailViewModel
import com.san.heartratemonitormobile.view.viewmodelfactory.ReportDetailViewModelFactory
import com.san.heartratemonitormobile.view.viewmodelimpl.ReportDetailViewModelImpl
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
        val userId = intent.getStringExtra(Const.TAG_ID) ?: ""
        val preference = this.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
        val repo = HeartRateServiceRepositoryImpl(Utils.getRetrofit(preference.getString(Const.TAG_ID_TOKEN, "")!!).create(HeartRateService::class.java))
        viewModel = ViewModelProvider(this, ReportDetailViewModelFactory(repo, reportModel, userId)).get(
            ReportDetailViewModelImpl::class.java
        )

        initObserver(this)
        initListener()
        initHeartRateGraph(this)
        initReportLocationMap(savedInstanceState)
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
                loadGraph(activity)
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
            AGE_UNIT,
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

    private fun loadGraph(activity: Activity) {
        val values = arrayListOf<Entry>()
        for (i in viewModel.heartRateData.indices) {
            values.add(Entry(i.toFloat(), viewModel.heartRateData[i].toFloat()))
        }
        val set = LineDataSet(values, HEART_RATE_GRAPH_LEGEND)
        set.color = ContextCompat.getColor(activity, R.color.orange)
        set.setDrawCircles(false)
        set.valueTextSize = 0f
        val dataset = arrayListOf<ILineDataSet>(set)
        val data = LineData(dataset)
        binding.chartDayHeartRate.data = data
        val average = if (viewModel.heartRateData.isEmpty()) EMPTY_HEART_RATE else  viewModel.heartRateData.average().toInt()
        val max = if (viewModel.heartRateData.isEmpty()) EMPTY_HEART_RATE else  viewModel.heartRateData.max().toInt()
        binding.txtAvgHeartRate.text = String.format(HEART_RATE_MESSAGE, average)
        binding.txtMaxHeartRate.text = String.format(HEART_RATE_MESSAGE, max)
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
        binding.btnEmergency.setOnClickListener {
            viewModel.setAction(Action.EMERGENCY)
            toggleActionImage(Action.EMERGENCY)
        }
        binding.btnRest.setOnClickListener {
            viewModel.setAction(Action.REST)
            toggleActionImage(Action.REST)
        }
        binding.btnWork.setOnClickListener {
            viewModel.setAction(Action.WORK)
            toggleActionImage(Action.WORK)
        }
    }

    private fun initHeartRateGraph(activity: Activity) {
        setGraphStyle()
        setGraphLegend(activity)
        setAxisBottom(activity)
        setAxisLeft(activity)
    }

    private fun setGraphStyle() {
        binding.chartDayHeartRate.setBackgroundColor(Color.WHITE)
        binding.chartDayHeartRate.description.isEnabled = false
        binding.chartDayHeartRate.setDrawGridBackground(false)
        binding.chartDayHeartRate.setTouchEnabled(false)
        binding.chartDayHeartRate.setExtraOffsets(12f, 12f, 12f, 16f)
        binding.chartDayHeartRate.axisRight.isEnabled = false
    }

    private fun setGraphLegend(activity: Activity) {
        binding.chartDayHeartRate.legend.textColor = ContextCompat.getColor(activity, R.color.text_sub)
    }

    private fun setAxisBottom(activity: Activity) {
        binding.chartDayHeartRate.xAxis.isEnabled = true

        binding.chartDayHeartRate.xAxis.setDrawGridLines(false)
        binding.chartDayHeartRate.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chartDayHeartRate.xAxis.valueFormatter = xAxisValueFormatter()
        binding.chartDayHeartRate.xAxis.axisMinimum = MIN_AXIS_BOTTOM
        binding.chartDayHeartRate.xAxis.axisMaximum = MAX_AXIS_BOTTOM
        binding.chartDayHeartRate.xAxis.setLabelCount(LABEL_COUNT_AXIS_BOTTOM)
        binding.chartDayHeartRate.xAxis.setAvoidFirstLastClipping(true)
        binding.chartDayHeartRate.xAxis.textColor = ContextCompat.getColor(activity, R.color.text_sub)
    }

    private fun xAxisValueFormatter() = object : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val hour = (value / MINUTE_OF_HOUR).toInt()
            val minute = (value % MINUTE_OF_HOUR).toInt()
            val a = LocalTime.of(hour, minute, 0)
            return a.toString()
        }
    }

    private fun setAxisLeft(activity: Activity) {
        binding.chartDayHeartRate.axisLeft.isEnabled = true

        binding.chartDayHeartRate.axisLeft.setDrawGridLines(false)
        binding.chartDayHeartRate.axisLeft.axisMaximum = MAX_AXIS_LEFT
        binding.chartDayHeartRate.axisLeft.axisMinimum = MIN_AXIS_LEFT
        binding.chartDayHeartRate.axisLeft.setLabelCount(LABEL_COUNT_AXIS_LEFT)
        binding.chartDayHeartRate.axisLeft.textColor = ContextCompat.getColor(activity, R.color.text_sub)
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

    private fun initReportLocationMap(savedInstanceState: Bundle?) {
        binding.mapReportLocation.onCreate(savedInstanceState)
        val location = LatLng(viewModel.report.locationLatitude.toDouble(),
            viewModel.report.locationLongitude.toDouble())

        binding.mapReportLocation.getMapAsync {
            it.addMarker(MarkerOptions().position(location).title(REPORT_POSITION))
            it.uiSettings.isZoomControlsEnabled = true
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        }
    }

    override fun onStart() {
        binding.mapReportLocation.onStart()
        super.onStart()
    }

    override fun onResume() {
        binding.mapReportLocation.onResume()
        super.onResume()
    }

    override fun onPause() {
        binding.mapReportLocation.onPause()
        super.onPause()
    }

    override fun onStop() {
        binding.mapReportLocation.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        binding.mapReportLocation.onDestroy()
        super.onDestroy()
    }

    companion object {
        private const val THRESHOLD_OVER_MESSAGE = "+%d"
        private const val AGE_UNIT = "%d세"
        private const val HEIGHT_UNIT = "cm"
        private const val WEIGHT_UNIT = "kg"
        private const val TODAY_REPORT_COUNT_MESSAGE = "%d건"
        private const val HEART_RATE_MESSAGE = "%d bpm"
        private const val HEART_RATE_GRAPH_LEGEND = "심박수 구간 안내"
        private const val REPORT_POSITION = "신고 위치"
        private const val EMPTY_HEART_RATE = 0

        private const val MAX_AXIS_LEFT = 160f
        private const val MIN_AXIS_LEFT = 0f
        private const val MAX_AXIS_BOTTOM = 1440f
        private const val MIN_AXIS_BOTTOM = 0f
        private const val LABEL_COUNT_AXIS_LEFT = 8
        private const val LABEL_COUNT_AXIS_BOTTOM = 4
        private const val MINUTE_OF_HOUR = 60
    }
}