package com.yaromchikv.data.models.views

import androidx.room.ColumnInfo
import com.yaromchikv.domain.model.GroupInterface

class GroupView(
    @ColumnInfo(name = "id") override val id: Int,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "speciality") override val speciality: String
): GroupInterface
