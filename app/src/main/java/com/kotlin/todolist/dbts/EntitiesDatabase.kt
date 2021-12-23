package com.kotlin.todolist.dbts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [JobEntities::class],version = 1)
abstract class EntitiesDatabase : RoomDatabase() {

     abstract fun jobDao() : JobDao

    companion object {
        private var INSTANCE: EntitiesDatabase? = null

        fun getInstance(context: Context): EntitiesDatabase? {
            if (INSTANCE == null) {
                synchronized(EntitiesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        EntitiesDatabase::class.java, "user.db")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}