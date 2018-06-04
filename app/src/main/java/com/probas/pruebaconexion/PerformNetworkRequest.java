package com.probas.pruebaconexion;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.probas.pruebaconexion.ClasesBasicas.Bebida;
import com.probas.pruebaconexion.ClasesBasicas.Cliente;
import com.probas.pruebaconexion.ClasesBasicas.Ensalada;
import com.probas.pruebaconexion.ClasesBasicas.Hamburguesa;
import com.probas.pruebaconexion.ClasesBasicas.Ingrediente;
import com.probas.pruebaconexion.ClasesBasicas.Lasania;
import com.probas.pruebaconexion.ClasesBasicas.Pasta;
import com.probas.pruebaconexion.fragments.Mis_pedidos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

    //the url where we need to send the request
    private String url;

    //the parameters
    private HashMap<String, String> params;

    //the request code to define whether it is a GET or POST
    private int requestCode;

    private char tipoDato;

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
            if (object.length() != 0) {
                if (!object.getBoolean(MainActivity.context.getResources().getString(R.string.key_error))) {
                    //Toast.makeText(MainActivity.context, object.getString("message"), Toast.LENGTH_SHORT).show();

                    if (MainActivity.CARGADATOS)
                        cargaData(object.getJSONArray(MainActivity.context.getResources().getString(R.string.key_datos_pnreq)), tipoDato);
                    if (Login.LOGIN)
                        if (object.getJSONArray(MainActivity.context.getResources().getString(R.string.key_datos_pnreq)).length() != 0)
                            login(object.getJSONArray(MainActivity.context.getResources().getString(R.string.key_datos_pnreq)));
                        else {
                            Login.LOGIN = false;
                            Toast.makeText(MainActivity.context, MainActivity.context.getResources().getString(R.string.error_login_pnreq), Toast.LENGTH_SHORT).show();
                        }
                    if (Mis_pedidos.PEDIDOS)
                        if (object.getJSONArray(MainActivity.context.getResources().getString(R.string.key_datos_pnreq)).length() != 0) {
                            Mis_pedidos.PEDIDOS = false;
                            Menu_principal.cargaDatos(object.getJSONArray(MainActivity.context.getResources().getString(R.string.key_datos_pnreq)));
                        }

                } else {
                    if (Integer.parseInt(object.getString(MainActivity.context.getResources().getString(R.string.key_num_error_pnreq))) == 1062)
                        Toast.makeText(MainActivity.context, MainActivity.context.getResources().getString(R.string.error_user_existente_pnreq), Toast.LENGTH_SHORT).show();
                }
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
        JSONObject obj;
        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < datos.length(); i++) {

            obj = datos.getJSONObject(i);

            switch (tipoDato) {
                case 'h':
                    MainActivity.listaHamb.add(new Hamburguesa(
                            obj.getInt(MainActivity.context.getResources().getString(R.string.key_id)),
                            obj.getString(MainActivity.context.getResources().getString(R.string.key_nombre)),
                            obj.getString(MainActivity.context.getResources().getString(R.string.key_ingredientes_pnreq)),
                            obj.getDouble(MainActivity.context.getResources().getString(R.string.key_precio_pnreq))
                    ));
                    break;
                case 'e':
                    MainActivity.listaEnsa.add(new Ensalada(
                            obj.getInt(MainActivity.context.getResources().getString(R.string.key_id)),
                            obj.getString(MainActivity.context.getResources().getString(R.string.key_nombre)),
                            obj.getString(MainActivity.context.getResources().getString(R.string.key_ingredientes_pnreq)),
                            obj.getDouble(MainActivity.context.getResources().getString(R.string.key_precio_pnreq))
                    ));
                    break;
                case 'l':
                    MainActivity.listaLas.add(new Lasania(
                            obj.getInt(MainActivity.context.getResources().getString(R.string.key_id)),
                            obj.getString(MainActivity.context.getResources().getString(R.string.key_nombre)),
                            obj.getString(MainActivity.context.getResources().getString(R.string.key_ingredientes_pnreq)),
                            obj.getDouble(MainActivity.context.getResources().getString(R.string.key_precio_pnreq))
                    ));
                    break;
                case 'b':
                    MainActivity.listaBebs.add(new Bebida(
                            obj.getInt(MainActivity.context.getResources().getString(R.string.key_id)),
                            obj.getString(MainActivity.context.getResources().getString(R.string.key_nombre)),
                            obj.getString(MainActivity.context.getResources().getString(R.string.key_ingredientes_pnreq)),
                            obj.getDouble(MainActivity.context.getResources().getString(R.string.key_precio_pnreq))
                    ));
                    break;
                case 'p':
                    MainActivity.listaPasta.add(new Pasta(
                            obj.getInt(MainActivity.context.getResources().getString(R.string.key_id)),
                            obj.getString(MainActivity.context.getResources().getString(R.string.key_nombre)),
                            obj.getString(MainActivity.context.getResources().getString(R.string.key_ingredientes_pnreq)),
                            obj.getDouble(MainActivity.context.getResources().getString(R.string.key_precio_pnreq))
                    ));
                    break;
                case 'i':
                    MainActivity.listaIngredientes.add(new Ingrediente(
                            obj.getInt(MainActivity.context.getResources().getString(R.string.key_id)),
                            obj.getString(MainActivity.context.getResources().getString(R.string.key_tipo_pnreq)),
                            obj.getString(MainActivity.context.getResources().getString(R.string.key_nombre)),
                            obj.getInt(MainActivity.context.getResources().getString(R.string.key_stock_pnreq)),
                            obj.getDouble(MainActivity.context.getResources().getString(R.string.key_precio_pnreq))
                    ));
                    break;
            }
        }
        MainActivity.haAcabadoCargaDatos++;
        if (MainActivity.haAcabadoCargaDatos >= 6) {
            Toast.makeText(MainActivity.context, MainActivity.context.getResources().getString(R.string.msg_datos_cargados_pnreq) + MainActivity.haAcabadoCargaDatos, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.context, MenuLoginReg.class);
            MainActivity.context.startActivity(i);
            MainActivity.CARGADATOS = false;

        }
    }

    private void login(JSONArray datos) throws JSONException {
        JSONObject obj = datos.getJSONObject(0);

        MainActivity.clienteActivo = new Cliente(
                obj.getInt(MainActivity.context.getResources().getString(R.string.key_id)),
                obj.getString(MainActivity.context.getResources().getString(R.string.key_nombre)),
                obj.getString(MainActivity.context.getResources().getString(R.string.key_apellido)),
                obj.getString(MainActivity.context.getResources().getString(R.string.key_tlfno)),
                obj.getString(MainActivity.context.getResources().getString(R.string.key_calle)),
                obj.getString(MainActivity.context.getResources().getString(R.string.key_portal)),
                obj.getString(MainActivity.context.getResources().getString(R.string.key_piso)),
                obj.getString(MainActivity.context.getResources().getString(R.string.key_puerta)),
                obj.getString(MainActivity.context.getResources().getString(R.string.key_urbanizacion)),
                obj.getString(MainActivity.context.getResources().getString(R.string.key_cod_postal))
        );
        Toast.makeText(Login.context, MainActivity.context.getResources().getString(R.string.msg_login_correcto_pnreq), Toast.LENGTH_SHORT).show();
        Login.LOGIN = false;
        Intent i = new Intent(Login.context, Menu_principal.class);
        Login.context.startActivity(i);
    }
}
