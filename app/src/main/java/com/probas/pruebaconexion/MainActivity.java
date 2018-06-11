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

/**
 * Clase que se encarga de la carga de datos de la bd en el dispositivo
 */
public class MainActivity extends AppCompatActivity {

    //public static final int CODE_GET_REQUEST = 1024;

    // Codigos de peticion a la bd por http
    public static final int CODE_POST_REQUEST = 1025;
    public static Context context;

    // Flag que controla se si esta cargando datos
    public static boolean CARGADATOS;


    // --------------------------------------------------
    // FIJOS---------------------------------------------
    // Datos fijos sacados de la bd, como bebidas
    // o demás productos precocinados
    // --------------------------------------------------

    public static List<Hamburguesa> listaHamb;
    public static List<Lasania> listaLas;
    public static List<Ensalada> listaEnsa;
    public static List<Bebida> listaBebs;
    public static List<Pasta> listaPasta;

    // Flag que controla que se ha acabado de cargar datos si llega a 6
    public static short haAcabadoCargaDatos = 0;


    // --------------------------------------------------
    // INGREDIENTES--------------------------------------
    // Lista de ingredientes disponibles para las pizzas
    // --------------------------------------------------
    public static List<Ingrediente> listaIngredientes;

    // --------------------------------------------------
    // CLIENTE ACTIVO------------------------------------
    // --------------------------------------------------
    public static Cliente clienteActivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        listaHamb = new ArrayList<>();
        listaEnsa = new ArrayList<>();
        listaPasta = new ArrayList<>();
        listaBebs = new ArrayList<>();
        listaLas = new ArrayList<>();
        listaIngredientes = new ArrayList<>();

        CARGADATOS = true;
        cargaDatosAMovil();
    }

    /**
     * Metodo que llama a la bd por http y carga los datos
     * de ésta a la app
     */
    private void cargaDatosAMovil() {
        cargaFijos(getString(R.string.key_hamburguesa_main), 'h');
        cargaFijos(getString(R.string.key_lasania_main), 'l');
        cargaFijos(getString(R.string.key_ensalada_main), 'e');
        cargaFijos(getString(R.string.key_pasta_main), 'p');
        cargaFijos(getString(R.string.kay_bebida_main), 'b');
        cargaIngredientes();
    }

    /**
     * Metodo que carga los productos prefabricados (lasañas, bebidas...)
     * @param nombreTabla   nombre de tabla con los productos
     * @param tipoDato      identificador interno para tratamiento de resultados
     */
    private void cargaFijos(String nombreTabla, char tipoDato) {
        HashMap<String, String> params = new HashMap<>();
        params.put(getString(R.string.key_nombre_tabla_main), nombreTabla);
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_GET_DATOS, params, CODE_POST_REQUEST, tipoDato);
        request.execute();
    }

    /**
     * Metodo que carga los ingredientes de la bd
     */
    private void cargaIngredientes() {
        HashMap<String, String> params = new HashMap<>();
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_GET_INGREDIENTES, params, CODE_POST_REQUEST, 'i');
        request.execute();
    }
}
