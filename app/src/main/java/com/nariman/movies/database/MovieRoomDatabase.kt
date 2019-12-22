package com.nariman.movies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nariman.movies.model.MovieDetailsEntity

@Database(entities = arrayOf(MovieDetailsEntity::class), version = 1, exportSchema = false)
abstract class MovieRoomDatabase: RoomDatabase() {

    abstract fun movieDAO(): MovieDAO

    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(context: Context): MovieRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java,
                    "movie_database").build()

                INSTANCE = instance
                return instance
            }
        }
    }
}