package com.yaromchikv.data.models.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    "SELECT classrooms.id, classrooms.number, building_id, buildings.name AS building_name " +
            "FROM classrooms " +
            "JOIN buildings ON building_id = buildings.id"
)
class ClassroomView(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "building_id") val buildingId: Int,
    @ColumnInfo(name = "building_name") val buildingName: String,
)
