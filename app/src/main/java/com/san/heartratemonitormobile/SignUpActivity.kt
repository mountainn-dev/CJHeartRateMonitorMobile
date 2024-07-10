package com.san.heartratemonitormobile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.san.heartratemonitormobile.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_sign_up)

        initListener()
    }

    private fun initListener() {
        setBtnCheckTermListener()
    }

    private fun setBtnCheckTermListener() {
        binding.llPrivacyTerm.setOnClickListener {
            Log.d("privacy", "s")
            // TODO: 뷰모델 내 privacyTerm 데이터 수정
            binding.imgCheckPrivacyTerm.visibility =
                if (binding.imgCheckPrivacyTerm.isVisible) View.GONE else View.VISIBLE
        }
    }
}