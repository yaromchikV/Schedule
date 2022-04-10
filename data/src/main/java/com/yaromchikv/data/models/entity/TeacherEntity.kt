package com.yaromchikv.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teachers")
data class TeacherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "surname") val surname: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "patronymic") val patronymic: String,
    @ColumnInfo(name = "rank") val rank: String
)