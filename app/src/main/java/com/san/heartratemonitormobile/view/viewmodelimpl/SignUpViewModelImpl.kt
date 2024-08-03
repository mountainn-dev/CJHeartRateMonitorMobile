package com.san.heartratemonitormobile.view.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.heartratemonitormobile.data.Error
import com.san.heartratemonitormobile.data.Success
import com.san.heartratemonitormobile.data.repository.LoginRepository
import com.san.heartratemonitormobile.data.vo.Birth
import com.san.heartratemonitormobile.data.vo.Height
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.data.vo.Name
import com.san.heartratemonitormobile.data.vo.PassWord
import com.san.heartratemonitormobile.data.vo.PhoneNumber
import com.san.heartratemonitormobile.data.vo.Weight
import com.san.heartratemonitormobile.domain.enums.Gender
import com.san.heartratemonitormobile.domain.model.SignUpModel
import com.san.heartratemonitormobile.domain.utils.InputValidator
import com.san.heartratemonitormobile.domain.utils.Invalid
import com.san.heartratemonitormobile.domain.utils.Valid
import com.san.heartratemonitormobile.view.viewmodel.SignUpViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModelImpl(
    private val repository: LoginRepository
) : SignUpViewModel, ViewModel() {
    override val idMessage: LiveData<String>
        get() = idValidationMessage
    private val idValidationMessage = MutableLiveData<String>()
    override val checkIdMessage: LiveData<String>
        get() = idDuplicationMessage
    private val idDuplicationMessage = MutableLiveData<String>()
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
    override val signUpMessage: LiveData<String>
        get() = signUpStateMessage
    private val signUpStateMessage = MutableLiveData<String>()

    private var id: Id? = null
    private var idDuplicated = true
    private var pw: PassWord? = null
    private var pwTemp = BLANK
    private var checkPW: String? = null
    private var name: Name? = null
    private var phoneNumber: PhoneNumber? = null
    private var gender = Gender.MALE
    private var birth: Birth? = null
    private var height: Height? = null
    private var weight: Weight? = null
    private var serviceTerm = false
    private var privacyTerm = false
    private var signUpProcess = false

    override fun setId(id: String) {
        if (!idDuplicated) idDuplicated = true   // 중복 검사 후 아이디 수정 상황 방지용

        val result = InputValidator.checkId(id)

        if (result is Valid) {
            this.id = result.data
            idValidationMessage.postValue(BLANK)
        } else {
            if (this.id != null) this.id = null
            idValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun checkIdDuplication() {
        if (id != null) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getIdDuplication(id!!)
                }
            }
        }
    }

    private suspend fun getIdDuplication(id: Id) {
        val result = repository.getIdDuplication(id.get())

        if (result is Success) {
            this.idDuplicated = result.data
            idDuplicationMessage.postValue(AVAILABLE_ID_MESSAGE)
        } else {
            idDuplicated = true
            idDuplicationMessage.postValue((result as Error).message())
        }
    }

    override fun setPassWord(pw: String) {
        pwTemp = pw
        val result = InputValidator.checkPassWord(pw)

        if (result is Valid) {
            this.pw = result.data
            pwValidationMessage.postValue(BLANK)
        } else {
            if (this.pw != null) this.pw = null
            pwValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun checkPassWord(pw: String) {
        val result = InputValidator.doubleCheckPassWord(pwTemp, pw)

        if (result is Valid) {
            this.checkPW = result.data
            checkPwValidationMessage.postValue(BLANK)
        } else {
            if (this.checkPW != null) this.checkPW = null
            checkPwValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setName(name: String) {
        val result = InputValidator.checkName(name)

        if (result is Valid) {
            this.name = result.data
            nameValidationMessage.postValue(BLANK)
        } else {
            if (this.name != null) this.name = null
            nameValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setPhoneNumber(phoneNumber: String) {
        val result = InputValidator.checkPhoneNumber(phoneNumber)

        if (result is Valid) {
            this.phoneNumber = result.data
            phoneNumberValidationMessage.postValue(BLANK)
        } else {
            if (this.phoneNumber != null) this.phoneNumber = null
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
            if (this.birth != null) this.birth = null
            birthValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setHeight(height: String) {
        val result = InputValidator.checkHeight(height)

        if (result is Valid) {
            this.height = result.data
            heightValidationMessage.postValue(BLANK)
        } else {
            if (this.height != null) this.height = null
            heightValidationMessage.postValue((result as Invalid).message())
        }
    }

    override fun setWeight(weight: String) {
        val result = InputValidator.checkWeight(weight)

        if (result is Valid) {
            this.weight = result.data
            weightValidationMessage.postValue(BLANK)
        } else {
            if (this.weight != null) this.weight = null
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
        if (signUpCondition() && !signUpProcess) {
            signUpProcess = true

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    userSignUp()
                    signUpProcess = false
                }
            }
        } else notifySignUpCondition()
    }

    private suspend fun userSignUp() {
        val signUpModel = SignUpModel(id!!, pw!!, name!!, phoneNumber!!, gender, birth!!, height!!, weight!!)
        val result = repository.signUp(signUpModel)

        if (result is Success) {
            signUpStateMessage.postValue(BLANK)
        } else {
            signUpStateMessage.postValue(FAIL_TO_SIGN_UP_MESSAGE)
        }
    }

    private fun notifySignUpCondition() {
        signUpStateMessage.postValue(SIGN_UP_CONDITION_MESSAGE)
    }

    private fun signUpCondition() =
        id != null && !idDuplicated && pw != null && checkPW != null && name != null && phoneNumber != null
                && birth != null && height != null && weight != null && serviceTerm

    companion object {
        private const val BLANK = ""
        private const val AVAILABLE_ID_MESSAGE = "사용 가능한 ID 입니다."
        private const val FAIL_TO_SIGN_UP_MESSAGE = "회원 가입에 실패하였습니다."
        private const val SIGN_UP_CONDITION_MESSAGE = "입력하신 정보를 다시 한 번 확인해주시기 바랍니다."
    }
}