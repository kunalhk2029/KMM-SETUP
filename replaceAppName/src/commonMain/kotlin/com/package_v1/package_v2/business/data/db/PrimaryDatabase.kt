package com.package_v1.package_v2.business.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.package_v1.package_v2.business.data.db.feature_1.ReplaceDaoName
import com.package_v1.package_v2.business.data.db.feature_1.entities.ReplaceEntityName
import com.package_v1.package_v2.business.data.db.utils.TypeConverters


@Database(
    entities = [
        ReplaceEntityName::class
    ],
    version = 1,
    autoMigrations = [
//        AutoMigration(from = 2, to = 3)
    ]
)
//@TypeConverters(TypeConverters::class)
@ConstructedBy(PrimaryDatabaseConstructor::class)
abstract class PrimaryDatabase() : RoomDatabase() {
    abstract fun getReplaceDaoName(): ReplaceDaoName
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object PrimaryDatabaseConstructor : RoomDatabaseConstructor<PrimaryDatabase> {
    override fun initialize(): PrimaryDatabase
}