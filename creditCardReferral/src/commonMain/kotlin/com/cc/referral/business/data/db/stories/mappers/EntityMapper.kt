package com.cc.referral.business.data.db.stories.mappers

import com.cc.referral.business.data.db.stories.entities.Entity
import com.cc.referral.business.data.db.utils.EntityMapper

class EntityMapper() : EntityMapper<Entity, Entity> {

    override fun mapToEntity(data: Entity): Entity {
        return Entity(
            id = data.id ?: "",
        )
    }

    override fun mapFromEntity(entity: Entity): Entity {
        return Entity(id = entity.id)
    }
}