package com.example.androimocktest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androimocktest.data.local.dao.ItemsDao
import com.example.androimocktest.data.local.entity.Items

@Database(entities = [Items::class],version = 1, exportSchema = true)
abstract class ItemsDatabase : RoomDatabase() {

    abstract fun itemsDao(): ItemsDao

    companion object {
        private const val DB_NAME = "items_db"


        @Volatile
        private var INSTANCE: ItemsDatabase? = null
        fun getInstance(context: Context): ItemsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemsDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}