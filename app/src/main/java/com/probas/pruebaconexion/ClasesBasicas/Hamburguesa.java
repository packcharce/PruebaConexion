/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.ClasesBasicas;

import java.io.Serializable;

public class Hamburguesa implements Serializable {
    private final int id;
    private String nombre, ingredientes;
    private double precio;

    public Hamburguesa(int id, String nombre, String ingredientes, double precio) {
        this.id=id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
    }

    public Hamburguesa(Hamburguesa hamburguesa) {
        this(hamburguesa.getId(), hamburguesa.getNombre(), hamburguesa.getIngredientes(), hamburguesa.getPrecio());
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

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}