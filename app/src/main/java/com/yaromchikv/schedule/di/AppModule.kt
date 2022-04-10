package com.yaromchikv.schedule.di

import com.yaromchikv.schedule.presentation.feature.schedule.ScheduleViewModel
import com.yaromchikv.schedule.presentation.feature.viewpager.ViewPagerAdapter
import com.yaromchikv.schedule.presentation.feature.viewpager.page.PageViewModel
import com.yaromchikv.schedule.presentation.feature.viewpager.page.ScheduleAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<ScheduleViewModel> {
        ScheduleViewModel(getListOfDaysOfWeekUseCase = get())
    }

    viewModel<PageViewModel> { parameters ->
        PageViewModel(dayIndex = parameters.get(), getListOfLessonsUseCase = get())
    }

    factory<ScheduleAdapter> {
        ScheduleAdapter(context = get())
    }

    factory<ViewPagerAdapter> { parameters ->
        ViewPagerAdapter(parameters.get())
    }
}