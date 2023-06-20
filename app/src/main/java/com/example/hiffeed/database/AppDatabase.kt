package com.example.hiffeed.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsItem::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): NewsItemDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        internal fun getDatabase(context: Context):
                AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Room.databaseBuilder<AppDatabase
                                    >(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "newsTable"
                            ).fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
