package com.yaromchikv.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yaromchikv.domain.model.DayOfWeekInterface

@Entity(tableName = "days")
data class DayOfWeekEntity(
    @PrimaryKey(autoGenerate = true) override val id: Int? = null,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "abbrev") override val abbrev: String
) : DayOfWeekInterface
