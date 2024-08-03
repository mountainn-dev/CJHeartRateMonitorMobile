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
import com.san.heartratemonitormobile.BuildConfig
import com.san.heartratemonitormobile.R
import com.san.heartratemonitormobile.data.remote.retrofit.HeartRateService
import com.san.heartratemonitormobile.data.repositoryimpl.HeartRateServiceRepositoryImpl
import com.san.heartratemonitormobile.databinding.ActivityUserDetailBinding
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.utils.Const
import com.san.heartratemonitormobile.domain.utils.Utils
import com.san.heartratemonitormobile.view.viewmodel.UserDetailViewModel
import com.san.heartratemonitormobile.view.viewmodelfactory.UserDetailViewModelFactory
import com.san.heartratemonitormobile.view.viewmodelimpl.UserDetailViewModelImpl
import java.time.LocalDate
import java.time.LocalTime

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userModel = intent.getSerializableExtra(Const.TAG_USER) as UserModel
        val userId = intent.getStringExtra(Const.TAG_ID) ?: ""
        val preference = this.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
        val repo = HeartRateServiceRepositoryImpl(
            Utils.getRetrofit(preference.getString(Const.TAG_ID_TOKEN, "")!!).create(
                HeartRateService::class.java))
        viewModel = ViewModelProvider(this, UserDetailViewModelFactory(repo, userModel, userId)).get(
            UserDetailViewModelImpl::class.java
        )

        initObserver(this)
        initListener()
        initHeartRateGraph(this)
    }

    private fun initObserver(activity: Activity) {
        viewModel.state.observe(
            activity as LifecycleOwner,
            stateObserver(activity)
        )
    }

    private fun stateObserver(activity: Activity) = Observer<UiState> {
        when (it) {
            UiState.Success -> {
                loadSummary()
                loadGraph(activity)
                toggleView(binding.svUserDetail)
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
        val user = viewModel.user

        loadUserSummary(user)
    }

    private fun loadUserSummary(user: UserModel) {
        binding.txtName.text = user.name.get()
        binding.txtThreshold.text = user.threshold.toString()
        binding.txtGender.text = user.gender.genderName
        binding.txtAge.text = String.format(
            AGE_UNIT,
            LocalDate.now().year - user.birth.get().year + 1)
        binding.txtHeight.text = "${user.height.get()}${HEIGHT_UNIT}"
        binding.txtWeight.text = "${user.weight.get()}${WEIGHT_UNIT}"
        binding.txtId.text = user.id.get()
        binding.txtPhoneNumber.text = String.format(
            PHONE_NUMBER_FORMAT,
            user.phoneNumber.first(), user.phoneNumber.mid(), user.phoneNumber.last()
        )
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
        binding.txtAvgHeartRate.text = String.format(HEART_RATE_MESSAGE, viewModel.heartRateData.average().toInt())
        binding.txtMaxHeartRate.text = String.format(HEART_RATE_MESSAGE, viewModel.heartRateData.max())
    }

    private fun initListener() {
        setBtnBackListener()
    }

    private fun setBtnBackListener() {
        binding.btnBack.setOnClickListener {
            finish()
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
        binding.svUserDetail.visibility = if (view == binding.svUserDetail) View.VISIBLE else View.GONE
        binding.pgbSearchRoute.visibility = if (view == binding.pgbSearchRoute) View.VISIBLE else View.GONE
        binding.llTimeout.visibility = if (view == binding.llTimeout) View.VISIBLE else View.GONE
        binding.llServiceError.visibility = if (view == binding.llServiceError) View.VISIBLE else View.GONE
    }

    companion object {
        private const val AGE_UNIT = "%d세"
        private const val HEIGHT_UNIT = "cm"
        private const val WEIGHT_UNIT = "kg"
        private const val PHONE_NUMBER_FORMAT = "%s-%s-%s"
        private const val HEART_RATE_MESSAGE = "%d bpm"
        private const val HEART_RATE_GRAPH_LEGEND = "심박수 구간 안내"

        private const val MAX_AXIS_LEFT = 160f
        private const val MIN_AXIS_LEFT = 0f
        private const val MAX_AXIS_BOTTOM = 1440f
        private const val MIN_AXIS_BOTTOM = 0f
        private const val LABEL_COUNT_AXIS_LEFT = 8
        private const val LABEL_COUNT_AXIS_BOTTOM = 4
        private const val MINUTE_OF_HOUR = 60
    }
}