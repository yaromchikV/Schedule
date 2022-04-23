package com.yaromchikv.data.models.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    "SELECT groups.id, groups.name, speciality_id, specialities.name AS speciality " +
            "FROM groups " +
            "JOIN specialities ON specialities.id = speciality_id"
)
class GroupView(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "speciality_id") val specialityId: Int,
    @ColumnInfo(name = "speciality") val speciality: String
)
