package com.yaromchikv.schedule.di

import com.yaromchikv.schedule.presentation.feature.viewpager.page.PageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<PageViewModel> {
        PageViewModel(getListOfLessonsUseCase = get())
    }
}