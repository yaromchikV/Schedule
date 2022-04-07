package com.yaromchikv.data.api

import com.yaromchikv.data.models.dto.ScheduleDto
import retrofit2.Response
import retrofit2.http.GET

interface ScheduleApi {

    @GET("api/v1/schedule?studentGroup=910903")
    suspend fun getSchedule(): Response<ScheduleDto>
}