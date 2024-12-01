package com.cc.referral.business.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.cc.referral.business.data.db.stories.Dao
import com.cc.referral.business.data.db.stories.entities.Entity


@Database(
    entities = [
        Entity::class
    ],
    version = 1,
    autoMigrations = [
//        AutoMigration(from = 2, to = 3)
    ]
)
//@TypeConverters(com.saver.igv1.business.data.db.utils.TypeConverters::class)
@ConstructedBy(PrimaryDatabaseConstructor::class)
abstract class PrimaryDatabase() : RoomDatabase() {
    abstract fun storiesDao(): Dao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object PrimaryDatabaseConstructor : RoomDatabaseConstructor<PrimaryDatabase> {
    override fun initialize(): PrimaryDatabase
}