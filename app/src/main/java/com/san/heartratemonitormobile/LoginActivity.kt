package com.san.heartratemonitormobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.san.heartratemonitormobile.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener(this)
    }

    private fun initListener(activity: Activity) {
        setBtnSignUpListener(activity)
    }

    private fun setBtnSignUpListener(activity: Activity) {
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(activity, SignUpActivity::class.java)

            startActivity(intent)
        }
    }
}