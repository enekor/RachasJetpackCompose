package com.example.rachascompose.baseDeDatos.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rachascompose.model.Counter
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterDao {

    @Insert
    suspend fun insertarContador(contador:Counter)

    @Query("Select * from counter")
    fun getAllCounters(): Flow<List<Counter>>

    @Query("update counter set contador=:contador where id=:id")
    suspend fun updateCounter(contador:Int,id:Int)

    @Delete
    suspend fun deleteCounter(counter:Counter)

}