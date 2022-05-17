package com.example.rachascompose.baseDeDatos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rachascompose.baseDeDatos.dao.CounterDao
import com.example.rachascompose.model.Counter

@Database(entities = [Counter::class], version = 1)
abstract class BaseDeDatos: RoomDatabase(){

    abstract fun itemDao(): CounterDao

    companion object{
        @Volatile
        private var INSTANCE :BaseDeDatos? = null
        fun getDatabase(context: Context): BaseDeDatos{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatos::class.java,
                    "contadores").build()

                INSTANCE = instance
                instance
            }
        }
    }
}