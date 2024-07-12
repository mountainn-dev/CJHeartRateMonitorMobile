package com.san.heartratemonitormobile.domain.viewmodelimpl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.san.heartratemonitormobile.data.vo.Weight
import com.san.heartratemonitormobile.domain.enums.Gender
import com.san.heartratemonitormobile.domain.utils.InputValidator
import com.san.heartratemonitormobile.domain.utils.Invalid
import com.san.heartratemonitormobile.domain.utils.Valid
import com.san.heartratemonitormobile.domain.viewmodel.SignUpViewModel

class SignUpViewModelImpl : SignUpViewModel, ViewModel() {
    override val idMessage: LiveData<String>
        get() = idValidationMessage
    private val idValidationMessage = MutableLiveData<String>()
    override val pwMessage: LiveData<String>
        get() = pwValidationMessage
    private val pwValidationMessage = MutableLiveData<String>()
    override val checkPwMessage: LiveData<String>
        get() = checkPwValidationMessage
    private val checkPwValidationMessage = MutableLiveData<String>()
    override val nameMessage: LiveData<String>
        get() = nameValidationMessage
    private val nameValidationMessage = MutableLiveData<String>()
    override val phoneNumberMessage: LiveData<String>
        get() = phoneNumberValidationMessage
    private val phoneNumberValidationMessage = MutableLiveData<String>()
    override val birthMessage: LiveData<String>
        get() = birthValidationMessage
    private val birthValidationMessage = MutableLiveData<String>()
    override val weightMessage: LiveData<String>
        get() = weightValidationMessage
    private val weightValidationMessage = MutableLiveData<String>()
    override val heightMessage: LiveData<String>
        get() = heightValidationMessage
    private val heightValidationMessage = MutableLiveData<String>()

    private var id = BLANK
    private var pw = BLANK
    private var checkPw = BLANK
    private var name = BLANK
    private var phoneNumber = BLANK
    private var gender = Gender.MALE
    private var birth = BLANK
    private var height = BLANK
    private var weight = BLANK
    private var serviceTerm = false
    private var privacyTerm = false

    override fun setId(id: String) {
        val result = InputValidator.checkId(id)

        if (result is Valid) {
            this.id = id
            idValidationMessage.postValue(BLANK)
        } else {
            this.id = BLANK
            idValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setPassWord(pw: String) {
        val result = InputValidator.checkPassWord(pw)

        if (result is Valid) {
            this.pw = pw
            pwValidationMessage.postValue(BLANK)
        } else {
            this.pw = BLANK
            pwValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun checkPassWord(pw: String) {
        val result = InputValidator.doubleCheckPassWord(this.pw, pw)

        if (result is Valid) {
            this.checkPw = pw
            checkPwValidationMessage.postValue(BLANK)
        } else {
            this.checkPw = BLANK
            checkPwValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setName(name: String) {
        val result = InputValidator.checkName(name)

        if (result is Valid) {
            this.name = name
            nameValidationMessage.postValue(BLANK)
        } else {
            this.name = BLANK
            nameValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setPhoneNumber(phoneNumber: String) {
        val result = InputValidator.checkPhoneNumber(phoneNumber)

        if (result is Valid) {
            this.phoneNumber = phoneNumber
            phoneNumberValidationMessage.postValue(BLANK)
        } else {
            this.phoneNumber = BLANK
            phoneNumberValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setGender(gender: Gender) {
        this.gender = gender
    }

    override fun setBirth(birth: String) {
        val result = InputValidator.checkBirth(birth)

        if (result is Valid) {
            this.birth = birth
            birthValidationMessage.postValue(BLANK)
        } else {
            this.birth = BLANK
            birthValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setHeight(height: String) {
        val result = InputValidator.checkHeight(height)

        if (result is Valid) {
            this.height = height
            heightValidationMessage.postValue(BLANK)
        } else {
            this.height = BLANK
            heightValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setWeight(weight: String) {
        val result = InputValidator.checkWeight(weight)

        if (result is Valid) {
            this.weight = weight
            weightValidationMessage.postValue(BLANK)
        } else {
            this.weight = BLANK
            weightValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun toggleServiceTerm() {
        this.serviceTerm = !this.serviceTerm
    }

    override fun togglePrivacyTerm() {
        this.privacyTerm = !this.privacyTerm
    }

    override fun signUp() {
        if (signUpCondition()) {
            // TODO: SignUp
        }
    }

    private fun signUpCondition() =
        // TODO: 아이디 중복 검사 조건 추가
        id != BLANK && pw != BLANK && name != BLANK && phoneNumber != BLANK && birth != BLANK && height != BLANK && weight != BLANK && serviceTerm

    companion object {
        private const val BLANK = ""
    }
}