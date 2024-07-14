package com.san.heartratemonitormobile.domain.enums

enum class Action(val code: Int, val actionName: String) {
    NONE(-1, ""),
    EMERGENCY(0, "응급 조치"),
    REST(1, "즉시 휴식"),
    WORK(2, "작업 재개")
}