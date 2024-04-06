package com.game.northFrontier.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Location::class, LocalRuler::class, Squad::class, YourStats::class, EnemyStats::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract val locationDao: LocationsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this){
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_db"
            ).build().also {
                INSTANCE = it
            }
            }
        }


    }
}
