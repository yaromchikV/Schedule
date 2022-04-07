package com.yaromchikv.schedule.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaromchikv.data.api.ScheduleApi
import com.yaromchikv.data.db.ScheduleDao
import com.yaromchikv.data.db.ScheduleDatabase
import com.yaromchikv.data.mapper.LessonMapper
import com.yaromchikv.data.models.views.LessonView
import com.yaromchikv.data.repository.ScheduleRepositoryImpl
import com.yaromchikv.domain.common.DomainMapper
import com.yaromchikv.domain.model.LessonModel
import com.yaromchikv.domain.repository.ScheduleRepository
import com.yaromchikv.schedule.util.DataGenerator
import java.util.concurrent.Executors
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


private const val BASE_URL = "https://iis.bsuir.by/"

val dataModule = module {

    single<Moshi> {
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

    single<ScheduleDatabase> {
        Room.databaseBuilder(
            get(),
            ScheduleDatabase::class.java,
            ScheduleDatabase.DATABASE_NAME
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadExecutor().execute {
                    runBlocking {
                        val dao = (get() as ScheduleDatabase).scheduleDao
                        dao.insertListOfDaysOfWeek(DataGenerator.generateDaysOfWeek())
                        dao.insertListOfBuildings(DataGenerator.generateBuildings())
                        dao.insertListOfClassrooms(DataGenerator.generateClassrooms())
                        dao.insertListOfFaculties(DataGenerator.generateFaculties())
                        dao.insertListOfSpecialities(DataGenerator.generateSpecialities())
                        dao.insertListOfGroups(DataGenerator.generateGroups())
                        dao.insertListOfTeachers(DataGenerator.generateTeachers())
                        dao.insertListOfLessons(DataGenerator.generateLessons())
                    }
                }
            }
        }).build()
    }

    single<ScheduleDao> {
        get<ScheduleDatabase>().scheduleDao
    }

    single<ScheduleRepository> {
        ScheduleRepositoryImpl(api = get(), dao = get())
    }

    factory<DomainMapper<LessonView, LessonModel>> {
        LessonMapper()
    }
}