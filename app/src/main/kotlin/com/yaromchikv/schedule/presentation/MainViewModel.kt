package com.yaromchikv.schedule.presentation

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.common.Result
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.repository.ScheduleRepository
import com.yaromchikv.schedule.presentation.common.AccessRights
import com.yaromchikv.schedule.presentation.common.DEFAULT_ID
import com.yaromchikv.schedule.presentation.common.GROUP_ID_PREFS_KEY
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val preferences: SharedPreferences,
    private val repository: ScheduleRepository
) : ViewModel() {

    private val _groups = MutableStateFlow<List<GroupModel>?>(null)
    val groups: StateFlow<List<GroupModel>?> = _groups

    private val _selectedGroup = MutableStateFlow<GroupModel?>(null)
    val selectedGroup: StateFlow<GroupModel?> = _selectedGroup

    var accessRights = AccessRights.NONE

    private var getGroupsJob: Job? = null

    init {
        fetchTeachers()
        fetchClassrooms()
        fetchSpecialities()
        getGroups()
    }

    private fun fetchTeachers() {
        viewModelScope.launch {
            val count = repository.getCountOfTeachers()
            if (count == 0) {
                when (val teachers = repository.getTeachersFromApi()) {
                    is Result.Success -> {
                        teachers.data?.let { repository.addTeachersList(it) }
                    }
                    is Result.Error -> Unit
                }
            }
        }
    }

    private fun fetchClassrooms() {
        viewModelScope.launch {
            val count = repository.getCountOfClassrooms()
            if (count == 0) {
                when (val classrooms = repository.getClassroomsFromApi()) {
                    is Result.Success -> {
                        classrooms.data?.let { repository.addClassroomList(it) }
                    }
                    is Result.Error -> Unit
                }
            }
        }
    }

    private fun fetchSpecialities() {
        viewModelScope.launch {
            val count = repository.getCountOfSpecialities()
            if (count == 0) {
                when (val specialities = repository.getSpecialitiesFromApi()) {
                    is Result.Success -> {
                        specialities.data?.let { repository.addSpecialityList(it) }
                    }
                    is Result.Error -> Unit
                }
            }
        }
    }

    private fun getGroups() {
        getGroupsJob?.cancel()
        getGroupsJob = repository.getGroups()
            .onEach { groups ->
                _groups.value = groups
                updateSelectedGroup()
            }
            .launchIn(viewModelScope)
    }

    private fun updateSelectedGroup() {
        val groupId = preferences.getInt(GROUP_ID_PREFS_KEY, DEFAULT_ID)
        _selectedGroup.value = _groups.value?.find { it.id == groupId }?.apply {
            isSelected = true
        }
    }

    fun selectGroupClick(group: GroupModel) {
        viewModelScope.launch {
            _selectedGroup.value = group
            _groups.value?.map { it.isSelected = it.id == group.id }

            preferences.edit()
                .putInt(GROUP_ID_PREFS_KEY, _selectedGroup.value?.id ?: DEFAULT_ID)
                .apply()
        }
    }
}