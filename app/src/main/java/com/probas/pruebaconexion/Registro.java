package com.probas.pruebaconexion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        String user = editTextUsuario.getText().toString();
        String pass = editTextContrasenia.getText().toString();
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


        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_CLIENTE, params, MainActivity.CODE_POST_REQUEST, 'a');
        request.execute();
    }
}
