package com.example.hiffeed.database.MessageAndNews

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.example.hiffeed.database.MessageAndNews.Message.MessageItem
import com.example.hiffeed.database.MessageAndNews.Message.MessageItemDao
import com.example.hiffeed.database.MessageAndNews.News.NewsItem
import com.example.hiffeed.database.MessageAndNews.News.NewsItemDao

@Database(entities = [NewsItem::class, MessageItem::class], version = 14)

@TypeConverters(Converters::class)

abstract class MessageAndNewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsItemDao
    abstract fun messageDao(): MessageItemDao


    companion object {
        private var INSTANCE: MessageAndNewsDatabase? = null
        internal fun getDatabase(context: Context):
                MessageAndNewsDatabase? {
            if (INSTANCE == null) {
                synchronized(MessageAndNewsDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Room.databaseBuilder<MessageAndNewsDatabase
                                    >(
                                context.applicationContext,
                                MessageAndNewsDatabase::class.java,
                                "MessageAndNewsDatabase"
                            ).fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
