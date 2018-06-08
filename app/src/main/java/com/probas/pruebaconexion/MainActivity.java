/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.probas.pruebaconexion.ClasesBasicas.Bebida;
import com.probas.pruebaconexion.ClasesBasicas.Cliente;
import com.probas.pruebaconexion.ClasesBasicas.Ensalada;
import com.probas.pruebaconexion.ClasesBasicas.Hamburguesa;
import com.probas.pruebaconexion.ClasesBasicas.Ingrediente;
import com.probas.pruebaconexion.ClasesBasicas.Lasania;
import com.probas.pruebaconexion.ClasesBasicas.Pasta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;
    public static Context context;
    public static boolean CARGADATOS;


    // --------------------------------------------------
    // FIJOS---------------------------------------------
    // --------------------------------------------------

    public static List<Hamburguesa> listaHamb;
    public static List<Lasania> listaLas;
    public static List<Ensalada> listaEnsa;
    public static List<Bebida> listaBebs;
    public static List<Pasta> listaPasta;
    public static short haAcabadoCargaDatos = 0;


    // --------------------------------------------------
    // INGREDIENTES--------------------------------------
    // --------------------------------------------------

    public static List<Ingrediente> listaIngredientes;

    // --------------------------------------------------
    // CLIENTE ACTIVO------------------------------------
    // --------------------------------------------------
    public static Cliente clienteActivo;


    //as the same button is used for create and update
    //we need to track whether it is an update or create operation
    //for this we have this boolean
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();

        listaHamb = new ArrayList<>();
        listaEnsa = new ArrayList<>();
        listaPasta = new ArrayList<>();
        listaBebs = new ArrayList<>();
        listaLas = new ArrayList<>();
        listaIngredientes = new ArrayList<>();

        CARGADATOS = true;
        cargaDatosAMovil();


    }

    private void cargaDatosAMovil(){
        cargaFijos(getString(R.string.key_hamburguesa_main), 'h');
        cargaFijos(getString(R.string.key_lasania_main), 'l');
        cargaFijos(getString(R.string.key_ensalada_main), 'e');
        cargaFijos(getString(R.string.key_pasta_main), 'p');
        cargaFijos(getString(R.string.kay_bebida_main), 'b');
        cargaIngredientes();
    }

    private void cargaFijos(String nombreTabla, char tipoDato){
        HashMap<String, String> params = new HashMap<>();
        params.put(getString(R.string.key_nombre_tabla_main), nombreTabla);
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_GET_DATOS, params, CODE_POST_REQUEST, tipoDato);
        request.execute();
    }

    private void cargaIngredientes(){
        HashMap<String, String> params = new HashMap<>();
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_GET_INGREDIENTES, params, CODE_POST_REQUEST, 'i');
        request.execute();
    }
}
