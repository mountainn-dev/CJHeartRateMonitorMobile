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
import com.san.heartratemonitormobile.databinding.FragmentUserBinding
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.model.UserModel
import com.san.heartratemonitormobile.domain.state.UiState
import com.san.heartratemonitormobile.domain.utils.Const
import com.san.heartratemonitormobile.domain.utils.Utils
import com.san.heartratemonitormobile.view.adapter.UserAdapter
import com.san.heartratemonitormobile.view.listener.ItemClickEventListener
import com.san.heartratemonitormobile.view.viewmodel.UserViewModel
import com.san.heartratemonitormobile.view.viewmodelfactory.UserViewModelFactory
import com.san.heartratemonitormobile.view.viewmodelimpl.UserViewModelImpl
import java.time.LocalDate

class UserFragment(
    private val account: AccountModel,
    private val userId: String
) : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preference = requireActivity().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
        val repo = HeartRateServiceRepositoryImpl(
            Utils.getRetrofit(preference.getString(Const.TAG_ID_TOKEN, "")!!).create(
                HeartRateService::class.java))
        viewModel = ViewModelProvider(requireActivity(), UserViewModelFactory(repo)).get(
            UserViewModelImpl::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUserBinding.inflate(layoutInflater)

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
                loadUsers(activity)
                toggleView(binding.rvUser)
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

    private fun loadUsers(activity: Activity) {
        binding.rvUser.adapter = UserAdapter(
            viewModel.users,
            userItemClickEventListener(viewModel.users, activity),
            activity
        )
        binding.rvUser.layoutManager = LinearLayoutManager(activity)
    }

    private fun userItemClickEventListener(
        items: List<UserModel>,
        activity: Activity
    ) = object : ItemClickEventListener {
        override fun onItemClickListener(position: Int) {
            val intent = Intent(activity, UserDetailActivity::class.java)
            intent.putExtra(Const.TAG_USER, items[position])
            intent.putExtra(Const.TAG_ID, userId)

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
        binding.btnRefresh.setOnClickListener {
            viewModel.load()
        }
        binding.btnTimeoutRequest.setOnClickListener {
            viewModel.load()
        }
        binding.btnServiceErrorRequest.setOnClickListener {
            viewModel.load()
        }
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
            viewModel.setStartDateAndLoad(LocalDate.of(year, month+1, day))
        }

    private fun endDateSetListener() =
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.setEndDateAndLoad(LocalDate.of(year, month+1, day))
        }

    private fun setBtnFilterIdListener() {
        binding.edtId.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.filterById(binding.edtId.text.toString())
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    private fun toggleView(view: View) {
        binding.rvUser.visibility = if (view == binding.rvUser) View.VISIBLE else View.GONE
        binding.pgbSearchRoute.visibility = if (view == binding.pgbSearchRoute) View.VISIBLE else View.GONE
        binding.llTimeout.visibility = if (view == binding.llTimeout) View.VISIBLE else View.GONE
        binding.llServiceError.visibility = if (view == binding.llServiceError) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

}