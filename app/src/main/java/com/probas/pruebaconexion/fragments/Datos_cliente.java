/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.probas.pruebaconexion.Api;
import com.probas.pruebaconexion.MainActivity;
import com.probas.pruebaconexion.Menu_principal;
import com.probas.pruebaconexion.R;
import com.probas.pruebaconexion.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.probas.pruebaconexion.MainActivity.CODE_POST_REQUEST;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Datos_cliente.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Datos_cliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Datos_cliente extends Fragment {


    private EditText
            editTextNombre,
            editTextApellido1,
            editTextTlfno,
            editTextCalle,
            editTextPortal,
            editTextPiso,
            editTextPuerta,
            editTextUrbanizacion,
            editTextCodPostal;

    private OnFragmentInteractionListener mListener;

    public Datos_cliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Datos_cliente.
     */
    // TODO: Rename and change types and number of parameters
    public static Datos_cliente newInstance() {
        return new Datos_cliente();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos_cliente, container, false);

        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextApellido1 = view.findViewById(R.id.editTextApellido1);
        editTextTlfno = view.findViewById(R.id.editTextTlfno);
        editTextCalle = view.findViewById(R.id.editTextCalle);
        editTextPortal = view.findViewById(R.id.editTextPortal);
        editTextPiso = view.findViewById(R.id.editTextPiso);
        editTextPuerta = view.findViewById(R.id.editTextPuerta);
        editTextUrbanizacion = view.findViewById(R.id.editTextUrbanizacion);
        editTextCodPostal = view.findViewById(R.id.editTextCodPostal);

        editTextNombre.setText(MainActivity.clienteActivo.getNombre());
        editTextApellido1.setText(MainActivity.clienteActivo.getApellido());
        editTextTlfno.setText(MainActivity.clienteActivo.getTlfno());
        editTextCalle.setText(MainActivity.clienteActivo.getCalle());
        editTextPortal.setText(MainActivity.clienteActivo.getPortal());
        editTextPiso.setText(MainActivity.clienteActivo.getPiso());
        editTextPuerta.setText(MainActivity.clienteActivo.getPuerta());
        editTextUrbanizacion.setText(MainActivity.clienteActivo.getUrbanizacion());
        editTextCodPostal.setText(MainActivity.clienteActivo.getCodigoPostal());

        Button buttonRegistro = view.findViewById(R.id.buttonAddUpdate);

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO poner metodo update
                actualizaCliente();
            }
        });
        getActivity().setTitle(getString(R.string.tit_frag_mi_perfil_miperfil));
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    private void actualizaCliente(){
        HashMap<String, String> params = new HashMap<>();
        params.put(Menu_principal.context.getResources().getString(R.string.key_id), String.valueOf(MainActivity.clienteActivo.getId()));
        params.put(Menu_principal.context.getResources().getString(R.string.key_nombre), editTextNombre.getText().toString());
        params.put(Menu_principal.context.getResources().getString(R.string.key_apellido), editTextApellido1.getText().toString());
        params.put(Menu_principal.context.getResources().getString(R.string.key_tlfno), editTextTlfno.getText().toString());
        params.put(Menu_principal.context.getResources().getString(R.string.key_calle), editTextCalle.getText().toString());
        params.put(Menu_principal.context.getResources().getString(R.string.key_portal), editTextPortal.getText().toString());
        params.put(Menu_principal.context.getResources().getString(R.string.key_piso), editTextPiso.getText().toString());
        params.put(Menu_principal.context.getResources().getString(R.string.key_puerta), editTextPuerta.getText().toString());
        params.put(Menu_principal.context.getResources().getString(R.string.key_urbanizacion), editTextUrbanizacion.getText().toString());
        params.put(Menu_principal.context.getResources().getString(R.string.key_cod_postal), editTextCodPostal.getText().toString());

        Actualizadora ac = new Actualizadora(Api.URL_UPDATE_CLIENTE, params, CODE_POST_REQUEST);
        ac.execute();
    }

    private class Actualizadora extends AsyncTask<Void, Void, String> {

        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        char tipoDato;

        private Actualizadora(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
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
                    if (!object.getBoolean(Menu_principal.context.getResources().getString(R.string.key_error))) {
                        Toast.makeText(getActivity(), getString(R.string.msg_actualizado_datos_cliente), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(), object.getString(getString(R.string.key_message_datos_cliente)), Toast.LENGTH_LONG).show();
                    }
                }
            }catch (JSONException js){
                js.printStackTrace();
            }
        }
    }
}
