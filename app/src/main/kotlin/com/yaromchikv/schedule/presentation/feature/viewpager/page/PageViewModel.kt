package com.yaromchikv.schedule.presentation.feature.viewpager.page

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.usecase.GetListOfLessonsUseCase
import com.yaromchikv.schedule.presentation.common.GROUP_ID_PREFS_KEY
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class PageViewModel(
    private val dayIndex: Int,
    private val getListOfLessonsUseCase: GetListOfLessonsUseCase,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _lessons = MutableStateFlow<List<LessonModel>?>(null)
    val lessons: StateFlow<List<LessonModel>?> = _lessons

    private val preferenceListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == GROUP_ID_PREFS_KEY) {
                getLessons()
            }
        }

    private var getLessonsJob: Job? = null

    init {
        getLessons()
        preferences.registerOnSharedPreferenceChangeListener(preferenceListener)
    }

    private fun getLessons() {
        val groupId = preferences.getInt(GROUP_ID_PREFS_KEY, 1)

        getLessonsJob?.cancel()
        getLessonsJob = getListOfLessonsUseCase(dayIndex + 1, groupId)
            .onEach { lessons ->
                Timber.i("day: ${dayIndex}; ${if (lessons.isNotEmpty()) lessons.toString() else "EMPTY"}")
                _lessons.value = lessons
            }
            .launchIn(viewModelScope)
    }

    fun getCurrentDayName(list: List<DayOfWeekModel>, index: Int?): String {
        return list.find { day ->
            day.id == index?.plus(1)
        }?.name ?: ""
    }

    override fun onCleared() {
        super.onCleared()
        preferences.unregisterOnSharedPreferenceChangeListener(preferenceListener)
    }
}