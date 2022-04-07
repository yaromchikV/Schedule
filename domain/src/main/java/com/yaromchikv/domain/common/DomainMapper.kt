package com.yaromchikv.domain.common

interface DomainMapper<Entity, DomainModel> {
    fun mapFromDomain(domainModel: DomainModel): Entity
    fun mapToDomain(entity: Entity): DomainModel
    fun fromDomainList(list: List<DomainModel>): List<Entity>
    fun toDomainList(list: List<Entity>): List<DomainModel>
}