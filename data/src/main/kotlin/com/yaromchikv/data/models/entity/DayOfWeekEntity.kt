package com.yaromchikv.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yaromchikv.domain.model.DayOfWeekModel

@Entity(tableName = "days")
data class DayOfWeekEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "abbrev") val abbrev: String
)
