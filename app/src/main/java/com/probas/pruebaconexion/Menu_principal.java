package com.probas.pruebaconexion;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.probas.pruebaconexion.fragments.ConfirmacionPedido;
import com.probas.pruebaconexion.fragments.Crea_pedido;
import com.probas.pruebaconexion.fragments.Datos_cliente;
import com.probas.pruebaconexion.fragments.Mis_pedidos;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Ensalada;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Hamburguesa;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Lasania;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Pasta;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_bebidas;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_crea_pedido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.probas.pruebaconexion.MainActivity.CODE_POST_REQUEST;

public class Menu_principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Datos_cliente.OnFragmentInteractionListener,
        Mis_pedidos.OnFragmentInteractionListener,
        Crea_pedido.OnFragmentInteractionListener,
        Sub_crea_pedido.OnFragmentInteractionListener,
        Sub_bebidas.OnFragmentInteractionListener,
        Sub_Hamburguesa.OnFragmentInteractionListener,
        Sub_Lasania.OnFragmentInteractionListener,
        Sub_Ensalada.OnFragmentInteractionListener,
        Sub_Pasta.OnFragmentInteractionListener,
        ConfirmacionPedido.NoticeDialogListener
{

    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();

        pidePedidosCliente();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.nombreUsuarioHeader);
        nav_user.setText(MainActivity.clienteActivo.getNombre());
        nav_user = hView.findViewById(R.id.apellido_header);
        nav_user.setText(MainActivity.clienteActivo.getApellido());
    }

    public static Context getContext() {
        return Menu_principal.context;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment newFragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (id == R.id.nav_mispedidos) {
            newFragment = Mis_pedidos.newInstance(numeroPedido, fecha, total);
            transaction.replace(R.id.fragment, newFragment);
            transaction.commit();
        } else if (id == R.id.nav_perfil) {
            newFragment = Datos_cliente.newInstance();
            transaction.replace(R.id.fragment, newFragment);
            transaction.commit();
        } else if (id == R.id.nav_ofertas) {

        } else if (id == R.id.nav_pizzas) {

        } else if (id == R.id.nav_donde_estamos) {

        } else if (id == R.id.nav_contacto) {

        } else if (id == R.id.nav_crea_pedido){
            setTitle("Nuevo Pedido");
            newFragment = Crea_pedido.newInstance();
            transaction.replace(R.id.fragment, newFragment);
            transaction.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    private synchronized void pidePedidosCliente(){
        Mis_pedidos.PEDIDOS=true;
        HashMap<String, String> params = new HashMap<>();
        params.put("nombrePar", "refCliente");
        params.put("valorPar", String.valueOf(MainActivity.clienteActivo.getId()));
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_GET_PEDIDO, params, CODE_POST_REQUEST, '0');
        request.execute();
    }

    private static ArrayList<String> numeroPedido = new ArrayList<>();
    private static ArrayList<String> fecha = new ArrayList<>();
    private static ArrayList<String> total = new ArrayList<>();

    public synchronized static void cargaDatos(JSONArray datos) throws JSONException {
        numeroPedido.clear();
        fecha.clear();
        total.clear();
        numeroPedido.add("Referencia\npedido");
        fecha.add("Fecha\nPedido");
        total.add("Precio\npedido");
        for (int i = 0; i < datos.length(); i++) {
            JSONObject obj = datos.getJSONObject(i);
            numeroPedido.add(obj.getString("numPedido"));
            fecha.add(obj.getString("fechaPedido"));
            total.add(String.valueOf(obj.getDouble("total")));
        }
        Mis_pedidos.CARGA_COMPLETA_PEDIDOS=true;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Pedido p = Crea_pedido.pedido;

        HashMap<String, String> params = new HashMap<>();

        //TODO quitar todo este tocho y pasar el objeto Pedido p directamente
        params.put("refCliente", String.valueOf(MainActivity.clienteActivo.getId()));
        params.put("numPedido", String.valueOf(p.getNumPedido()));
        params.put("extra_domicilio", String.valueOf(p.getExtra_domicilio()));
        params.put("extra_recoger", String.valueOf(p.getExtra_domicilio()));
        params.put("extra_local", String.valueOf(p.getExtra_local()));
        params.put("subtotal", String.valueOf(p.getSubtotal()));
        params.put("impuesto", String.valueOf(p.getImpuesto()));
        params.put("total", String.valueOf(p.getTotal()));

        params.put("listaPizzas", new Gson().toJson(p.getListaPizzas()));
        params.put("listaLasania", new Gson().toJson(p.getListaLas()));
        params.put("listaEnsaladas", new Gson().toJson(p.getListaEnsa()));
        params.put("listaBebs", new Gson().toJson(p.getListaBebs()));
        params.put("listaPasta", new Gson().toJson(p.getListaPasta()));
        params.put("listaHamburguesas", new Gson().toJson(p.getListaHamb()));


        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_PEDIDO, params, MainActivity.CODE_POST_REQUEST, 'a');
        request.execute();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
