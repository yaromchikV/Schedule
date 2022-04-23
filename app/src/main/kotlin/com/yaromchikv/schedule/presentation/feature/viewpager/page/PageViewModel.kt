package com.yaromchikv.schedule.presentation.feature.viewpager.page

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.repository.ScheduleRepository
import com.yaromchikv.schedule.presentation.common.DEFAULT_ID
import com.yaromchikv.schedule.presentation.common.GROUP_ID_PREFS_KEY
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PageViewModel(
    private val dayIndex: Int,
    private val repository: ScheduleRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _lessonsState = MutableStateFlow<UiState>(UiState.Idle)
    val lessonsState: StateFlow<UiState> = _lessonsState

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
        _lessonsState.value = UiState.Loading
        val groupId = preferences.getInt(GROUP_ID_PREFS_KEY, DEFAULT_ID)

        getLessonsJob?.cancel()
        getLessonsJob = repository.getLessons(dayIndex + 1, groupId)
            .onEach { lessons -> _lessonsState.value = UiState.Ready(lessons) }
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

    sealed class UiState {
        object Idle: UiState()
        data class Ready(val data: List<LessonModel?>): UiState()
        object Loading: UiState()
    }
}