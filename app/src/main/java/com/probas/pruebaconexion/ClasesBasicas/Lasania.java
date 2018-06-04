/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.ClasesBasicas;

import java.io.Serializable;

public class Lasania implements Serializable {
    private final int id;
    private String nombre, ingredientes;
    private final double precio;

    public Lasania(int id, String nombre, String ingredientes, double precio) {
        this.id=id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
    }

    public Lasania(Lasania lasania) {
        this(lasania.getId(), lasania.getNombre(), lasania.getIngredientes(), lasania.getPrecio());
    }
    private int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public double getPrecio() {
        return precio;
    }
}
