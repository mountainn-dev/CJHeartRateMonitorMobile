package com.san.heartratemonitormobile.domain.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var name: String
    private lateinit var phoneNumber: String
    private lateinit var birth: String
    private lateinit var height: String
    private lateinit var weight: String
    private var gender = Gender.MALE
    private var serviceTerm = false
    private var privacyTerm = false

    override fun setId(id: String) {
        val result = InputValidator.checkId(id)

        if (result is Valid) {
            this.id = id
            idValidationMessage.postValue(VALIDATION_COMPLETED)
        }
        else idValidationMessage.postValue((result as Invalid).message())
    }

    override fun setPassWord(pw: String) {
        val result = InputValidator.checkPassWord(pw)

        if (result is Valid) {
            this.pw = pw
            pwValidationMessage.postValue(VALIDATION_COMPLETED)
        }
        else pwValidationMessage.postValue((result as Invalid).message())
    }

    override fun checkPassWord(pw: String) {
        val result = InputValidator.doubleCheckPassWord(this.pw, pw)

        if (result is Valid) {

        }
    }

    override fun setName(name: String) {
        val result = InputValidator.checkName(name)

        if (result is Valid) {
            this.name = name
            nameValidationMessage.postValue(VALIDATION_COMPLETED)
        }
        else nameValidationMessage.postValue((result as Invalid).message())
    }

    override fun setPhoneNumber(phoneNumber: String) {
        val result = InputValidator.checkPhoneNumber(phoneNumber)

        if (result is Valid) {
            this.phoneNumber = phoneNumber
            phoneNumberValidationMessage.postValue(VALIDATION_COMPLETED)
        }
        else phoneNumberValidationMessage.postValue((result as Invalid).message())
    }

    override fun setGender(gender: Gender) {
        this.gender = gender
    }

    override fun setBirth(birth: String) {
        val result = InputValidator.checkBirth(birth)

        if (result is Valid) {
            this.birth = birth
            birthValidationMessage.postValue(VALIDATION_COMPLETED)
        }
        else birthValidationMessage.postValue((result as Invalid).message())
    }

    override fun setHeight(height: String) {
        val result = InputValidator.checkHeight(height)

        if (result is Valid) {
            this.height = height
            heightValidationMessage.postValue(VALIDATION_COMPLETED)
        }
        else heightValidationMessage.postValue((result as Invalid).message())
    }

    override fun setWeight(weight: String) {
        val result = InputValidator.checkWeight(weight)

        if (result is Valid) {
            this.weight = weight
            weightValidationMessage.postValue(VALIDATION_COMPLETED)
        }
        else weightValidationMessage.postValue((result as Invalid).message())
    }

    companion object {
        private const val VALIDATION_COMPLETED = ""
    }
}