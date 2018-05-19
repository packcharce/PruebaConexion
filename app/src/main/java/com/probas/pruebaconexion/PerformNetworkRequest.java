package com.probas.pruebaconexion;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.probas.pruebaconexion.fragments.Mis_pedidos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

    //the url where we need to send the request
    String url;

    //the parameters
    HashMap<String, String> params;

    //the request code to define whether it is a GET or POST
    int requestCode;

    char tipoDato;

    //constructor to initialize values
    public PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode, char tipoDato) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
        this.tipoDato = tipoDato;
    }

    //when the task started displaying a progressbar
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    //this method will give the response from the request
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject object = new JSONObject(s);
            if (!object.getBoolean("error")) {
                //Toast.makeText(MainActivity.context, object.getString("message"), Toast.LENGTH_SHORT).show();

                if(MainActivity.CARGADATOS)
                    cargaData(object.getJSONArray("datos"), tipoDato);
                if(Login.LOGIN)
                    if(object.getJSONArray("datos").length() != 0)
                        login(object.getJSONArray("datos"));
                    else
                    {
                        Login.LOGIN=false;
                        Toast.makeText(MainActivity.context, "Error de login", Toast.LENGTH_SHORT).show();
                    }
                if(Mis_pedidos.PEDIDOS)
                    if(object.getJSONArray("datos").length() != 0) {
                        Mis_pedidos.PEDIDOS = false;
                        misPedidos(object.getJSONArray("datos"));

                    }

            }else{
                if(Integer.parseInt(object.getString("numError")) == 1062)
                    Toast.makeText(MainActivity.context, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), object.getString("numError"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //the network operation will be performed in background
    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == MainActivity.CODE_POST_REQUEST)
            return requestHandler.sendPostRequest(url, params);


        if (requestCode == MainActivity.CODE_GET_REQUEST)
            return requestHandler.sendGetRequest(url);

        return null;
    }

    private void cargaData(JSONArray datos, char tipoDato) throws JSONException {

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < datos.length(); i++) {

            JSONObject obj = datos.getJSONObject(i);

            switch (tipoDato){
                case 'h':
                    MainActivity.listaHamb.add(new Hamburguesa(
                            obj.getString("nombre"),
                            obj.getString("ingredientes"),
                            obj.getDouble("precio")
                    ));
                break;
                case 'e':
                    MainActivity.listaEnsa.add(new Ensalada(
                            obj.getString("nombre"),
                            obj.getString("ingredientes"),
                            obj.getDouble("precio")
                    ));
                break;
                case 'l':
                    MainActivity.listaLas.add(new Lasania(
                            obj.getString("nombre"),
                            obj.getString("ingredientes"),
                            obj.getDouble("precio")
                    ));
                    break;
                case 'b':
                    MainActivity.listaBebs.add(new Bebida(
                            obj.getString("nombre"),
                            obj.getString("ingredientes"),
                            obj.getDouble("precio")
                    ));
                    break;
                case 'p':
                    MainActivity.listaPasta.add(new Pasta(
                            obj.getString("nombre"),
                            obj.getString("ingredientes"),
                            obj.getDouble("precio")
                    ));
                    break;
                case 'i':
                    MainActivity.listaIngredientes.add(new Ingrediente(
                            obj.getString("tipo"),
                            obj.getString("nombre"),
                            obj.getInt("stock"),
                            obj.getDouble("precio")
                    ));
                    break;
            }
        }
        MainActivity.haAcabadoCargaDatos++;
        if(MainActivity.haAcabadoCargaDatos >= 6) {
            Toast.makeText(MainActivity.context, "Datos Cargados" + MainActivity.haAcabadoCargaDatos, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.context, MenuLoginReg.class);
            MainActivity.context.startActivity(i);
            MainActivity.CARGADATOS=false;

        }
    }

    private void login(JSONArray datos) throws JSONException {
        JSONObject obj = datos.getJSONObject(0);

        MainActivity.clienteActivo = new Cliente(
                obj.getInt("id"),
                obj.getString("nombre"),
                obj.getString("apellido"),
                obj.getString("tlfno"),
                obj.getString("calle"),
                obj.getString("portal"),
                obj.getString("piso"),
                obj.getString("puerta"),
                obj.getString("urbanizacion"),
                obj.getString("codigoPostal")
        );
        Toast.makeText(MainActivity.context, "Login Correcto", Toast.LENGTH_SHORT).show();
        Login.LOGIN = false;
        Intent i = new Intent(Login.context, menu_principal.class);
        Login.context.startActivity(i);
    }

    private void misPedidos(JSONArray datos) throws JSONException{
        menu_principal.cargaDatos(datos);
    }
}
