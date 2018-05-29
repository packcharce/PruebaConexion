package com.probas.pruebaconexion;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.probas.pruebaconexion.fragments.ConfirmacionPedido;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Ensalada;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Hamburguesa;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Lasania;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Pasta;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_bebidas;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_crea_pedido;

import java.util.ArrayList;
import java.util.HashMap;

public class CreaPedido2 extends AppCompatActivity implements
        Sub_crea_pedido.OnFragmentInteractionListener,
        Sub_bebidas.OnFragmentInteractionListener,
        Sub_Hamburguesa.OnFragmentInteractionListener,
        Sub_Lasania.OnFragmentInteractionListener,
        Sub_Ensalada.OnFragmentInteractionListener,
        Sub_Pasta.OnFragmentInteractionListener,
        ConfirmacionPedido.NoticeDialogListener {

    public static Pedido pedido;

    private int numeroDePizza;
    ArrayList<Fragment> listaFragments;

    TextView contPizzas;
    Button siguiente;
    Button anterior;

    private int fasePedido, fasesTotales;
    private final int numeroFasesProtegidas = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_pedido2);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                listaFragments = new ArrayList<>();
                fasePedido = 0;
                numeroDePizza = 0;

                pedido = new Pedido();

                pedido.getListaPizzas().put(numeroDePizza, new Pizza("Custom"));


                cargaFragments();
                getFragmentManager().beginTransaction().replace(R.id.fragment2, listaFragments.get(0)).commit();


                siguiente = findViewById(R.id.btnSig);
                anterior = findViewById(R.id.btnAnterior);
                Button anhadePizza = findViewById(R.id.btn_anhade_pizza);
                Button quitaPizza = findViewById(R.id.btn_quita_pizza);
                contPizzas = findViewById(R.id.tv_muestra_cant_pizzas);

                anhadePizza.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numeroDePizza++;
                        pedido.getListaPizzas().put(numeroDePizza, new Pizza("Custom"));

                        contPizzas.setText(
                                String.valueOf(Integer.parseInt(contPizzas.getText().toString()) + 1));

                        listaFragments.add(fasesTotales - numeroFasesProtegidas, Sub_crea_pedido.newInstance(numeroDePizza));

                        fasesTotales++;
                    }
                });

                quitaPizza.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pedido.getListaPizzas().size() > 1) {
                            if (fasePedido < fasesTotales - numeroFasesProtegidas) {
                                Object[] aux = pedido.getListaPizzas().keySet().toArray();
                                pedido.getListaPizzas().remove(aux[fasePedido]);

                                contPizzas.setText(
                                        String.valueOf(Integer.parseInt(contPizzas.getText().toString()) - 1));

                                listaFragments.remove(fasePedido);
                                if (fasePedido == 0)
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.fragment2, listaFragments.get(fasePedido))
                                            .commit();
                                else
                                    anterior.performClick();


                                fasesTotales--;
                            } else {
                                Toast.makeText(getApplicationContext(), "Solo puedes borrar pizzas", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });


                anterior.setVisibility(View.GONE);
                anterior.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fasePedido > 0) {
                            fasePedido--;
                            getFragmentManager().popBackStack();
                            if (fasePedido <= 0) anterior.setVisibility(View.GONE);
                        }
                        siguiente.setVisibility(View.VISIBLE);
                    }
                });

                siguiente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fasePedido < listaFragments.size() - 1) {
                            fasePedido++;
                            //this.setTitle("Fase de pedido: " + (fasePedido+1));

                            getFragmentManager().beginTransaction()
                                    .add(R.id.fragment2, listaFragments.get(fasePedido))
                                    .hide(listaFragments.get(fasePedido - 1))
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .addToBackStack(null)
                                    .commit();

                        } else {
                            DialogFragment dialog = new ConfirmacionPedido();
                            dialog.show(getFragmentManager(), "NoticeDialogFragment");
                        }
                        anterior.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
        t1.setName("Carga CreaPedido2");
        t1.start();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        Pedido p = CreaPedido2.pedido;
        if (p.getTotal() != 0.0) {

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
            this.finish();
        } else {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "Error, pedido vacio", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    private void cargaFragments() {
        listaFragments.add(Sub_crea_pedido.newInstance(numeroDePizza));
        listaFragments.add(Sub_bebidas.newInstance());
        listaFragments.add(Sub_Hamburguesa.newInstance());
        listaFragments.add(Sub_Lasania.newInstance());
        listaFragments.add(Sub_Ensalada.newInstance());
        listaFragments.add(Sub_Pasta.newInstance());
        fasesTotales = listaFragments.size();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    @Override
    public void onBackPressed() {
        if (fasePedido == 0) {
            this.finish();
        } else {
            anterior.performClick();
        }
    }

}
