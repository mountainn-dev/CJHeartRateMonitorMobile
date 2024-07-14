package com.san.heartratemonitormobile.data.repositoryimpl

import com.san.heartratemonitormobile.data.repository.ServiceRepository
import com.san.heartratemonitormobile.data.vo.Birth
import com.san.heartratemonitormobile.data.vo.Height
import com.san.heartratemonitormobile.data.vo.Id
import com.san.heartratemonitormobile.data.vo.Name
import com.san.heartratemonitormobile.data.vo.PhoneNumber
import com.san.heartratemonitormobile.data.vo.Weight
import com.san.heartratemonitormobile.domain.enums.Action
import com.san.heartratemonitormobile.domain.enums.Gender
import com.san.heartratemonitormobile.domain.model.ReportModel
import com.san.heartratemonitormobile.domain.model.UserModel
import java.time.LocalDate
import java.time.LocalTime

class ServiceRepositoryImpl : ServiceRepository {
    override suspend fun getReports(): List<ReportModel> {
        val list = mutableListOf<ReportModel>()
        val testModel = ReportModel(
            Id("test123"),
            Name("홍성산"),
            PhoneNumber("010-1234-1234"),
            Gender.MALE,
            Birth("000101"),
            Height("100"),
            Weight("100"),
            140,
            1,
            1,
            150,
            LocalDate.parse("2024-07-14"),
            LocalTime.parse("12:00:00"),
            Action.WORK,
            32.123f,
            127.1f
        )

        repeat(2, {list.add(testModel)})

        return list
    }

    override suspend fun getUsers(): List<UserModel> {
        val list = mutableListOf<UserModel>()
        val testModel1 = UserModel(
            Id("test123"),
            Name("홍성산"),
            PhoneNumber("010-1234-1234"),
            Gender.MALE,
            Birth("000101"),
            Height("100"),
            Weight("100"),
            1,
            1,
            1,
            130
        )
        val testModel2 = UserModel(
            Id("test123"),
            Name("홍성산"),
            PhoneNumber("010-1234-1234"),
            Gender.MALE,
            Birth("000101"),
            Height("100"),
            Weight("100"),
            1,
            0,
            1,
            130
        )

        list.add(testModel1)
        list.add(testModel2)

        return list
    }
}