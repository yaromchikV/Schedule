package com.yaromchikv.data.models.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "specialities",
    foreignKeys = [
        ForeignKey(
            entity = FacultyEntity::class,
            parentColumns = ["id"],
            childColumns = ["faculty_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class SpecialityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "abbrev") val abbrev: String,
    @ColumnInfo(name = "faculty_id") val facultyId: Int,
)