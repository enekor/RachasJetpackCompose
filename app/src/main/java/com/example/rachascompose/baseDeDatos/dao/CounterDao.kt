package com.example.rachascompose.baseDeDatos.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rachascompose.model.Counter

@Dao
interface CounterDao {

    @Insert
    fun insertarContador(contador:Counter)

    @Query("Select * from counter")
    fun getAllCounters():MutableList<Counter>

    @Query("update counter set contador=:contador where id=:id")
    fun updateCounter(contador:Int,id:Int)

    @Delete
    fun deleteCounter(counter:Counter)

}