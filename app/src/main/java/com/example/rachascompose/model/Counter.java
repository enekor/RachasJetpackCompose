package com.example.rachascompose.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Counter {

    @PrimaryKey(autoGenerate = true) int id;
    String nombre;
    String imagen;
    int contador;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Counter(String nombre, String imagen, int contador) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.contador = contador;
    }
}
