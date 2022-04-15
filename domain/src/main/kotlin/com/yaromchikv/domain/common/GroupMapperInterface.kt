package com.yaromchikv.domain.common

interface GroupMapperInterface<Entity, Model> {
    fun mapToEntity(model: Model): Entity
    fun mapFromEntity(entity: Entity): Model
    fun mapToEntityList(list: List<Model>): List<Entity>
    fun mapFromEntityList(list: List<Entity>): List<Model>
}