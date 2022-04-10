package com.yaromchikv.schedule.di

import com.yaromchikv.domain.usecase.GetListOfDaysOfWeekUseCase
import com.yaromchikv.domain.usecase.GetListOfLessonsUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetListOfLessonsUseCase> {
        GetListOfLessonsUseCase(repository = get(), mapper = get())
    }

    factory<GetListOfDaysOfWeekUseCase> {
        GetListOfDaysOfWeekUseCase(repository = get())
    }
}