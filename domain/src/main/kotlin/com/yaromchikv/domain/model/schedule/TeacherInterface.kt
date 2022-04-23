package com.yaromchikv.domain.model.schedule

interface TeacherInterface {
    val id: Int
    val surname: String
    val name: String
    val patronymic: String
    val rank: String?
}