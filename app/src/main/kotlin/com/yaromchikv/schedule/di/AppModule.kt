package com.yaromchikv.schedule.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.yaromchikv.schedule.presentation.MainViewModel
import com.yaromchikv.schedule.presentation.feature.change_group_menu.GroupsAdapter
import com.yaromchikv.schedule.presentation.feature.editing.EditLessonViewModel
import com.yaromchikv.schedule.presentation.feature.editing.choosing_classroom.ChoosingClassroomAdapter
import com.yaromchikv.schedule.presentation.feature.editing.choosing_classroom.ChoosingClassroomViewModel
import com.yaromchikv.schedule.presentation.feature.editing.choosing_day_of_week.ChoosingDayOfWeekAdapter
import com.yaromchikv.schedule.presentation.feature.editing.choosing_day_of_week.ChoosingDayOfWeekViewModel
import com.yaromchikv.schedule.presentation.feature.editing.choosing_lesson_type.ChoosingLessonTypeAdapter
import com.yaromchikv.schedule.presentation.feature.editing.choosing_lesson_type.ChoosingLessonTypeViewModel
import com.yaromchikv.schedule.presentation.feature.editing.choosing_teacher.ChoosingTeacherAdapter
import com.yaromchikv.schedule.presentation.feature.editing.choosing_teacher.ChoosingTeacherViewModel
import com.yaromchikv.schedule.presentation.feature.login.LoginViewModel
import com.yaromchikv.schedule.presentation.feature.schedule.ScheduleViewModel
import com.yaromchikv.schedule.presentation.feature.viewpager.page.PageViewModel
import com.yaromchikv.schedule.presentation.feature.viewpager.page.ScheduleAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(get()) }

    viewModel { MainViewModel(preferences = get(), getGroupsUseCase = get()) }

    viewModel { LoginViewModel(getIdByUsernameUseCase = get(), getAccessPermissionUseCase = get()) }

    viewModel { ScheduleViewModel(getDaysOfWeekUseCase = get()) }

    //viewModel { ChangeGroupViewModel() }

    viewModel { parameters ->
        PageViewModel(
            dayIndex = parameters.get(),
            getLessonsUseCase = get(),
            preferences = get()
        )
    }

    viewModel {
        EditLessonViewModel(
            getLessonByIdUseCase = get(),
            updateLessonUseCase = get(),
            deleteLessonUseCase = get()
        )
    }

    viewModel { ChoosingClassroomViewModel(getClassroomsUseCase = get()) }
    viewModel { ChoosingDayOfWeekViewModel(getDaysOfWeekUseCase = get()) }
    viewModel { ChoosingLessonTypeViewModel(getLessonTypesUseCase = get()) }
    viewModel { ChoosingTeacherViewModel(getTeachersUseCase = get()) }

    factory { ScheduleAdapter() }
    factory { GroupsAdapter() }
    factory { ChoosingClassroomAdapter() }
    factory { ChoosingDayOfWeekAdapter() }
    factory { ChoosingLessonTypeAdapter() }
    factory { ChoosingTeacherAdapter() }


}