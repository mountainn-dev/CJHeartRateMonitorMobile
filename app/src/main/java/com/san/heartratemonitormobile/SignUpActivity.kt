package com.san.heartratemonitormobile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.san.heartratemonitormobile.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        setBtnCheckTermListener()
        setBtnShowTermDetailListener()
    }

    private fun setBtnCheckTermListener() {
        binding.llServiceTerm.setOnClickListener {
            // TODO: 뷰모델 serviceTerm 데이터 수정
            binding.imgCheckServiceTerm.visibility =
                if (binding.imgCheckServiceTerm.isVisible) View.GONE else View.VISIBLE
        }
        binding.llPrivacyTerm.setOnClickListener {
            // TODO: 뷰모델 privacyTerm 데이터 수정
            binding.imgCheckPrivacyTerm.visibility =
                if (binding.imgCheckPrivacyTerm.isVisible) View.GONE else View.VISIBLE
        }
    }

    private fun setBtnShowTermDetailListener() {
        binding.btnShowServiceTermDetail.setOnClickListener {}
        binding.btnShowPrivacyTermDetail.setOnClickListener {}
    }
}