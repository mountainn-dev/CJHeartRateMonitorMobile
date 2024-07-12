package com.san.heartratemonitormobile.listener

import android.text.Editable
import android.text.TextWatcher

/**
 * class EditTextChangedListener
 *
 * EditText 의 입력값이 변화할 때, 즉시 변화값을 감지하고 입력받은 기능을 수행하기 위한 리스너
 * 기존 TextWatcher 의 다른 기능을 제외하고 onTextChanged 만을 수행하기 위해 제작
 */
class EditTextChangedListener(private val onTextChangedListener: TextChangedListener) : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        onTextChangedListener.onTextChanged(p0, p1, p2, p3)
    }

    override fun afterTextChanged(p0: Editable?) {}
}