package com.san.heartratemonitormobile.data.exception

object ExceptionMessage {
    // Sign Up
    const val NO_INPUT_DATA_EXCEPTION = "%s를 입력해주시기 바랍니다"
    const val WRONG_DATE_FORMAT_EXCEPTION = "날짜 형식이 올바르지 않습니다."
    const val WRONG_HEIGHT_FORMAT_EXCEPTION = "신장(cm)은 공백을 포함하지 않은 숫자 형태의 값만 입력 가능합니다."
    const val WRONG_WEIGHT_FORMAT_EXCEPTION = "체중(kg)은 공백을 포함하지 않은 숫자 형태의 값만 입력 가능합니다."
    const val WRONG_NAME_FORMAT_EXCEPTION = "이름은 공백을 포함하지 않은 문자 형태의 값만 입력 가능합니다."
    const val WRONG_ID_FORMAT_EXCEPTION = "아이디는 최소 4 ~ 12 글자의 영문, 숫자 조합만 입력 가능합니다."
    const val WRONG_PASS_WORD_FORMAT_EXCEPTION = "비밀번호는 최소 8글자 이상의 영문, 숫자, 특수문자 조합만 입력 가능합니다."
    const val WRONG_CHECK_PASS_WORD_EXCEPTION = "비밀번호가 일치하지 않습니다."
    const val WRONG_PHONE_NUMBER_FORMAT_EXCEPTION = "전화번호 형식이 올바르지 않습니다."

    // API Parse
    const val WRONG_ADMIN_VALUE_EXCEPTION = "관리자 여부 값이 올바르지 않습니다."
    const val WRONG_GENDER_VALUE_EXCEPTION = "성별 값이 올바르지 않습니다."
    const val WRONG_REPORT_DATE_TIME_FORAMT_EXCEPTION = "신고 시간 형식이 올바르지 않습니다."
    const val WRONG_ACTION_VALUE_EXCEPTION = "관리자 조치 값이 올바르지 않습니다."
}