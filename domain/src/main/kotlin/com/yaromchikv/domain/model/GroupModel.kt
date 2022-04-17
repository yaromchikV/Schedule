package com.yaromchikv.domain.model

data class GroupModel(
    val id: Int,
    val name: String,
    val speciality: String,

    var isSelected: Boolean = false
)