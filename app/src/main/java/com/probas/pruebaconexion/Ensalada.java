package com.probas.pruebaconexion;

import java.io.Serializable;

public class Ensalada implements Serializable {
    private String nombre, ingredientes;
    private double precio;

    public Ensalada(String nombre, String ingredientes, double precio) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
    }

    public Ensalada(Ensalada ensalada) {
        this(ensalada.getNombre(), ensalada.getIngredientes(), ensalada.getPrecio());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public double getPrecio() {
        return precio;
    }
}
