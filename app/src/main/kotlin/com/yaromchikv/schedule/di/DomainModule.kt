package com.yaromchikv.schedule.di

import com.yaromchikv.domain.usecase.GetAccessPermissionUseCase
import com.yaromchikv.domain.usecase.GetHashPasswordUseCase
import com.yaromchikv.domain.usecase.GetIdByUsernameUseCase
import com.yaromchikv.domain.usecase.GetListOfDaysOfWeekUseCase
import com.yaromchikv.domain.usecase.GetListOfGroupsUseCase
import com.yaromchikv.domain.usecase.GetListOfLessonsUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetListOfLessonsUseCase(repository = get()) }

    factory { GetListOfDaysOfWeekUseCase(repository = get()) }

    factory { GetIdByUsernameUseCase(repository = get()) }

    factory { GetAccessPermissionUseCase(repository = get()) }

    factory { GetHashPasswordUseCase() }

    factory { GetListOfGroupsUseCase(repository = get()) }
}