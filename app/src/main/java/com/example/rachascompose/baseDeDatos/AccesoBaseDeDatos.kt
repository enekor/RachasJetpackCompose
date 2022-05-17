package com.example.rachascompose.baseDeDatos

import android.content.Context
import com.example.rachascompose.model.Counter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

object AccesoBaseDeDatos {

    fun createNewCounter(counter: Counter, db: BaseDeDatos) {
        runBlocking {
            db.itemDao().insertarContador(counter)
        }
    }

    fun loadListFromDataBase(db: BaseDeDatos): Flow<List<Counter>> {

        lateinit var listado: Flow<List<Counter>>

        runBlocking {
            listado = db.itemDao().getAllCounters()
        }
        return listado
    }

    fun deleteCounter(contador: Counter, db: BaseDeDatos) {
        runBlocking {
            db.itemDao().deleteCounter(contador)
        }
    }

    fun updateCounter(contador:Int, id:Int, db:BaseDeDatos){
        runBlocking {
            db.itemDao().updateCounter(contador, id)
        }
    }
}