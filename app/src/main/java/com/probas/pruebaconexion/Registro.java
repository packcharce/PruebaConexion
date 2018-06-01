package com.probas.pruebaconexion;

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

    EditText
            editTextNombre,
            editTextApellido1,
            editTextApellido2,
            editTextTlfno,
            editTextCalle,
            editTextPortal,
            editTextPiso,
            editTextPuerta,
            editTextUrbanizacion,
            editTextUsuario,
            editTextContrasenia,
            editTextCodPostal;
    Button buttonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido1 = findViewById(R.id.editTextApellido1);
        editTextApellido2 = findViewById(R.id.editTextApellido2);
        editTextTlfno = findViewById(R.id.editTextTlfno);
        editTextCalle = findViewById(R.id.editTextCalle);
        editTextPortal = findViewById(R.id.editTextPortal);
        editTextPiso = findViewById(R.id.editTextPiso);
        editTextPuerta = findViewById(R.id.editTextPuerta);
        editTextUrbanizacion = findViewById(R.id.editTextUrbanizacion);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContrasenia = findViewById(R.id.editTextContrasenia);
        editTextCodPostal = findViewById(R.id.editTextCodPostal);


        buttonRegistro =findViewById(R.id.buttonAddUpdate);

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearCliente();
                System.out.println("Cargado todo");
            }
        });
    }

    String user, pass;
    private void crearCliente() {
        String nombre = editTextNombre.getText().toString();
        String ap1 = editTextApellido1.getText().toString();
        String ap2 = editTextApellido2.getText().toString();
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
            editTextNombre.setError("Please enter name");
            editTextNombre.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(ap1)) {
            editTextApellido1.setError("Please enter real ap");
            editTextApellido1.requestFocus();
            return;
        }
        /*
        if (TextUtils.isEmpty(ap2)) {
            editTextApellido2.setError("Please enter ap2");
            editTextApellido2.requestFocus();
            return;
        }
        */
        if (TextUtils.isEmpty(tlfno)) {
            editTextTlfno.setError("Please enter tlfn");
            editTextTlfno.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(calle)) {
            editTextCalle.setError("Please enter calle");
            editTextCalle.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(portal)) {
            editTextPortal.setError("Please enter portal");
            editTextPortal.requestFocus();
            return;
        }
        /*
        if (TextUtils.isEmpty(piso)) {
            editTextPiso.setError("Please enter piso");
            editTextPiso.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(puerta)) {
            editTextPuerta.setError("Please enter pueta");
            editTextPuerta.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(urba)) {
            editTextPuerta.setError("Please enter urba");
            editTextPuerta.requestFocus();
            return;
        }*/
        if (TextUtils.isEmpty(user)) {
            editTextUsuario.setError("Please enter user");
            editTextUsuario.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            editTextContrasenia.setError("Please enter pass");
            editTextContrasenia.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(codPostal)) {
            editTextCodPostal.setError("Please enter codpostal");
            editTextCodPostal.requestFocus();
            return;
        }

        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", nombre);
        params.put("apellido1", ap1);
        params.put("apellido2", ap2);
        params.put("tlfno", tlfno);
        params.put("calle", calle);
        params.put("portal", portal);
        params.put("piso", piso);
        params.put("puerta", puerta);
        params.put("urbanizacion", urba);
        params.put("usuario", user);
        params.put("contrasenia", pass);
        params.put("codigoPostal", codPostal);


        Registradora request = new Registradora(Api.URL_CREATE_CLIENTE, params, MainActivity.CODE_POST_REQUEST, 'a');
        request.execute();
    }

    private class Registradora extends AsyncTask<Void, Void, String> {

        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        char tipoDato;

        private Registradora(String url, HashMap<String, String> params, int requestCode, char tipoDato) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
            this.tipoDato = tipoDato;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                    if (!object.getBoolean("error")) {
                        if (object.getString("message").length() != 0) {
                            Toast.makeText(getApplicationContext(), "Registrado Correctamente, haga login", Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(getApplicationContext(), Login.class);
                            startActivity(i);
                            finishAffinity();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), object.getString("descError"), Toast.LENGTH_LONG).show();
                    }
                }

            }catch (JSONException js){
                js.printStackTrace();
            }
        }
    }
}
