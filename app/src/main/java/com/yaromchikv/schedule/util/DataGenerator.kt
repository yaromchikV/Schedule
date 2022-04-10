package com.yaromchikv.schedule.util

import com.yaromchikv.data.models.entity.BuildingEntity
import com.yaromchikv.data.models.entity.ClassroomEntity
import com.yaromchikv.data.models.entity.DayOfWeekEntity
import com.yaromchikv.data.models.entity.FacultyEntity
import com.yaromchikv.data.models.entity.GroupEntity
import com.yaromchikv.data.models.entity.LessonEntity
import com.yaromchikv.data.models.entity.SpecialityEntity
import com.yaromchikv.data.models.entity.TeacherEntity

class DataGenerator {
    companion object {
        fun generateDaysOfWeek(): List<DayOfWeekEntity> {
            val list = mutableListOf<DayOfWeekEntity>()
            list.add(DayOfWeekEntity(name = "Понедельник", abbrev = "ПН"))
            list.add(DayOfWeekEntity(name = "Вторник", abbrev = "ВТ"))
            list.add(DayOfWeekEntity(name = "Среда", abbrev = "СР"))
            list.add(DayOfWeekEntity(name = "Четверг", abbrev = "ЧТ"))
            list.add(DayOfWeekEntity(name = "Пятница", abbrev = "ПТ"))
            list.add(DayOfWeekEntity(name = "Суббота", abbrev = "СБ"))
            list.add(DayOfWeekEntity(name = "Воскресенье", abbrev = "ВС"))
            return list
        }

        fun generateBuildings(): List<BuildingEntity> {
            val list = mutableListOf<BuildingEntity>()
            list.add(BuildingEntity(number = 1, name = "1 к."))
            list.add(BuildingEntity(number = 2, name = "2 к."))
            return list
        }

        fun generateClassrooms(): List<ClassroomEntity> {
            val list = mutableListOf<ClassroomEntity>()
            list.add(ClassroomEntity(number = "610", buildingId = 1))
            return list
        }

        fun generateFaculties(): List<FacultyEntity> {
            val list = mutableListOf<FacultyEntity>()
            list.add(FacultyEntity(name = "ФКП"))
            return list
        }

        fun generateSpecialities(): List<SpecialityEntity> {
            val list = mutableListOf<SpecialityEntity>()
            list.add(SpecialityEntity(name = "ИПОИТ", facultyId = 1))
            return list
        }

        fun generateGroups(): List<GroupEntity> {
            val list = mutableListOf<GroupEntity>()
            list.add(GroupEntity(name = "910903", specialityId = 1))
            return list
        }

        fun generateTeachers(): List<TeacherEntity> {
            val list = mutableListOf<TeacherEntity>()
            list.add(
                TeacherEntity(
                    surname = "Яромчик",
                    name = "Владислав",
                    patronymic = "Александрович",
                    rank = "доцент"
                )
            )
            return list
        }

        fun generateLessons(): List<LessonEntity> {
            val list = mutableListOf<LessonEntity>()
            list.add(
                LessonEntity(
                    dayOfWeekId = 1,
                    classroomId = 1,
                    weekNumbers = "24",
                    startTime = "10:30",
                    endTime = "11:50",
                    type = "ПЗ",
                    subject = "УИП",
                    note = "Примечание",
                    groupId = 1,
                    subgroup = 0,
                    teacherId = 1
                )
            )
            list.add(
                LessonEntity(
                    dayOfWeekId = 3,
                    classroomId = 1,
                    weekNumbers = "01234",
                    startTime = "15:30",
                    endTime = "19:50",
                    type = "ЛК",
                    subject = "ИНИС",
                    note = null,
                    groupId = 1,
                    subgroup = 2,
                    teacherId = 1
                )
            )
            list.add(
                LessonEntity(
                    dayOfWeekId = 2,
                    classroomId = 1,
                    weekNumbers = "12",
                    startTime = "12:30",
                    endTime = "14:50",
                    type = "ЛР",
                    subject = "ТВР",
                    note = null,
                    groupId = 1,
                    subgroup = 1,
                    teacherId = 1
                )
            )
            return list
        }
    }
}