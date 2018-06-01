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
        args.putStringArrayList("numPedido", numPedido);
        args.putStringArrayList("fecha", fecha);
        args.putStringArrayList("total", total);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            numeroPedido = getArguments().getStringArrayList("numPedido");
            total = getArguments().getStringArrayList("total");
            fecha = getArguments().getStringArrayList("fecha");
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
        b.putStringArrayList("numeroPedido", numeroPedido);
        b.putStringArrayList("fecha", fecha);
        b.putStringArrayList("total", total);
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(b, 0, new ClickListener() {
            @Override
            public void onPositionClicked(View v, int position) {
                System.out.println(position + " Mis Pedidos");
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        getActivity().setTitle("Historial de Pedidos");

        sw = v.findViewById(R.id.swiperefresh);
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pideDatos();
            }
        });

        return v;
    }
    SwipeRefreshLayout sw;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

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
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void pideDatos(){
        HashMap<String, String> params = new HashMap<>();
        params.put("nombrePar", "refCliente");
        params.put("valorPar", String.valueOf(MainActivity.clienteActivo.getId()));
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
                        if (object.getJSONArray("datos").length() != 0) {
                            //Mis_pedidos.PEDIDOS = false;
                            //misPedidos(object.getJSONArray("datos"));
                            //Menu_principal.cargaDatos(object.getJSONArray("datos"));
                            numeroPedido.clear();
                            fecha.clear();
                            total.clear();
                            numeroPedido.add("Referencia\npedido");
                            fecha.add("Fecha\nPedido");
                            total.add("Precio\npedido");
                            JSONArray datos = object.getJSONArray("datos");
                            for (int i = 0; i < datos.length(); i++) {
                                JSONObject obj = datos.getJSONObject(i);
                                numeroPedido.add(obj.getString("numPedido"));
                                fecha.add(obj.getString("fechaPedido"));
                                total.add(String.valueOf(obj.getDouble("total")));
                            }
                            Bundle b= new Bundle();
                            b.putStringArrayList("numeroPedido", numeroPedido);
                            b.putStringArrayList("fecha", fecha);
                            b.putStringArrayList("total", total);
                            mAdapter = new MyAdapter(b, 0, null);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }
                }
                sw.setRefreshing(false);
            }catch (JSONException js){

            }
        }
    }

}
