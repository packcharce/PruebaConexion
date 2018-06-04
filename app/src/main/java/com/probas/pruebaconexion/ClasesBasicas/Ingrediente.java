/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.ClasesBasicas;


import java.io.Serializable;

public class Ingrediente implements Serializable{
    private int id;
    private static short numeroIngrediente=0;
    private String tipo, nombre;
    private static int stock;
    private double precio;

    // 0: entera
    // 1: primera mitad
    // 2: segunda mitad
    private byte mitad;

    public Ingrediente(int id, String tipo, String nombre, int stock, double precio) {
        this.id=id;
        Ingrediente.numeroIngrediente++;
        this.tipo = tipo;
        this.nombre = nombre;
        Ingrediente.stock--;
        this.precio = precio;
    }

    public Ingrediente(Ingrediente ingrediente) {
        this(ingrediente.getId(), ingrediente.getTipo(), ingrediente.getNombre(), ingrediente.getStock(), ingrediente.getPrecio());
    }
    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        Ingrediente.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public static short getNumeroIngrediente() {
        return numeroIngrediente;
    }

    public byte getMitad() {
        return mitad;
    }

    public void setMitad(byte mitad) {
        this.mitad = mitad;
    }
}
