package com.cc.referral.business.data.db.stories.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "entity")
data class Entity(
    @PrimaryKey(autoGenerate = false) val id: String
)