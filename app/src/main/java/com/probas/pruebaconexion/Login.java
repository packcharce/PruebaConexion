/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.probas.pruebaconexion.ClasesBasicas.Cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    private Context context;
    private EditText editTextUsuario;
    private EditText editTextContrasenia;

    // Flag que indica si se ha terminado la operacion de login y si ha habido error
    static boolean LOGIN;

    /**
     * On create que inicializa los botones
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LOGIN=false;
        context=getApplicationContext();

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContrasenia = findViewById(R.id.editTextContrasenia);
        editTextUsuario.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button buttonLogin = findViewById(R.id.buttonAddUpdate);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOGIN=true;
                login(editTextUsuario.getText().toString(), editTextContrasenia.getText().toString());
                if (!LOGIN) {
                    try {
                        finish();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
        });
    }

    public Login() {
    }

    /**
     * Metodo que llama a la operacion de login
     * @param user
     * @param pass
     */
    private void login(String user, String pass){
        HashMap<String, String> params = new HashMap<>();
        params.put(getString(R.string.key_usuario_login), user);
        params.put(getString(R.string.key_pass_login), pass);

        Logueador l= new Logueador(Api.URL_LOGIN, params, MainActivity.CODE_POST_REQUEST);
        l.execute();
        l=null;
    }

    /**
     * Carga los datos de la bd en el objeto cliente
     * @param datos
     * @throws JSONException
     */
    private void login(JSONArray datos) throws JSONException {
        JSONObject obj = datos.getJSONObject(0);

        MainActivity.clienteActivo = new Cliente(
                obj.getInt(getString(R.string.key_id)),
                obj.getString(getString(R.string.key_nombre)),
                obj.getString(getString(R.string.key_apellido)),
                obj.getString(getString(R.string.key_tlfno)),
                obj.getString(getString(R.string.key_calle)),
                obj.getString(getString(R.string.key_portal)),
                obj.getString(getString(R.string.key_piso)),
                obj.getString(getString(R.string.key_puerta)),
                obj.getString(getString(R.string.key_urbanizacion)),
                obj.getString(getString(R.string.key_cod_postal))
        );
        Toast.makeText(this, getString(R.string.msg_login_correcto_pnreq), Toast.LENGTH_SHORT).show();
        Login.LOGIN = false;
        Intent i = new Intent(this, Menu_principal.class);
        startActivity(i);
    }


    /**
     * Clase interna que hace el login por http request a la bd
     */
    private class Logueador extends AsyncTask<Void, Void, String> {

        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        private Logueador(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        /**
         * Realiza la peticion a la bd
         * @param voids
         * @return
         */
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == MainActivity.CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);
            return null;

        }

        /**
         * Resultado de la peticion a la bd
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                if (object.length() != 0) {
                    if (Login.LOGIN)
                        if (object.getJSONArray(MainActivity.context.getResources().getString(R.string.key_datos_pnreq)).length() != 0)
                            login(object.getJSONArray(getString(R.string.key_datos_pnreq)));
                        else {
                            Login.LOGIN = false;
                            Toast.makeText(getApplicationContext(), getString(R.string.error_login_pnreq), Toast.LENGTH_SHORT).show();
                        }
                }
            }catch (JSONException js){
                js.printStackTrace();
            }
        }
    }
}
