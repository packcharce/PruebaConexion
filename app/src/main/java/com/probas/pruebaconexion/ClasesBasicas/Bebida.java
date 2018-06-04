/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.ClasesBasicas;

import java.io.Serializable;

public class Bebida implements Serializable {
    private int id;
    private String nombre, ingredientes;
    private double precio;

    public Bebida(int id, String nombre, String ingredientes, double precio) {
        this.id=id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
    }
    public Bebida(Bebida bebida) {
        this(bebida.getId(), bebida.getNombre(), bebida.getIngredientes(), bebida.getPrecio());
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

    public double getPrecio() {
        return precio;
    }
}
