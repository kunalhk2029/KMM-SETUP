package com.saver.igv1.business.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.saver.igv1.business.data.db.stories.StoriesDao
import com.saver.igv1.business.data.db.stories.entities.StoriesTrayInfoEntity
import com.saver.igv1.business.data.db.stories.entities.StoryMediaInfoEntity


@Database(
    entities = [StoriesTrayInfoEntity::class,
        StoryMediaInfoEntity::class
    ],
    version = 1,
    autoMigrations = [
//        AutoMigration(from = 2, to = 3)
    ]
)
@TypeConverters(com.saver.igv1.business.data.db.utils.TypeConverters::class)
@ConstructedBy(PrimaryDatabaseConstructor::class)
abstract class PrimaryDatabase() : RoomDatabase() {
    abstract fun storiesDao(): StoriesDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object PrimaryDatabaseConstructor : RoomDatabaseConstructor<PrimaryDatabase> {
    override fun initialize(): PrimaryDatabase
}