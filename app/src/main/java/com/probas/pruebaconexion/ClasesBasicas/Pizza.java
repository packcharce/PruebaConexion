/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.ClasesBasicas;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private String nombre;
    private List<Ingrediente> listaIngredientes;
    private float precio;

    public Pizza( String nombre) {
        this.nombre = nombre;
        listaIngredientes = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Ingrediente> getListaIngredientes() {
        return listaIngredientes;
    }

    public void setListaIngredientes(List<Ingrediente> listaIngredientes) {
        this.listaIngredientes = listaIngredientes;
    }

    public float getPrecio() {
        return precio;
    }

    private void setPrecio() {
        float aux=0;
        for (int i=0; i<listaIngredientes.size(); i++)
            aux += listaIngredientes.get(i).getPrecio();
        float precioFijo = 5.0f;
        this.precio = aux+ precioFijo;
    }

    public void quitaPrecio(){
        precio=0;
    }

    public void agregaIngrediente(Ingrediente i){
        listaIngredientes.add(i);
        setPrecio();
    }
    public void quitaIngrediente(String nombre){
        for (int i=0; i<listaIngredientes.size(); i++){
            if(listaIngredientes.get(i).getNombre().equals(nombre))
                listaIngredientes.remove(i);
        }
        setPrecio();
    }
}
