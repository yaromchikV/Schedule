package com.yaromchikv.domain.model.schedule

interface LessonInterface {
    val classroom: List<String>
    val weeks: List<Int>
    val startTime: String
    val endTime: String
    val lessonType: String
    val subject: String
    val note: String?
    val teacher: List<TeacherInterface>
    val subgroup: Int
}