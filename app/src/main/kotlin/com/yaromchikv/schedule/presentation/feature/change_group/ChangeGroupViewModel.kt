package com.yaromchikv.schedule.presentation.feature.change_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.domain.common.Result
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class ChangeGroupViewModel(
    private val repository: ScheduleRepository
) : ViewModel() {

    private val _addGroupState = MutableStateFlow<UiState>(UiState.Idle)
    val addGroupState: StateFlow<UiState> = _addGroupState

    private var checkGroupJob: Job? = null
    private var getDataJob: Job? = null

    fun addGroupClick(groupName: String) {
        viewModelScope.launch {
            _addGroupState.value = UiState.Loading

            when (val response = repository.getScheduleFromApi(groupName)) {
                is Result.Success -> {
                    Timber.i("response: ${response.data}")

                    getDataJob?.cancel()
                    getDataJob = combine(
                        repository.getDaysOfWeek(),
                        repository.getLessonTypes(),
                        repository.getSpecialities(),
                        repository.getClassrooms()
                    ) { days, lessonTypes, specialities, classrooms ->
                        val lessonsList = mutableListOf<LessonModel>()

                        var subject: String
                        var typeId: Int?
                        var type: String?
                        var note: String?
                        var startTime: String
                        var endTime: String
                        var dayOfWeekId: Int?
                        var dayOfWeek: String?
                        var weeks: List<Int>
                        var subgroup: Int
                        var teacherId: Int?
                        var classroomId: Int?
                        var groupId: Int
                        var specialityId: Int
                        var specialityName: String?

                        response.data?.let { schedule ->
                            groupId = schedule.group.id
                            specialityId = schedule.group.specialityId
                            specialityName =
                                specialities.find { it.id == specialityId }?.name
                            schedule.lisOfDays.forEach { day ->
                                dayOfWeek = day.weekDay
                                dayOfWeekId = days.find { it.name == day.weekDay }?.id
                                day.listOfLessons.forEach { lesson ->
                                    subject = lesson.subject
                                    note = lesson.note
                                    startTime = lesson.startTime
                                    endTime = lesson.endTime
                                    subgroup = lesson.subgroup
                                    teacherId =
                                        if (lesson.teacher.isNotEmpty()) lesson.teacher[0].id else null
                                    weeks = lesson.weeks
                                    type = lesson.lessonType
                                    typeId =
                                        lessonTypes.find { it.type == lesson.lessonType }?.id
                                    if (lesson.classroom.isNotEmpty()) {
                                        val classroomName = lesson.classroom[0]
                                        var classroomNumber = ""
                                        var buildingId: Int? = null
                                        for (i in classroomName.indices) {
                                            if (classroomName[i] != '-') {
                                                classroomNumber += classroomName[i]
                                            } else {
                                                buildingId =
                                                    classroomName[i + 1].digitToIntOrNull()
                                                break
                                            }
                                        }
                                        classroomId = classrooms.find {
                                            it.number == classroomNumber && it.buildingId == buildingId
                                        }?.id
                                    } else classroomId = null

                                    lessonsList.add(
                                        LessonModel(
                                            subject = subject,
                                            typeId = typeId,
                                            type = type,
                                            note = note,
                                            startTime = startTime,
                                            endTime = endTime,
                                            dayOfWeekId = dayOfWeekId,
                                            dayOfWeek = dayOfWeek,
                                            weeks = weeks,
                                            subgroup = subgroup,
                                            teacherId = teacherId,
                                            classroomId = classroomId,
                                            groupId = groupId
                                        )
                                    )
                                }
                            }
                            val groupModel = GroupModel(
                                id = groupId,
                                name = groupName,
                                specialityId = specialityId,
                                speciality = specialityName ?: "",
                            )

                            Timber.i("group: $groupModel")
                            Timber.i("lessons: $lessonsList")

                            checkGroupJob?.cancel()
                            checkGroupJob = repository.getCountOfGroupByName(groupName)
                                .onEach { groupCount ->
                                    if (groupCount == 0) {
                                        repository.addGroup(groupModel)
                                    } else {
                                        repository.deleteLessonByGroupId(groupModel.id)
                                    }
                                    repository.addLessonList(lessonsList)
                                    _addGroupState.value = UiState.Ready
                                }.launchIn(viewModelScope)

                        }
                    }.launchIn(viewModelScope)
                }
                is Result.Error -> {
                    Timber.i("response: ${response.message}")
                    _addGroupState.value =
                        UiState.Error("Что-то пошло не так... Возможно, такой группы не существует")
                }
            }
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Ready : UiState()
        object Loading : UiState()
        data class Error(val message: String?) : UiState()
    }
}