package com.probas.pruebaconexion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pedido {
    private int refCliente;
    private int numPedido;
    private float extra_domicilio, extra_local, extra_recoger;
    private float subtotal, impuesto, total;

    private HashMap<Integer, Pizza> listaPizzas;

    private List<Hamburguesa> listaHamb;
    private List<Lasania> listaLas;
    private List<Ensalada> listaEnsa;
    private List<Bebida> listaBebs;
    private List<Pasta> listaPasta;


    public Pedido() {
        this.refCliente = MainActivity.clienteActivo.getId();
        listaPizzas = new HashMap<>();
        listaHamb = new ArrayList<>();
        listaEnsa = new ArrayList<>();
        listaPasta = new ArrayList<>();
        listaBebs = new ArrayList<>();
        listaLas = new ArrayList<>();
    }

    public HashMap<Integer, Pizza> getListaPizzas() {
        return listaPizzas;
    }

    public List<Hamburguesa> getListaHamb() {
        return listaHamb;
    }

    public void setListaHamb(List<Hamburguesa> listaHamb) {
        listaHamb = listaHamb;
    }

    public List<Lasania> getListaLas() {
        return listaLas;
    }

    public void setListaLas(List<Lasania> listaLas) {
        listaLas = listaLas;
    }

    public  List<Ensalada> getListaEnsa() {
        return listaEnsa;
    }

    public  void setListaEnsa(List<Ensalada> listaEnsa) {
        listaEnsa = listaEnsa;
    }

    public  List<Bebida> getListaBebs() {
        return listaBebs;
    }

    public  void setListaBebs(List<Bebida> listaBebs) {
        listaBebs = listaBebs;
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

    public  List<Pasta> getListaPasta() {
        return listaPasta;
    }

    public  void setListaPasta(List<Pasta> listaPasta) {
        listaPasta = listaPasta;
    }

    public int getRefCliente() {
        return refCliente;
    }

    public int getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public float getExtra_domicilio() {
        return extra_domicilio;
    }

    public void setExtra_domicilio(float extra_domicilio) {
        this.extra_domicilio = extra_domicilio;
    }

    public float getExtra_local() {
        return extra_local;
    }

    public void setExtra_local(float extra_local) {
        this.extra_local = extra_local;
    }

    public float getExtra_recoger() {
        return extra_recoger;
    }

    public void setExtra_recoger(float extra_recoger) {
        this.extra_recoger = extra_recoger;
    }

    public float getSubtotal() {
        return subtotal;
    }

    private void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    private float getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(int impuesto) {
        this.impuesto = impuesto/100;
    }

    public float getTotal() {
        return total;
    }

    private void setTotal() {
        this.total = getSubtotal() + (getSubtotal()*getImpuesto());
    }

    public void calculaTotal(){
        float res=0f;
        for (Pizza a:listaPizzas.values()) {
            res += a.getPrecio();
        }
        for (Hamburguesa h:listaHamb
             ) {
            res += h.getPrecio();
        }
        for (Lasania l:
             listaLas) {
            res += l.getPrecio();
        }
        for (Ensalada e:
             listaEnsa) {
            res+=e.getPrecio();
        }
        for (Bebida b:
             listaBebs) {
            res+=b.getPrecio();
        }
        for (Pasta p:
             listaPasta) {
            res+=p.getPrecio();
        }
        res += getExtra_domicilio();
        res += getExtra_local();
        res += getExtra_recoger();
        setSubtotal(res);
        setTotal();
    }
}
