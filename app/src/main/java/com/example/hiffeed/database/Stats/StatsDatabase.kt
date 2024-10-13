package com.example.hiffeed.database.Stats

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.example.hiffeed.database.MessageAndNews.Message.MessageItem
import com.example.hiffeed.database.MessageAndNews.Message.MessageItemDao
import com.example.hiffeed.database.MessageAndNews.News.NewsItem
import com.example.hiffeed.database.MessageAndNews.News.NewsItemDao

@Database(entities = [GameItem::class, PlayerItem::class], version = 2)

abstract class StatsDatabase : RoomDatabase() {
    abstract fun gamesDao(): GameItemDao
    abstract fun playerDao(): PlayerItemDao


    companion object {
        private var INSTANCE: StatsDatabase? = null
        internal fun getDatabase(context: Context):
                StatsDatabase? {
            if (INSTANCE == null) {
                synchronized(StatsDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Room.databaseBuilder<StatsDatabase
                                    >(
                                context.applicationContext,
                                StatsDatabase::class.java,
                                "StatsDatabase"
                            ).fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
