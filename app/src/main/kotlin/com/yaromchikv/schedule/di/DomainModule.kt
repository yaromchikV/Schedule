package com.yaromchikv.schedule.di

import com.yaromchikv.domain.usecase.DeleteLessonUseCase
import com.yaromchikv.domain.usecase.GetAccessPermissionUseCase
import com.yaromchikv.domain.usecase.GetClassroomsUseCase
import com.yaromchikv.domain.usecase.GetDaysOfWeekUseCase
import com.yaromchikv.domain.usecase.GetGroupsUseCase
import com.yaromchikv.domain.usecase.GetHashPasswordUseCase
import com.yaromchikv.domain.usecase.GetIdByUsernameUseCase
import com.yaromchikv.domain.usecase.GetLessonByIdUseCase
import com.yaromchikv.domain.usecase.GetLessonTypesUseCase
import com.yaromchikv.domain.usecase.GetLessonsUseCase
import com.yaromchikv.domain.usecase.GetTeachersUseCase
import com.yaromchikv.domain.usecase.UpdateLessonUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetLessonsUseCase(repository = get()) }
    factory { GetLessonByIdUseCase(repository = get()) }
    factory { GetDaysOfWeekUseCase(repository = get()) }
    factory { GetIdByUsernameUseCase(repository = get()) }
    factory { GetAccessPermissionUseCase(repository = get()) }
    factory { GetHashPasswordUseCase() }
    factory { GetGroupsUseCase(repository = get()) }
    factory { UpdateLessonUseCase(repository = get()) }
    factory { DeleteLessonUseCase(repository = get()) }
    factory { GetTeachersUseCase(repository = get()) }
    factory { GetLessonTypesUseCase(repository = get()) }
    factory { GetClassroomsUseCase(repository = get()) }
}