package com.san.heartratemonitormobile.domain.enums

enum class Action(val code: Int, val actionName: String) {
    NONE(0, "조치 필요"),
    EMERGENCY(1, "응급 조치"),
    REST(2, "즉시 휴식"),
    WORK(3, "작업 재개")
}