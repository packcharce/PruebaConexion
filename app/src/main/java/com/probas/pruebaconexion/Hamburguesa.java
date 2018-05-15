package com.probas.pruebaconexion;

import java.io.Serializable;

public class Hamburguesa implements Serializable {
    private String nombre, ingredientes;
    private double precio;

    public Hamburguesa(String nombre, String ingredientes, double precio) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
    }

    public Hamburguesa(Hamburguesa hamburguesa) {
        this(hamburguesa.getNombre(), hamburguesa.getIngredientes(), hamburguesa.getPrecio());
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

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
