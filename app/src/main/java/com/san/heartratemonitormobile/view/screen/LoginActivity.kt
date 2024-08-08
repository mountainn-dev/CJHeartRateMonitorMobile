package com.san.heartratemonitormobile.view.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.NodeClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import com.san.heartratemonitormobile.BuildConfig
import com.san.heartratemonitormobile.data.remote.retrofit.LoginService
import com.san.heartratemonitormobile.data.repositoryimpl.LoginRepositoryImpl
import com.san.heartratemonitormobile.databinding.ActivityLoginBinding
import com.san.heartratemonitormobile.domain.model.AccountModel
import com.san.heartratemonitormobile.domain.utils.Const
import com.san.heartratemonitormobile.domain.utils.Utils
import com.san.heartratemonitormobile.view.viewmodel.LoginViewModel
import com.san.heartratemonitormobile.view.viewmodelfactory.LoginViewModelFactory
import com.san.heartratemonitormobile.view.viewmodelimpl.LoginViewModelImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var dataClient: DataClient

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
        sendMessageToWatch(it.idToken,binding.edtId.text.toString())
        sendUserToHomeScreen(activity, it, binding.edtId.text.toString())
    }

    private fun loginFailObserver(activity: Activity) = Observer<Boolean> {
        Toast.makeText(activity, LOGIN_FAIL_MESSAGE, Toast.LENGTH_SHORT).show()
    }

    private fun sendMessageToWatch(idToken: String, userId: String) {
        val putDataRequest = PutDataMapRequest.create("/userData").apply {
            dataMap.putString(BuildConfig.APPLICATION_ID + Const.TAG_ID_TOKEN, idToken)
            dataMap.putString(BuildConfig.APPLICATION_ID + Const.TAG_ID, userId)
            setUrgent()
        }.asPutDataRequest()

        dataClient.putDataItem(putDataRequest)
    }

    private fun sendUserToHomeScreen(activity: Activity, account: AccountModel, id: String) {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.putExtra(Const.TAG_ACCOUNT, account)
        intent.putExtra(Const.TAG_ID, id)

        startActivity(intent)
    }

    private fun initListener(activity: Activity) {
        setNodeClientListener(activity)
        setBtnLoginListener()
        setBtnSignUpListener(activity)
    }

    private fun setNodeClientListener(activity: Activity) {
        dataClient = Wearable.getDataClient(activity)
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