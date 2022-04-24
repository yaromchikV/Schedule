package com.yaromchikv.schedule.di

import com.yaromchikv.domain.usecase.AddScheduleUseCase
import com.yaromchikv.domain.usecase.GetAccessPermissionUseCase
import com.yaromchikv.domain.usecase.GetHashPasswordUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetAccessPermissionUseCase(repository = get()) }
    factory { GetHashPasswordUseCase() }
    factory { AddScheduleUseCase(repository = get()) }

}