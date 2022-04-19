package com.yaromchikv.schedule.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaromchikv.data.api.ScheduleApi
import com.yaromchikv.data.db.ScheduleDatabase
import com.yaromchikv.data.mapper.ClassroomMapper
import com.yaromchikv.data.mapper.DayOfWeekMapper
import com.yaromchikv.data.mapper.GroupMapper
import com.yaromchikv.data.mapper.LessonMapper
import com.yaromchikv.data.mapper.LessonTypeMapper
import com.yaromchikv.data.mapper.TeacherMapper
import com.yaromchikv.data.repository.ScheduleRepositoryImpl
import com.yaromchikv.domain.repository.ScheduleRepository
import com.yaromchikv.schedule.util.DataGenerator
import java.util.concurrent.Executors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


private const val BASE_URL = "https://iis.bsuir.by/"

val dataModule = module {

    factory<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single<ScheduleApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create(ScheduleApi::class.java)
    }

    single {
        Room.databaseBuilder(
            get(),
            ScheduleDatabase::class.java,
            ScheduleDatabase.DATABASE_NAME
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadExecutor().execute {
                    runBlocking(Dispatchers.IO) {
                        val dao = (get() as ScheduleDatabase).scheduleDao
                        dao.insertListOfRoles(DataGenerator.generateRoles())
                        dao.insertListOfUsers(DataGenerator.generateUsers())
                        dao.insertListOfDaysOfWeek(DataGenerator.generateDaysOfWeek())
                        dao.insertListOfBuildings(DataGenerator.generateBuildings())
                        dao.insertListOfClassrooms(DataGenerator.generateClassrooms())
                        dao.insertListOfFaculties(DataGenerator.generateFaculties())
                        dao.insertListOfSpecialities(DataGenerator.generateSpecialities())
                        dao.insertListOfGroups(DataGenerator.generateGroups())
                        dao.insertListOfTeachers(DataGenerator.generateTeachers())
                        dao.insertListOfLessonTypes(DataGenerator.generateLessonTypes())
                        dao.insertListOfLessons(DataGenerator.generateLessons())
                    }
                }
            }
        }).build()
    }

    factory { get<ScheduleDatabase>().scheduleDao }

    single<ScheduleRepository> {
        ScheduleRepositoryImpl(
            api = get(),
            dao = get(),
            lessonMapper = get(),
            groupMapper = get(),
            dayOfWeekMapper = get(),
            teacherMapper = get(),
            lessonTypeMapper = get(),
            classroomMapper = get()
        )
    }

    factory { LessonMapper() }
    factory { GroupMapper() }
    factory { DayOfWeekMapper() }
    factory { TeacherMapper() }
    factory { LessonTypeMapper() }
    factory { ClassroomMapper() }
}