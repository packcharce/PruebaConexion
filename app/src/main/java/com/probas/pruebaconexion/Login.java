package com.probas.pruebaconexion;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.probas.pruebaconexion.fragments.Mis_pedidos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Login extends AppCompatActivity {

    static Context context;
    EditText editTextUsuario,
            editTextContrasenia;
    Button buttonLogin;

    static boolean LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LOGIN=false;
        context=getApplicationContext();

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContrasenia = findViewById(R.id.editTextContrasenia);

        buttonLogin =findViewById(R.id.buttonAddUpdate);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOGIN=true;
                login();
                if (!LOGIN) {
                    try {
                        finalize();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
        });
    }

    public Login() {
    }

    private void login(){
        String user = editTextUsuario.getText().toString();
        String pass = editTextContrasenia.getText().toString();

        HashMap<String, String> params = new HashMap<>();
        params.put("usuario", user);
        params.put("contrasenia", pass);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_LOGIN, params, MainActivity.CODE_POST_REQUEST, 'a');
        request.execute();

    }
}
