package com.yaromchikv.data.models.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    "SELECT groups.id, groups.name, specialities.abbrev as abbrev " +
            "FROM groups " +
            "JOIN specialities ON specialities.id = groups.speciality_id"
)
data class GroupView(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "abbrev") val speciality: String
)
