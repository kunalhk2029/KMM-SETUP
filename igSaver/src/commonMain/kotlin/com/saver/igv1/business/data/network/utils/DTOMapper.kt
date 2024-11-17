package com.saver.igv1.business.data.network.utils

interface DTOMapper<D, R> {

    fun mapToDomain(dto: R?): D?

    fun mapToDomainList(list: List<R>?): List<D?>? {
        return list?.map { mapToDomain(it) }
    }

}