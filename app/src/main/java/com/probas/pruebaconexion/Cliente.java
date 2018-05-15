package com.probas.pruebaconexion;


public class Cliente {
    private int id;
    private String nombre, apellido, tlfno, calle, portal, piso, puerta, urbanizacion, codigoPostal;
    private Pedido pedido;

    public Cliente() {
    }

    public Cliente(int id, String nombre, String apellido, String tlfno, String calle, String portal, String piso, String puerta, String urbanizacion, String codigoPostal) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tlfno = tlfno;
        this.calle = calle;
        this.portal = portal;
        this.piso = piso;
        this.puerta = puerta;
        this.urbanizacion = urbanizacion;
        this.codigoPostal = codigoPostal;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTlfno() {
        return tlfno;
    }

    public void setTlfno(String tlfno) {
        this.tlfno = tlfno;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public String getUrbanizacion() {
        return urbanizacion;
    }

    public void setUrbanizacion(String urbanizacion) {
        this.urbanizacion = urbanizacion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public int getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
