package com.yaromchikv.schedule.util

import com.yaromchikv.data.models.entity.BuildingEntity
import com.yaromchikv.data.models.entity.ClassroomEntity
import com.yaromchikv.data.models.entity.DayOfWeekEntity
import com.yaromchikv.data.models.entity.FacultyEntity
import com.yaromchikv.data.models.entity.GroupEntity
import com.yaromchikv.data.models.entity.LessonEntity
import com.yaromchikv.data.models.entity.LessonTypeEntity
import com.yaromchikv.data.models.entity.RoleEntity
import com.yaromchikv.data.models.entity.SpecialityEntity
import com.yaromchikv.data.models.entity.TeacherEntity
import com.yaromchikv.data.models.entity.UserEntity
import com.yaromchikv.domain.usecase.GetHashPasswordUseCase

class DataGenerator {
    companion object {

        fun generateRoles(): List<RoleEntity> {
            val list = mutableListOf<RoleEntity>()
            list.add(RoleEntity(name = "Пользователь", role = 0))
            list.add(RoleEntity(name = "Администратор", role = 1))
            return list
        }

        fun generateUsers(): List<UserEntity> {
            val list = mutableListOf<UserEntity>()
            list.add(
                UserEntity(
                    id = 1,
                    username = "username",
                    password = GetHashPasswordUseCase().invoke("1password"),
                    roleId = 1
                )
            )
            list.add(
                UserEntity(
                    id = 2,
                    username = "admin",
                    password = GetHashPasswordUseCase().invoke("2admin"),
                    roleId = 2
                )
            )
            return list
        }

        fun generateDaysOfWeek(): List<DayOfWeekEntity> {
            val list = mutableListOf<DayOfWeekEntity>()
            list.add(DayOfWeekEntity(name = "Понедельник"))
            list.add(DayOfWeekEntity(name = "Вторник"))
            list.add(DayOfWeekEntity(name = "Среда"))
            list.add(DayOfWeekEntity(name = "Четверг"))
            list.add(DayOfWeekEntity(name = "Пятница"))
            list.add(DayOfWeekEntity(name = "Суббота"))
            list.add(DayOfWeekEntity(name = "Воскресенье"))
            return list
        }

        fun generateBuildings(): List<BuildingEntity> {
            val list = mutableListOf<BuildingEntity>()
            list.add(BuildingEntity(number = 1, name = "1 к."))
            list.add(BuildingEntity(number = 2, name = "2 к."))
            list.add(BuildingEntity(number = 3, name = "3 к."))
            return list
        }

        fun generateClassrooms(): List<ClassroomEntity> {
            val list = mutableListOf<ClassroomEntity>()
            list.add(ClassroomEntity(number = "610", buildingId = 1))
            list.add(ClassroomEntity(number = "205", buildingId = 3))
            list.add(ClassroomEntity(number = "401", buildingId = 2))
            return list
        }

        fun generateFaculties(): List<FacultyEntity> {
            val list = mutableListOf<FacultyEntity>()
            list.add(FacultyEntity(name = "ФКП"))
            list.add(FacultyEntity(name = "ФКСиС"))
            return list
        }

        fun generateSpecialities(): List<SpecialityEntity> {
            val list = mutableListOf<SpecialityEntity>()
            list.add(SpecialityEntity(name = "ИПОИТ", facultyId = 1))
            list.add(SpecialityEntity(name = "ИСИТ (БМ)", facultyId = 1))
            list.add(SpecialityEntity(name = "ИиТП", facultyId = 2))
            return list
        }

        fun generateGroups(): List<GroupEntity> {
            val list = mutableListOf<GroupEntity>()
            list.add(GroupEntity(name = "910903", specialityId = 1))
            list.add(GroupEntity(name = "014301", specialityId = 2))
            list.add(GroupEntity(name = "850301", specialityId = 3))
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
            list.add(
                TeacherEntity(
                    surname = "Иванов",
                    name = "Иван",
                    patronymic = "Иванович",
                    rank = "профессор"
                )
            )
            return list
        }

        fun generateLessonTypes(): List<LessonTypeEntity> {
            val list = mutableListOf<LessonTypeEntity>()
            list.add(LessonTypeEntity(type = "ЛК"))
            list.add(LessonTypeEntity(type = "ПЗ"))
            list.add(LessonTypeEntity(type = "ЛР"))
            return list
        }

        fun generateLessons(): List<LessonEntity> {
            val list = mutableListOf<LessonEntity>()
            list.add(
                LessonEntity(
                    dayOfWeekId = 1,
                    classroomId = 1,
                    weeks = "24",
                    startTime = "10:30",
                    endTime = "11:50",
                    typeId = 1,
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
                    classroomId = 2,
                    weeks = "1234",
                    startTime = "15:30",
                    endTime = "19:50",
                    typeId = 2,
                    subject = "ИНИС",
                    note = null,
                    groupId = 1,
                    subgroup = 2,
                    teacherId = 1
                )
            )
            list.add(
                LessonEntity(
                    dayOfWeekId = 4,
                    classroomId = 3,
                    weeks = "12",
                    startTime = "12:30",
                    endTime = "14:50",
                    typeId = 1,
                    subject = "ТВР",
                    note = null,
                    groupId = 1,
                    subgroup = 1,
                    teacherId = 1
                )
            )


            list.add(
                LessonEntity(
                    dayOfWeekId = 5,
                    classroomId = 1,
                    weeks = "24",
                    startTime = "10:30",
                    endTime = "11:50",
                    typeId = 3,
                    subject = "ЙЙЙ",
                    note = null,
                    groupId = 2,
                    subgroup = 0,
                    teacherId = 1
                )
            )
            list.add(
                LessonEntity(
                    dayOfWeekId = 2,
                    classroomId = 2,
                    weeks = "1234",
                    startTime = "15:30",
                    endTime = "19:50",
                    typeId = 1,
                    subject = "ЙФЯ",
                    note = null,
                    groupId = 2,
                    subgroup = 2,
                    teacherId = 1
                )
            )
            list.add(
                LessonEntity(
                    dayOfWeekId = 6,
                    classroomId = 3,
                    weeks = "12",
                    startTime = "12:30",
                    endTime = "14:50",
                    typeId = 2,
                    subject = "МСА",
                    note = "Примечание",
                    groupId = 2,
                    subgroup = 1,
                    teacherId = 1
                )
            )

            list.add(
                LessonEntity(
                    dayOfWeekId = 1,
                    classroomId = 3,
                    weeks = "24",
                    startTime = "10:30",
                    endTime = "11:50",
                    typeId = 3,
                    subject = "ЩЩЩ",
                    note = null,
                    groupId = 3,
                    subgroup = 0,
                    teacherId = 1
                )
            )
            list.add(
                LessonEntity(
                    dayOfWeekId = 4,
                    classroomId = 2,
                    weeks = "1234",
                    startTime = "15:30",
                    endTime = "19:50",
                    typeId = 2,
                    subject = "ЫД",
                    note = "опвофл",
                    groupId = 3,
                    subgroup = 2,
                    teacherId = 1
                )
            )
            list.add(
                LessonEntity(
                    dayOfWeekId = 4,
                    classroomId = 1,
                    weeks = "12",
                    startTime = "12:30",
                    endTime = "14:50",
                    typeId = 2,
                    subject = "ИАЛА",
                    note = null,
                    groupId = 3,
                    subgroup = 1,
                    teacherId = 1
                )
            )

            return list
        }
    }
}