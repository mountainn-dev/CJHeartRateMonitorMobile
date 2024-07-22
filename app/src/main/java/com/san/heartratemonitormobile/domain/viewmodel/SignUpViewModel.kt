package com.san.heartratemonitormobile.domain.viewmodel

import androidx.lifecycle.LiveData
import com.san.heartratemonitormobile.domain.enums.Gender

interface SignUpViewModel {
    val idMessage: LiveData<String>
    val checkIdMessage: LiveData<String>
    val pwMessage: LiveData<String>
    val checkPwMessage: LiveData<String>
    val nameMessage: LiveData<String>
    val phoneNumberMessage: LiveData<String>
    val birthMessage: LiveData<String>
    val weightMessage: LiveData<String>
    val heightMessage: LiveData<String>

    fun setId(id: String)
    fun checkIdDuplication()
    fun setPassWord(pw: String)
    fun checkPassWord(pw: String)
    fun setName(name: String)
    fun setPhoneNumber(phoneNumber: String)
    fun setGender(gender: Gender)
    fun setBirth(birth: String)
    fun setHeight(height: String)
    fun setWeight(weight: String)
    fun toggleServiceTerm()
    fun togglePrivacyTerm()
    fun signUp()
}