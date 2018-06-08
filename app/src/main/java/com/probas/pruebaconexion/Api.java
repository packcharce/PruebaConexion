/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion;

/**
 * Clase con las url de comunicación con la bd
 */
public class Api {

    private static final String ROOT_URL = MainActivity.context.getResources().getString(R.string.root_url);

    public static final String URL_CREATE_CLIENTE = ROOT_URL + MainActivity.context.getResources().getString(R.string.url_create_cliente);
    public static final String URL_LOGIN = ROOT_URL + MainActivity.context.getResources().getString(R.string.url_login);
    public static final String URL_UPDATE_CLIENTE = ROOT_URL + MainActivity.context.getResources().getString(R.string.url_update_cliente);
    public static final String URL_CREATE_PEDIDO = ROOT_URL + MainActivity.context.getResources().getString(R.string.url_create_pedido);
    public static final String URL_GET_PEDIDO = ROOT_URL + MainActivity.context.getResources().getString(R.string.url_get_pedido);
    public static final String URL_GET_DATOS = ROOT_URL + MainActivity.context.getResources().getString(R.string.url_get_datos);
    public static final String URL_GET_INGREDIENTES = ROOT_URL + MainActivity.context.getResources().getString(R.string.url_get_ingredientes);
}
