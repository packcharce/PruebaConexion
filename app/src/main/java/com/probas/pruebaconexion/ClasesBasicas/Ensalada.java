/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.ClasesBasicas;

import java.io.Serializable;

public class Ensalada implements Serializable {
    private final int id;
    private String nombre, ingredientes;
    private final double precio;

    public Ensalada(int id, String nombre, String ingredientes, double precio) {
        this.id=id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
    }

    public Ensalada(Ensalada ensalada) {
        this(ensalada.getId(), ensalada.getNombre(), ensalada.getIngredientes(), ensalada.getPrecio());
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

    private int getId() {
        return id;
    }
}
