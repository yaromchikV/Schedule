package com.yaromchikv.data.models.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "groups",
    foreignKeys = [
        ForeignKey(
            entity = SpecialityEntity::class,
            parentColumns = ["id"],
            childColumns = ["speciality_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class GroupEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "speciality_id") val specialityId: Int
)