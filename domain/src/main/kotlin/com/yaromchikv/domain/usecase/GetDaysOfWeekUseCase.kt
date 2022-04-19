package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.model.DayOfWeekModel
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetDaysOfWeekUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(): Flow<List<DayOfWeekModel>> {
        return repository.getDaysOfWeek()
    }
}