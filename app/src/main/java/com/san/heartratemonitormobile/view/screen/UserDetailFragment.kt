package com.san.heartratemonitormobile.view.screen

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import com.san.heartratemonitormobile.databinding.FragmentUserDetailBinding
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.utils.Const
import com.san.heartratemonitormobile.domain.utils.Utils
import com.san.heartratemonitormobile.view.viewmodel.UserDetailViewModel
import com.san.heartratemonitormobile.view.viewmodel.UserViewModel
import com.san.heartratemonitormobile.view.viewmodelfactory.UserDetailViewModelFactory
import com.san.heartratemonitormobile.view.viewmodelfactory.UserViewModelFactory
import com.san.heartratemonitormobile.view.viewmodelfactory.WorkerDetailViewModelFactory
import com.san.heartratemonitormobile.view.viewmodelimpl.UserDetailViewModelImpl
import com.san.heartratemonitormobile.view.viewmodelimpl.UserViewModelImpl
import com.san.heartratemonitormobile.view.viewmodelimpl.WorkerDetailViewModelImpl
import java.time.LocalDate
import java.time.LocalTime

/**
 * UserDetailFragment
 *
 * UserDetailActivity 와 동일한 화면
 * 관리자의 경우 회원 상세 페이지를 Activity 로 제공하고, 근로자의 경우 Fragment 로 제공한다.
 */
class UserDetailFragment(private val userId: String) : Fragment() {
    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preference = requireActivity().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
        val repo = HeartRateServiceRepositoryImpl(
            Utils.getRetrofit(preference.getString(Const.TAG_ID_TOKEN, "")!!).create(
                HeartRateService::class.java))
        viewModel = ViewModelProvider(requireActivity(), WorkerDetailViewModelFactory(repo, userId)).get(
            WorkerDetailViewModelImpl::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUserDetailBinding.inflate(layoutInflater)

        initObserver(requireActivity())
        initListener(requireActivity())
        initHeartRateGraph(requireActivity())

        return binding.root
    }

    private fun initObserver(activity: Activity) {
        viewModel.state.observe(
            activity as LifecycleOwner,
            stateObserver(activity)
        )
        viewModel.dateFilter.observe(
            activity as LifecycleOwner,
            dateFilterObserver()
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

    private fun dateFilterObserver() = Observer<LocalDate> {
        binding.btnDateFilter.text = it.toString()
    }

    private fun loadSummary() {
        val user = viewModel.user

        loadUserSummary(user)
    }

    private fun loadUserSummary(user: UserModel) {
        binding.txtName.text = user.name.get()
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
        val average = if (viewModel.heartRateData.isEmpty()) EMPTY_HEART_RATE else  viewModel.heartRateData.average().toInt()
        val max = if (viewModel.heartRateData.isEmpty()) EMPTY_HEART_RATE else  viewModel.heartRateData.max().toInt()
        binding.txtAvgHeartRate.text = String.format(HEART_RATE_MESSAGE, average)
        binding.txtMaxHeartRate.text = String.format(HEART_RATE_MESSAGE, max)
    }

    private fun initListener(activity: Activity) {
        setBtnDateFilterListener(activity)
    }

    private fun setBtnDateFilterListener(activity: Activity) {
        binding.btnDateFilter.setOnClickListener {
            val date = LocalDate.parse(binding.btnDateFilter.text)
            val dialog = DatePickerDialog(activity, dateFilterListener(), date.year, date.monthValue-1, date.dayOfMonth)
            dialog.datePicker.maxDate = System.currentTimeMillis()
            dialog.datePicker.setBackgroundColor(ContextCompat.getColor(activity, R.color.white))
            dialog.show()
        }
    }

    private fun dateFilterListener() =
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.setDateFilter(LocalDate.of(year, month+1, day))
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