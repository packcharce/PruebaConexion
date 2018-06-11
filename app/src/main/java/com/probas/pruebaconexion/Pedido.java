/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion;

import com.probas.pruebaconexion.ClasesBasicas.Bebida;
import com.probas.pruebaconexion.ClasesBasicas.Ensalada;
import com.probas.pruebaconexion.ClasesBasicas.Hamburguesa;
import com.probas.pruebaconexion.ClasesBasicas.Lasania;
import com.probas.pruebaconexion.ClasesBasicas.Pasta;
import com.probas.pruebaconexion.ClasesBasicas.Pizza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pedido {

    // Atributos del pedido
    private static int numPedido;
    private float extra_domicilio, extra_local, extra_recoger;
    private float subtotal, impuesto, total;

    private final HashMap<Integer, Pizza> listaPizzas;

    private final List<Hamburguesa> listaHamb;
    private final List<Lasania> listaLas;
    private final List<Ensalada> listaEnsa;
    private final List<Bebida> listaBebs;
    private final List<Pasta> listaPasta;


    // Inicializa las listas del pedido
    Pedido() {
        numPedido++;
        listaPizzas = new HashMap<>();
        listaHamb = new ArrayList<>();
        listaEnsa = new ArrayList<>();
        listaPasta = new ArrayList<>();
        listaBebs = new ArrayList<>();
        listaLas = new ArrayList<>();
    }

    // Getters y setters
    public void setExtra_domicilio(float extra_domicilio) {
        this.extra_domicilio = extra_domicilio;
    }

    public void setExtra_local(float extra_local) {
        this.extra_local = extra_local;
    }

    public void setExtra_recoger(float extra_recoger) {
        this.extra_recoger = extra_recoger;
    }

    public HashMap<Integer, Pizza> getListaPizzas() {
        return listaPizzas;
    }

    public List<Hamburguesa> getListaHamb() {
        return listaHamb;
    }

    public List<Lasania> getListaLas() {
        return listaLas;
    }

    public  List<Ensalada> getListaEnsa() {
        return listaEnsa;
    }

    public  List<Bebida> getListaBebs() {
        return listaBebs;
    }

    public  List<Pasta> getListaPasta() {
        return listaPasta;
    }

    public int getNumPedido() {
        return numPedido;
    }

    public float getExtra_domicilio() {
        return extra_domicilio;
    }

    public float getExtra_local() {
        return extra_local;
    }

    public float getExtra_recoger() {
        return extra_recoger;
    }

    public float getSubtotal() {
        return subtotal;
    }

    private void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getImpuesto() {
        return impuesto;
    }

    private void setImpuesto(float impuesto) {
        this.impuesto = impuesto;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total){
        this.total = total;
    }


    // Metodos auxiliares para quitar y/o poner hamburguesas, pasta... o calcular el total
    private void setTotal() {
        this.total = getSubtotal() + getImpuesto();
    }

    public void quitaBebida(String nombre){
        for(int i=0; i<listaBebs.size(); i++){
            if (listaBebs.get(i).getNombre().equals(nombre)){
                listaBebs.remove(i);
                break;
            }
        }
    }

    public void quitaHamb(String nombre){
        for(int i=0; i<listaHamb.size(); i++){
            if (listaHamb.get(i).getNombre().equals(nombre)){
                listaHamb.remove(i);
                break;
            }
        }
    }

    public void quitaLas(String nombre){
        for(int i=0; i<listaLas.size(); i++){
            if (listaLas.get(i).getNombre().equals(nombre)){
                listaLas.remove(i);
                break;
            }
        }
    }

    public void quitaEnsa(String nombre){
        for(int i=0; i<listaEnsa.size(); i++){
            if (getListaEnsa().get(i).getNombre().equals(nombre)){
                listaEnsa.remove(i);
                break;
            }
        }
    }

    public void quitaPasta(String nombre){
        for(int i=0; i<listaPasta.size(); i++){
            if (listaPasta.get(i).getNombre().equals(nombre)){
                listaPasta.remove(i);
                break;
            }
        }
    }

    // Recorre todas las listas para calcular el total dle pedido
    // Añadiendo los extras por local, recoger o domicilio
    public void calculaTotal() {
        float res = 0f;
        for (Pizza a : listaPizzas.values()) {
            res += a.getPrecio();
        }
        for (Hamburguesa h : listaHamb
                ) {
            res += h.getPrecio();
        }
        for (Lasania l :
                listaLas) {
            res += l.getPrecio();
        }
        for (Ensalada e :
                listaEnsa) {
            res += e.getPrecio();
        }
        for (Bebida b :
                listaBebs) {
            res += b.getPrecio();
        }
        for (Pasta p :
                listaPasta) {
            res += p.getPrecio();
        }
        res += getExtra_domicilio();
        res += getExtra_local();
        res += getExtra_recoger();
        setSubtotal(res);
        setImpuesto(res * 0.1f);
        setTotal();
    }


}
