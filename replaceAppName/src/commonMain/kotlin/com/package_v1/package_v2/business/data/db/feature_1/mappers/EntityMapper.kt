package com.package_v1.package_v2.business.data.db.feature_1.mappers

import com.package_v1.package_v2.business.data.db.feature_1.entities.ReplaceEntityName
import com.package_v1.package_v2.business.data.db.utils.EntityMapper

class EntityMapper() : EntityMapper<ReplaceEntityName, ReplaceEntityName> {

    override fun mapToEntity(data: ReplaceEntityName): ReplaceEntityName {
        return ReplaceEntityName(
            id = data.id ?: "",
        )
    }

    override fun mapFromEntity(entity: ReplaceEntityName): ReplaceEntityName {
        return ReplaceEntityName(id = entity.id)
    }
}