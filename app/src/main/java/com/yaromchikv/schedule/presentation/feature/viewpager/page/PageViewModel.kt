package com.yaromchikv.schedule.presentation.feature.viewpager.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.usecase.GetListOfLessonsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class PageViewModel(
    private val getListOfLessonsUseCase: GetListOfLessonsUseCase
) : ViewModel() {

    private val _subjects = MutableStateFlow<List<LessonModel>>(emptyList())
    val subjects: StateFlow<List<LessonModel>> = _subjects

    private var getLessonsJob: Job? = null

    init {
        getLessons()

        viewModelScope.launch {
            _subjects.collectLatest {
                Timber.i(_subjects.value.toString())
            }
        }
    }

    private fun getLessons() {
        getLessonsJob?.cancel()
        getLessonsJob = getListOfLessonsUseCase()
            .onEach { lessons -> _subjects.value = lessons }
            .launchIn(viewModelScope)
    }
}