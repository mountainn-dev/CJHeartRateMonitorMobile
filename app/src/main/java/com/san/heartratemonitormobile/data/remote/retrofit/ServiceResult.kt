package com.san.heartratemonitormobile.data.remote.retrofit

enum class ServiceResult(val code: Int, val message: String) {
    SUCCESS(200, "정상적으로 처리되었습니다."),
    WRONG_ID_PASSWORD(401, "아이디 또는 비밀번호가 올바르지 않습니다."),
    NO_RESULT(404, "조회 결과가 존재하지 않습니다."),
    ID_DUPLICATION(409, "중복 아이디가 존재합니다.")
}