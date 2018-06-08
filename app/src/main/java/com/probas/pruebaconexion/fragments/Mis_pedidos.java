/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.probas.pruebaconexion.Api;
import com.probas.pruebaconexion.MainActivity;
import com.probas.pruebaconexion.Menu_principal;
import com.probas.pruebaconexion.R;
import com.probas.pruebaconexion.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.probas.pruebaconexion.MainActivity.CODE_POST_REQUEST;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Mis_pedidos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Mis_pedidos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mis_pedidos extends Fragment {

    public static boolean PEDIDOS=false;
    public static boolean CARGA_COMPLETA_PEDIDOS=false;

    private static ArrayList<String> numeroPedido;
    private static ArrayList<String> fecha;
    private static ArrayList<String> total;

    private OnFragmentInteractionListener mListener;

    public Mis_pedidos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Mis_pedidos.
     */

    public static Mis_pedidos newInstance(ArrayList<String> numPedido, ArrayList<String> fecha, ArrayList<String> total) {
        Mis_pedidos fragment = new Mis_pedidos();
        Bundle args = new Bundle();
        args.putStringArrayList(Menu_principal.context.getResources().getString(R.string.key_num_pedido_mis_pedidos), numPedido);
        args.putStringArrayList(Menu_principal.context.getResources().getString(R.string.key_fecha_pedido_mis_pedidos), fecha);
        args.putStringArrayList(Menu_principal.context.getResources().getString(R.string.key_total_mis_pedidos), total);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            numeroPedido = getArguments().getStringArrayList(Menu_principal.context.getResources().getString(R.string.key_num_pedido_mis_pedidos));
            fecha = getArguments().getStringArrayList(Menu_principal.context.getResources().getString(R.string.key_fecha_pedido_mis_pedidos));
            total = getArguments().getStringArrayList(Menu_principal.context.getResources().getString(R.string.key_total_mis_pedidos));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mis_pedidos, container, false);


        mRecyclerView = v.findViewById(R.id.rec_view_misPedidos);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Bundle b= new Bundle();
        b.putStringArrayList(Menu_principal.context.getResources().getString(R.string.key_num_pedido_mis_pedidos), numeroPedido);
        b.putStringArrayList(Menu_principal.context.getResources().getString(R.string.key_fecha_pedido_mis_pedidos), fecha);
        b.putStringArrayList(Menu_principal.context.getResources().getString(R.string.key_total_mis_pedidos), total);
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(b, 0, new ClickListener() {
            @Override
            public void onPositionClicked(View v, int position) {
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        getActivity().setTitle(getString(R.string.tit_historial_mis_pedidos));

        sw = v.findViewById(R.id.swiperefresh);
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pideDatos();
            }
        });

        return v;
    }
    private SwipeRefreshLayout sw;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.excep_notice_dialog_listener));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void pideDatos(){
        HashMap<String, String> params = new HashMap<>();
        params.put(getString(R.string.key_nombre_param_menu_princ), getString(R.string.key_ref_cliente_menu_princ));
        params.put(getString(R.string.key_valor_princ_menu_princ), String.valueOf(MainActivity.clienteActivo.getId()));
        Refrescadora request = new Refrescadora(Api.URL_GET_PEDIDO, params, CODE_POST_REQUEST, '0');
        request.execute();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class Refrescadora extends AsyncTask<Void, Void, String> {

        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        char tipoDato;

        private Refrescadora(String url, HashMap<String, String> params, int requestCode, char tipoDato) {
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
                    if (!object.getBoolean(getResources().getString(R.string.key_error))) {
                        if (object.getJSONArray(getString(R.string.key_datos_pnreq)).length() != 0) {
                            numeroPedido.clear();
                            fecha.clear();
                            total.clear();
                            numeroPedido.add(getString(R.string.tit_numero_pedido_mis_pedidos));
                            fecha.add(getString(R.string.tit_fecha_mis_pedidos));
                            total.add(getString(R.string.tit_total_mis_pedidos));
                            JSONArray datos = object.getJSONArray(getString(R.string.key_datos_pnreq));
                            for (int i = 0; i < datos.length(); i++) {
                                JSONObject obj = datos.getJSONObject(i);
                                numeroPedido.add(obj.getString(getString(R.string.key_num_pedido_mis_pedidos)));
                                fecha.add(obj.getString(getString(R.string.key_fecha_pedido_mis_pedidos)));
                                total.add(String.valueOf(obj.getDouble(getString(R.string.key_total_mis_pedidos))));
                            }
                            Bundle b= new Bundle();
                            b.putStringArrayList(getString(R.string.key_num_pedido_mis_pedidos), numeroPedido);
                            b.putStringArrayList(getString(R.string.key_fecha_pedido_mis_pedidos), fecha);
                            b.putStringArrayList(getString(R.string.key_total_mis_pedidos), total);
                            mAdapter = new MyAdapter(b, 0, null);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }
                }
                sw.setRefreshing(false);
            }catch (JSONException js){
                js.printStackTrace();
            }
        }
    }

}
