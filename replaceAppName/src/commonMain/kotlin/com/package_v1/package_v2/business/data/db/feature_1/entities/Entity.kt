package com.package_v1.package_v2.business.data.db.feature_1.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "entity")
data class ReplaceEntityName(
    @PrimaryKey(autoGenerate = false) val id: String
)