package com.san.heartratemonitormobile.domain.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.repository.LoginRepository
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModelImpl(
    private val repository: LoginRepository
) : SignUpViewModel, ViewModel() {
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

    private var id: Id? = null
    private var pw: PassWord? = null
    private var userPW = BLANK
    private var checkPW: String? = null
    private var name: Name? = null
    private var phoneNumber: PhoneNumber? = null
    private var gender = Gender.MALE
    private var birth: Birth? = null
    private var height: Height? = null
    private var weight: Weight? = null
    private var serviceTerm = false
    private var privacyTerm = false

    override fun setId(id: String) {
        val result = InputValidator.checkId(id)

        if (result is Valid) {
            this.id = result.data
            idValidationMessage.postValue(BLANK)
        } else {
            idValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setPassWord(pw: String) {
        userPW = pw
        val result = InputValidator.checkPassWord(pw)

        if (result is Valid) {
            this.pw = result.data
            pwValidationMessage.postValue(BLANK)
        } else {
            pwValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun checkPassWord(pw: String) {
        val result = InputValidator.doubleCheckPassWord(userPW, pw)

        if (result is Valid) {
            this.checkPW = result.data
            checkPwValidationMessage.postValue(BLANK)
        } else {
            checkPwValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setName(name: String) {
        val result = InputValidator.checkName(name)

        if (result is Valid) {
            this.name = result.data
            nameValidationMessage.postValue(BLANK)
        } else {
            nameValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setPhoneNumber(phoneNumber: String) {
        val result = InputValidator.checkPhoneNumber(phoneNumber)

        if (result is Valid) {
            this.phoneNumber = result.data
            phoneNumberValidationMessage.postValue(BLANK)
        } else {
            phoneNumberValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setGender(gender: Gender) {
        this.gender = gender
    }

    override fun setBirth(birth: String) {
        val result = InputValidator.checkBirth(birth)

        if (result is Valid) {
            this.birth = result.data
            birthValidationMessage.postValue(BLANK)
        } else {
            birthValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setHeight(height: String) {
        val result = InputValidator.checkHeight(height)

        if (result is Valid) {
            this.height = result.data
            heightValidationMessage.postValue(BLANK)
        } else {
            heightValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setWeight(weight: String) {
        val result = InputValidator.checkWeight(weight)

        if (result is Valid) {
            this.weight = result.data
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

    override fun signUp() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userSignUp()
            }
        }
    }

    suspend fun userSignUp() {
        if (signUpCondition()) {
            // TODO: SignUpModel
            // TODO: signUpSuccess 라이브 데이터를 만들어서 성공한 경우에만 signup activity finish() 하도록 설정 필요
        }
    }

    private fun signUpCondition() =
        id != null && pw != null && checkPW != null && name != null && phoneNumber != null
                && birth != null && height != null && weight != null && serviceTerm

    companion object {
        private const val BLANK = ""
    }
}