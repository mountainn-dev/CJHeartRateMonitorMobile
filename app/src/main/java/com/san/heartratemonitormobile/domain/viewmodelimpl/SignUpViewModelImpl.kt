package com.san.heartratemonitormobile.domain.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.san.heartratemonitormobile.data.vo.Birth
import com.san.heartratemonitormobile.data.vo.Height
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.data.vo.Name
import com.san.heartratemonitormobile.data.vo.PassWord
import com.san.heartratemonitormobile.data.vo.PhoneNumber
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

    private var userId: Id? = null
    private var userPW: PassWord? = null
    private var pw = BLANK
    private var userCheckPW: String? = null
    private var userName: Name? = null
    private var userPhoneNumber: PhoneNumber? = null
    private var userGender = Gender.MALE
    private var userBirth: Birth? = null
    private var userHeight: Height? = null
    private var userWeight: Weight? = null
    private var serviceTerm = false
    private var privacyTerm = false

    override fun setId(id: String) {
        val result = InputValidator.checkId(id)

        if (result is Valid) {
            this.userId = result.data
            idValidationMessage.postValue(BLANK)
        } else {
            idValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setPassWord(pw: String) {
        this.pw = pw
        val result = InputValidator.checkPassWord(pw)

        if (result is Valid) {
            this.userPW = result.data
            pwValidationMessage.postValue(BLANK)
        } else {
            pwValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun checkPassWord(pw: String) {
        val result = InputValidator.doubleCheckPassWord(this.pw, pw)

        if (result is Valid) {
            this.userCheckPW = result.data
            checkPwValidationMessage.postValue(BLANK)
        } else {
            checkPwValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setName(name: String) {
        val result = InputValidator.checkName(name)

        if (result is Valid) {
            this.userName = result.data
            nameValidationMessage.postValue(BLANK)
        } else {
            nameValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setPhoneNumber(phoneNumber: String) {
        val result = InputValidator.checkPhoneNumber(phoneNumber)

        if (result is Valid) {
            this.userPhoneNumber = result.data
            phoneNumberValidationMessage.postValue(BLANK)
        } else {
            phoneNumberValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setGender(gender: Gender) {
        this.userGender = gender
    }

    override fun setBirth(birth: String) {
        val result = InputValidator.checkBirth(birth)

        if (result is Valid) {
            this.userBirth = result.data
            birthValidationMessage.postValue(BLANK)
        } else {
            birthValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setHeight(height: String) {
        val result = InputValidator.checkHeight(height)

        if (result is Valid) {
            this.userHeight = result.data
            heightValidationMessage.postValue(BLANK)
        } else {
            heightValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setWeight(weight: String) {
        val result = InputValidator.checkWeight(weight)

        if (result is Valid) {
            this.userWeight = result.data
            weightValidationMessage.postValue(BLANK)
        } else {
            weightValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun toggleServiceTerm() {
        this.serviceTerm = !this.serviceTerm
    }

    override fun togglePrivacyTerm() {
        this.privacyTerm = !this.privacyTerm
    }

    override suspend fun signUp() {
        if (signUpCondition()) {
            // TODO: SignUpModel
        }
    }

    private fun signUpCondition() =
        userId != null && userPW != null && userCheckPW != null && userName != null && userPhoneNumber != null
                && userBirth != null && userHeight != null && userWeight != null && serviceTerm

    companion object {
        private const val BLANK = ""
    }
}