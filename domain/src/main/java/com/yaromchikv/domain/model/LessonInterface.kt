package com.yaromchikv.domain.model

interface LessonInterface {
    val id: Int
    val subject: String
    val type: String
    val note: String?
    val startTime: String
    val endTime: String
    val weeks: String
    val subgroupNum: String?
    val teacher: String
    val classroomName: String
}