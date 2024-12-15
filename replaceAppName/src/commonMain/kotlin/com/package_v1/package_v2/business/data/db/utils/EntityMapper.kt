package com.package_v1.package_v2.business.data.db.utils

interface EntityMapper<D, E> {

    fun mapToEntity(data: D): E

    fun mapFromEntity(entity: E): D

}