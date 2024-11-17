package com.saver.igv1.business.data.db.utils

interface EntityMapper<D, E> {

    fun mapToEntity(data: D): E

    fun mapFromEntity(entity: E): D

}