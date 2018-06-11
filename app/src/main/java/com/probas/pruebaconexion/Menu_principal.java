/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.probas.pruebaconexion.fragments.ConfirmacionLogout;
import com.probas.pruebaconexion.fragments.Contacto;
import com.probas.pruebaconexion.fragments.Datos_cliente;
import com.probas.pruebaconexion.fragments.Mis_pedidos;
import com.probas.pruebaconexion.fragments.Ofertas;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Opciones_Pago;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static com.probas.pruebaconexion.MainActivity.CODE_POST_REQUEST;

/**
 * Clase que es el menu principal de la aplicación
 * contiene los fragmentos con perfil, pedidos... y se encarga de administrarlos
 */
public class Menu_principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Mis_pedidos.OnFragmentInteractionListener,
        Datos_cliente.OnFragmentInteractionListener,
        Ofertas.OnFragmentInteractionListener,
        Contacto.OnFragmentInteractionListener,
        Sub_Opciones_Pago.OnFragmentInteractionListener,
        ConfirmacionLogout.NoticeDialogListener {

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();

        pidePedidosCliente();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.nombreUsuarioHeader);
        nav_user.setText(MainActivity.clienteActivo.getNombre());
        nav_user = hView.findViewById(R.id.apellido_header);
        nav_user.setText(MainActivity.clienteActivo.getApellido());
        setTitle(R.string.txt_ofertas_drawer_menu);
    }

    /**
     * Metodo que controla la pulsacion del boton fisico
     * de "atras"
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            DialogFragment dialog = new ConfirmacionLogout();
            dialog.show(getFragmentManager(), "tag_confir_logout");
        }
    }

    /**
     * Carga el menu de los "tres puntos"
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    /**
     * Metodo que controla el click en los botones del menu de "tres puntos"
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    String lasTag;

    /**
     * Metodo que controla la seleccion en el menu lateral desplegable
     * @param item  item clickado
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment newFragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Intent i;
        try {
            switch (item.getItemId()) {
                case R.id.nav_mis_pedidos:
                    setTitle(getString(R.string.txt_mis_pedidos_drawer_menu));
                    newFragment = Mis_pedidos.newInstance(numeroPedido, fecha, total);
                    lasTag = "mispedidos";
                    transaction.replace(R.id.fragment, newFragment, "mispedidos");
                    transaction.commit();
                    break;
                case R.id.nav_perfil:
                    setTitle(getString(R.string.txt_perfil_drawer_menu));
                    newFragment = Datos_cliente.newInstance();
                    lasTag = "miperfil";
                    transaction.replace(R.id.fragment, newFragment, "miperfil");
                    transaction.commit();
                    break;
                case R.id.nav_ofertas:
                    setTitle(getString(R.string.txt_ofertas_drawer_menu));
                    newFragment = Ofertas.newInstance();
                    lasTag = "ofertas";
                    transaction.replace(R.id.fragment, newFragment, "ofertas");
                    transaction.commit();
                    break;
                case R.id.nav_contacto:
                    setTitle(getString(R.string.tit_contacto_contacto));
                    newFragment = Contacto.newInstance();
                    lasTag = "contacto";
                    transaction.replace(R.id.fragment, newFragment, "contacto");
                    transaction.commit();
                    break;
                case R.id.nav_crea_pedido:
                    if(lasTag != null) {
                        transaction.remove(getFragmentManager().findFragmentByTag(lasTag));
                        transaction.commit();
                    }
                    setTitle(R.string.txt_ofertas_drawer_menu);
                    i = new Intent(this, CreaPedido2.class);
                    startActivity(i);
                    break;
            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } catch (NullPointerException ne) {
            this.finish();
        }
        return true;
    }


    /**
     * Hace la peticion a la bd para sacar los pedidos realizados por el cliente
     */
    private void pidePedidosCliente() {
        Mis_pedidos.PEDIDOS = true;
        HashMap<String, String> params = new HashMap<>();
        params.put(getString(R.string.key_nombre_param_menu_princ), getString(R.string.key_ref_cliente_menu_princ));
        params.put(getString(R.string.key_valor_princ_menu_princ), String.valueOf(MainActivity.clienteActivo.getId()));
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_GET_PEDIDO, params, CODE_POST_REQUEST, '0');
        request.execute();
    }

    private static ArrayList<String> numeroPedido = new ArrayList<>();
    private static ArrayList<String> fecha = new ArrayList<>();
    private static ArrayList<String> total = new ArrayList<>();

    /**
     * Guarda los pedidos realizados por el cliente
     * @param datos
     * @throws JSONException
     */
    public static void cargaDatos(JSONArray datos) throws JSONException {
        numeroPedido.clear();
        fecha.clear();
        total.clear();
        /*
        numeroPedido.add(context.getString(R.string.tit_numero_pedido_mis_pedidos));
        fecha.add(context.getString(R.string.tit_fecha_mis_pedidos));
        total.add(context.getString(R.string.tit_total_mis_pedidos));
        */
        for (int i = 0; i < datos.length(); i++) {
            JSONObject obj = datos.getJSONObject(i);
            numeroPedido.add(obj.getString(context.getString(R.string.key_num_pedido_mis_pedidos)));
            fecha.add(obj.getString(context.getString(R.string.key_fecha_pedido_mis_pedidos)));
            total.add(String.format(Locale.FRANCE,"%.2f€", obj.getDouble(context.getString(R.string.key_total_mis_pedidos))));
        }
        Mis_pedidos.CARGA_COMPLETA_PEDIDOS = true;
    }

    /**
     * Metodo que hay que implementar con los fragmentos
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
