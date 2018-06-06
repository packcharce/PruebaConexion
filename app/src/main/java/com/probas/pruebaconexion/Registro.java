package com.probas.pruebaconexion;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

    private static Context context;
    private EditText
            editTextNombre;
    private EditText editTextApellido1;
    private EditText editTextTlfno;
    private EditText editTextCalle;
    private EditText editTextPortal;
    private EditText editTextPiso;
    private EditText editTextPuerta;
    private EditText editTextUrbanizacion;
    private EditText editTextUsuario;
    private EditText editTextContrasenia;
    private EditText editTextCodPostal;
    private Button buttonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido1 = findViewById(R.id.editTextApellido1);
        editTextTlfno = findViewById(R.id.editTextTlfno);
        editTextCalle = findViewById(R.id.editTextCalle);
        editTextPortal = findViewById(R.id.editTextPortal);
        editTextPiso = findViewById(R.id.editTextPiso);
        editTextPuerta = findViewById(R.id.editTextPuerta);
        editTextUrbanizacion = findViewById(R.id.editTextUrbanizacion);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContrasenia = findViewById(R.id.editTextContrasenia);
        editTextCodPostal = findViewById(R.id.editTextCodPostal);

        context=getApplicationContext();

        buttonRegistro = findViewById(R.id.buttonAddUpdate);

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearCliente();
            }
        });
    }

    private String user;
    private String pass;

    private void crearCliente() {
        String nombre = editTextNombre.getText().toString();
        String ap1 = editTextApellido1.getText().toString();
        String tlfno = editTextTlfno.getText().toString();
        String calle = editTextCalle.getText().toString();
        String portal = editTextPortal.getText().toString();
        String piso = editTextPiso.getText().toString();
        String puerta = editTextPuerta.getText().toString();
        String urba = editTextUrbanizacion.getText().toString();
        user = editTextUsuario.getText().toString();
        pass = editTextContrasenia.getText().toString();
        String codPostal = editTextCodPostal.getText().toString();


        //validating the inputs
        if (TextUtils.isEmpty(nombre)) {
            editTextNombre.setError(getString(R.string.error_introd_nombre_registro));
            editTextNombre.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(ap1)) {
            editTextApellido1.setError(getString(R.string.error_introd_ap_registro));
            editTextApellido1.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(tlfno)) {
            editTextTlfno.setError(getString(R.string.error_introd_tlfn_registro));
            editTextTlfno.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(calle)) {
            editTextCalle.setError(getString(R.string.error_introd_calle_registro));
            editTextCalle.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(portal)) {
            editTextPortal.setError(getString(R.string.error_introd_portal));
            editTextPortal.requestFocus();
            return;
        }
        if (puerta.length() > 3) {
            editTextPuerta.setError(getString(R.string.error_num_caract_puerta_registro));
            editTextPuerta.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(user)) {
            editTextUsuario.setError(getString(R.string.error_introd_user_registro));
            editTextUsuario.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            editTextContrasenia.setError(getString(R.string.error_introd_pass_registro));
            editTextContrasenia.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(codPostal) || codPostal.length() != 5) {
            editTextCodPostal.setError(getString(R.string.error_introd_cod_postal_registro));
            editTextCodPostal.requestFocus();
            return;
        }

        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put(Registro.context.getResources().getString(R.string.key_nombre), nombre);
        params.put(Registro.context.getResources().getString(R.string.key_apellido), ap1);
        params.put(Registro.context.getResources().getString(R.string.key_tlfno), tlfno);
        params.put(Registro.context.getResources().getString(R.string.key_calle), calle);
        params.put(Registro.context.getResources().getString(R.string.key_portal), portal);
        params.put(Registro.context.getResources().getString(R.string.key_piso), piso);
        params.put(Registro.context.getResources().getString(R.string.key_puerta), puerta);
        params.put(Registro.context.getResources().getString(R.string.key_urbanizacion), urba);
        params.put(Registro.context.getResources().getString(R.string.key_usuario_login), user);
        params.put(Registro.context.getResources().getString(R.string.key_pass_login), pass);
        params.put(Registro.context.getResources().getString(R.string.key_cod_postal), codPostal);


        Registradora request = new Registradora(Api.URL_CREATE_CLIENTE, params, MainActivity.CODE_POST_REQUEST, 'a');
        request.execute();
    }

    private class Registradora extends AsyncTask<Void, Void, String> {

        final String url;

        //the parameters
        final HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        final int requestCode;

        final char tipoDato;

        private Registradora(String url, HashMap<String, String> params, int requestCode, char tipoDato) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
            this.tipoDato = tipoDato;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == MainActivity.CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject object = new JSONObject(s);
                if (object.length() != 0) {
                    if (!object.getBoolean(MainActivity.context.getResources().getString(R.string.key_error))) {
                        Toast.makeText(getApplicationContext(), R.string.msg_registro_exito_registro, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
                        finishAffinity();
                    } else {
                        Toast.makeText(getApplicationContext(), object.getString(getString(R.string.key_desc_error_registro)), Toast.LENGTH_LONG).show();
                    }
                }

            } catch (JSONException js) {
                js.printStackTrace();
            }
        }
    }
}
