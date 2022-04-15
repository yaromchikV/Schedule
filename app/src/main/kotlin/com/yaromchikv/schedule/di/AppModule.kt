package com.yaromchikv.schedule.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.yaromchikv.schedule.presentation.MainViewModel
import com.yaromchikv.schedule.presentation.feature.change_group_menu.GroupsAdapter
import com.yaromchikv.schedule.presentation.feature.login.LoginViewModel
import com.yaromchikv.schedule.presentation.feature.schedule.ScheduleViewModel
import com.yaromchikv.schedule.presentation.feature.viewpager.page.PageViewModel
import com.yaromchikv.schedule.presentation.feature.viewpager.page.ScheduleAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(get()) }

    viewModel { MainViewModel(preferences = get(), getListOfGroupsUseCase = get()) }

    viewModel { ScheduleViewModel(getListOfDaysOfWeekUseCase = get()) }

    //viewModel { ChangeGroupViewModel() }

    viewModel { parameters ->
        PageViewModel(
            dayIndex = parameters.get(),
            getListOfLessonsUseCase = get(),
            preferences = get()
        )
    }

    viewModel { LoginViewModel(getIdByUsernameUseCase = get(), getAccessPermissionUseCase = get()) }

    factory { ScheduleAdapter() }

    factory { GroupsAdapter() }

}