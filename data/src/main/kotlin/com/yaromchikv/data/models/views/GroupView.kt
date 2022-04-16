package com.yaromchikv.data.models.views

import androidx.room.ColumnInfo

class GroupView(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "speciality") val speciality: String
)
