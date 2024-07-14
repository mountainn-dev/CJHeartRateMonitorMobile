package com.san.heartratemonitormobile.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.data.repositoryimpl.LoginRepositoryImpl
import com.san.heartratemonitormobile.data.repositoryimpl.ServiceRepositoryImpl
import com.san.heartratemonitormobile.databinding.ActivityLoginBinding
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.utils.Const
import com.san.heartratemonitormobile.domain.viewmodel.LoginViewModel
import com.san.heartratemonitormobile.domain.viewmodelfactory.LoginViewModelFactory
import com.san.heartratemonitormobile.domain.viewmodelimpl.LoginViewModelImpl

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = LoginRepositoryImpl()
        viewModel = ViewModelProvider(this, LoginViewModelFactory(repo)).get(LoginViewModelImpl::class.java)

        initObserver(this)
        initListener(this)
    }

    private fun initObserver(activity: Activity) {
        viewModel.account.observe(
            activity as LifecycleOwner,
            accountObserver(activity)
        )
    }

    private fun accountObserver(activity: Activity) = Observer<AccountModel> {
        sendUserToHomeScreen(activity, it)
    }

    private fun sendUserToHomeScreen(activity: Activity, account: AccountModel) {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.putExtra(Const.TAG_ACCOUNT, account)

        startActivity(intent)
    }

    private fun initListener(activity: Activity) {
        setBtnLoginForWorkerListener()
        setBtnLoginForAdminListener()
        setBtnSignUpListener(activity)
    }

    private fun setBtnLoginForWorkerListener() {
        binding.btnWorkerLogin.setOnClickListener {
            viewModel.loginForWorker()
        }
    }

    private fun setBtnLoginForAdminListener() {
        binding.btnAdminLogin.setOnClickListener {
            viewModel.loginForAdmin()
        }
    }

    private fun setBtnSignUpListener(activity: Activity) {
        binding.btnSignUp.setOnClickListener {
            sendUserToSignUpScreen(activity)
        }
    }

    private fun sendUserToSignUpScreen(activity: Activity) {
        val intent = Intent(activity, SignUpActivity::class.java)

        startActivity(intent)
    }
}