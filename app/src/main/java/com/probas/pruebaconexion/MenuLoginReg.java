/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Clase menú para elegir si registrarte o hacer login
 */
public class MenuLoginReg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_login_reg);
        Button login = findViewById(R.id.botLog);
        Button registro = findViewById(R.id.botReg);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegistro();
            }
        });
    }

    private void toLogin(){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
    private void toRegistro(){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }
}
