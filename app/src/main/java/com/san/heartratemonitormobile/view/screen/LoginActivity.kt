package com.san.heartratemonitormobile.view.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.data.remote.retrofit.LoginService
import com.san.heartratemonitormobile.data.repositoryimpl.LoginRepositoryImpl
import com.san.heartratemonitormobile.databinding.ActivityLoginBinding
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.utils.Const
import com.san.heartratemonitormobile.domain.utils.Utils
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

        val repo = LoginRepositoryImpl(Utils.getRetrofit().create(LoginService::class.java))
        viewModel = ViewModelProvider(this, LoginViewModelFactory(repo)).get(LoginViewModelImpl::class.java)

        initObserver(this)
        initListener(this)
    }

    private fun initObserver(activity: Activity) {
        viewModel.account.observe(
            activity as LifecycleOwner,
            accountObserver(activity)
        )
        viewModel.loginFail.observe(
            activity as LifecycleOwner,
            loginFailObserver(activity)
        )
    }

    private fun accountObserver(activity: Activity) = Observer<AccountModel> {
        sendUserToHomeScreen(activity, it)
    }

    private fun loginFailObserver(activity: Activity) = Observer<Boolean> {
        Toast.makeText(activity, LOGIN_FAIL_MESSAGE, Toast.LENGTH_SHORT).show()
    }

    private fun sendUserToHomeScreen(activity: Activity, account: AccountModel) {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.putExtra(Const.TAG_ACCOUNT, account)

        startActivity(intent)
    }

    private fun initListener(activity: Activity) {
        setBtnLoginListener()
        setBtnSignUpListener(activity)
    }

    private fun setBtnLoginListener() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.edtId.text.toString(), binding.edtPassword.text.toString())
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

    companion object {
        private const val LOGIN_FAIL_MESSAGE = "로그인에 실패하였습니다."
    }
}