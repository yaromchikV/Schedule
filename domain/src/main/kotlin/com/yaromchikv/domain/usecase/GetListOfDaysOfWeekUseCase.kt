package com.yaromchikv.domain.usecase

import com.yaromchikv.domain.model.DayOfWeekInterface
import com.yaromchikv.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetListOfDaysOfWeekUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(): Flow<List<DayOfWeekInterface>> {
        return repository.getDaysOfWeek()
    }
}