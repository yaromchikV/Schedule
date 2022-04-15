package com.yaromchikv.domain.model

data class GroupModel(
    override val id: Int,
    override val name: String,
    override val speciality: String,
) : GroupInterface