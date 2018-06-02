package com.probas.pruebaconexion;

public class Api {

    private static final String ROOT_URL = "http://192.168.1.11/TFGApi/v1/api.php?apicall=";

    public static final String URL_CREATE_CLIENTE = ROOT_URL + "createCliente";
    public static final String URL_LOGIN = ROOT_URL + "login";
    public static final String URL_UPDATE_CLIENTE = ROOT_URL + "updateCliente";
    public static final String URL_CREATE_PEDIDO = ROOT_URL + "createPedido";
    public static final String URL_GET_PEDIDO = ROOT_URL + "getPedido";
    public static final String URL_GET_DATOS = ROOT_URL + "cargaDatos";
    public static final String URL_GET_INGREDIENTES = ROOT_URL + "cargaIngredientes";
}
