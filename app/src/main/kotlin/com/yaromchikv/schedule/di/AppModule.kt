package com.yaromchikv.schedule.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.yaromchikv.schedule.presentation.MainViewModel
import com.yaromchikv.schedule.presentation.feature.change_group.ChangeGroupViewModel
import com.yaromchikv.schedule.presentation.feature.login.LoginViewModel
import com.yaromchikv.schedule.presentation.feature.modify_lessons.ModifyLessonViewModel
import com.yaromchikv.schedule.presentation.feature.modify_lessons.choosing_list.ChoosingModelAdapter
import com.yaromchikv.schedule.presentation.feature.modify_lessons.choosing_list.ChoosingModelViewModel
import com.yaromchikv.schedule.presentation.feature.schedule.ScheduleViewModel
import com.yaromchikv.schedule.presentation.feature.viewpager.page.PageViewModel
import com.yaromchikv.schedule.presentation.feature.viewpager.page.ScheduleAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(get()) }

    viewModel { MainViewModel(preferences = get(), repository = get()) }

    viewModel { LoginViewModel(repository = get(), getAccessPermissionUseCase = get()) }

    viewModel { ScheduleViewModel(repository = get()) }

    viewModel { ChangeGroupViewModel(repository = get()) }

    viewModel { parameters ->
        PageViewModel(
            dayIndex = parameters.get(),
            repository = get(),
            preferences = get()
        )
    }

    viewModel {
        ModifyLessonViewModel(
            repository = get()
        )
    }

    viewModel { parameters ->
        ChoosingModelViewModel(
            listMode = parameters.get(),
            repository = get()
        )
    }

    factory { ScheduleAdapter() }
    factory { ChoosingModelAdapter() }

}