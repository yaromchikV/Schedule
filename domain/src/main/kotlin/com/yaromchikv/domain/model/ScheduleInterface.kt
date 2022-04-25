package com.yaromchikv.domain.model

interface ScheduleInterface {
    val group: GroupInterface
    val lisOfDays: List<DayScheduleInterface>
}

interface GroupInterface {
    val id: Int
    val name: String
    val specialityId: Int
}

interface DayScheduleInterface {
    val weekDay: String
    val listOfLessons: List<LessonInterface>
}

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

interface TeacherInterface {
    val id: Int
    val surname: String
    val name: String
    val patronymic: String
    val rank: String?
}