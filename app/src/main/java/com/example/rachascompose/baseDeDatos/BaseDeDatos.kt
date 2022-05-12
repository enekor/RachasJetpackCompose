package com.example.rachascompose.baseDeDatos

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rachascompose.baseDeDatos.dao.CounterDao
import com.example.rachascompose.model.Counter

@Database(entities = [Counter::class], version = 1)
abstract class BaseDeDatos: RoomDatabase(){

    abstract fun itemDao(): CounterDao
}