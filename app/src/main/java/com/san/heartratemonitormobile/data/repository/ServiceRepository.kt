package com.san.heartratemonitormobile.data.repository

import com.san.heartratemonitormobile.domain.model.LoginResultModel

interface ServiceRepository {
    suspend fun loginForWorker(): LoginResultModel   // TODO: 로그인 api 개발 이후 login() 으로 통일 및 id, pw param 추가 필요
    suspend fun loginForAdmin(): LoginResultModel
}