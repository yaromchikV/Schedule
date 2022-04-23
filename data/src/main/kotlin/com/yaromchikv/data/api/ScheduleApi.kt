package com.yaromchikv.data.api

import com.yaromchikv.data.models.dto.ClassroomDto
import com.yaromchikv.data.models.dto.ScheduleDto
import com.yaromchikv.data.models.dto.SpecialityDto
import com.yaromchikv.data.models.dto.TeacherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApi {

    @GET("api/v1/employees/all")
    suspend fun getTeachers(): Response<List<TeacherDto>>

    @GET("api/v1/auditories")
    suspend fun getClassrooms(): Response<List<ClassroomDto>>

    @GET("api/v1/specialities")
    suspend fun getSpecialities(): Response<List<SpecialityDto>>

    @GET("api/v1/schedule")
    suspend fun getSchedule(@Query(value = "studentGroup") group: String): Response<ScheduleDto>
}