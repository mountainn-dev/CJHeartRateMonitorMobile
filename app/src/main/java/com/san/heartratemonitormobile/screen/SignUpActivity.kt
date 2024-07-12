package com.san.heartratemonitormobile.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.san.heartratemonitormobile.databinding.ActivitySignUpBinding
import com.san.heartratemonitormobile.domain.enums.Gender
import com.san.heartratemonitormobile.domain.viewmodel.SignUpViewModel
import com.san.heartratemonitormobile.domain.viewmodelfactory.SignUpViewModelFactory
import com.san.heartratemonitormobile.domain.viewmodelimpl.SignUpViewModelImpl
import com.san.heartratemonitormobile.listener.EditTextChangedListener
import com.san.heartratemonitormobile.listener.TextChangedListener

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, SignUpViewModelFactory()
        ).get(SignUpViewModelImpl::class.java)

        initListener()
    }

    private fun initListener() {
        setSignUpItemsListener()
    }

    private fun setSignUpItemsListener() {
        setEdtIdListener()
        setEdtPassWordListener()
        setEdtCheckPassWordListener()
        setEdtNameListener()
        setEdtPhoneNumberListener()
        setEdtBirthListener()
        setEdtHeightListener()
        setEdtWeightListener()
        setRadioGenderListener()
        setBtnCheckTermListener()
        setBtnShowTermDetailListener()
    }

    private fun setEdtIdListener() {
        binding.edtId.addTextChangedListener(
            EditTextChangedListener(
                object : TextChangedListener {
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        viewModel.setId(p0.toString())
                    }
                }
            )
        )
    }

    private fun setEdtPassWordListener() {
        binding.edtPassword.addTextChangedListener(
            EditTextChangedListener(
                object : TextChangedListener {
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        viewModel.setPassWord(p0.toString())
                    }
                }
            )
        )
    }

    private fun setEdtCheckPassWordListener() {
        binding.edtCheckPassword.addTextChangedListener(
            EditTextChangedListener(
                object : TextChangedListener {
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        viewModel.checkPassWord(p0.toString())
                    }
                }
            )
        )
    }

    private fun setEdtNameListener() {
        binding.edtName.addTextChangedListener(
            EditTextChangedListener(
                object : TextChangedListener {
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        viewModel.setName(p0.toString())
                    }
                }
            )
        )
    }

    private fun setEdtPhoneNumberListener() {
        binding.edtPhoneNumber.addTextChangedListener(
            EditTextChangedListener(
                object : TextChangedListener {
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        viewModel.setPhoneNumber(p0.toString())
                    }
                }
            )
        )
    }

    private fun setEdtBirthListener() {
        binding.edtBirth.addTextChangedListener(
            EditTextChangedListener(
                object : TextChangedListener {
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        viewModel.setBirth(p0.toString())
                    }
                }
            )
        )
    }

    private fun setEdtHeightListener() {
        binding.edtHeight.addTextChangedListener(
            EditTextChangedListener(
                object : TextChangedListener {
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        viewModel.setHeight(p0.toString())
                    }
                }
            )
        )
    }

    private fun setEdtWeightListener() {
        binding.edtWeight.addTextChangedListener(
            EditTextChangedListener(
                object : TextChangedListener {
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        viewModel.setWeight(p0.toString())
                    }
                }
            )
        )
    }

    private fun setRadioGenderListener() {
        binding.radioMale.setOnClickListener { viewModel.setGender(Gender.MALE) }
        binding.radioFemale.setOnClickListener { viewModel.setGender(Gender.FEMALE) }
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