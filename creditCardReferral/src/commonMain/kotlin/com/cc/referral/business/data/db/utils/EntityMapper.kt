package com.cc.referral.business.data.db.utils

interface EntityMapper<D, E> {

    fun mapToEntity(data: D): E

    fun mapFromEntity(entity: E): D

}