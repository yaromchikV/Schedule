package com.yaromchikv.schedule.util

import com.yaromchikv.data.models.entity.DayOfWeekEntity
import com.yaromchikv.data.models.entity.GroupEntity
import com.yaromchikv.data.models.entity.LessonEntity
import com.yaromchikv.data.models.entity.LessonTypeEntity
import com.yaromchikv.data.models.entity.RoleEntity
import com.yaromchikv.data.models.entity.SpecialityEntity
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

        fun generateLessonTypes(): List<LessonTypeEntity> {
            val list = mutableListOf<LessonTypeEntity>()
            list.add(LessonTypeEntity(type = "ЛК"))
            list.add(LessonTypeEntity(type = "ПЗ"))
            list.add(LessonTypeEntity(type = "ЛР"))
            return list
        }
    }
}