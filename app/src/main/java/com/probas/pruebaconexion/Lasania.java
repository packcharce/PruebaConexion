package com.probas.pruebaconexion;

import java.io.Serializable;

public class Lasania implements Serializable {
    private int id;
    private String nombre, ingredientes;
    private double precio;

    public Lasania(int id, String nombre, String ingredientes, double precio) {
        this.id=id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
    }

    public Lasania(Lasania lasania) {
        this(lasania.getId(), lasania.getNombre(), lasania.getIngredientes(), lasania.getPrecio());
    }
    public int getId() {
        return id;
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
