/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.fragments.SubFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.probas.pruebaconexion.CreaPedido2;
import com.probas.pruebaconexion.R;

/**
 * Fragmento para elegir las opciones de envio
 */
public class Sub_Opciones_envio extends android.app.Fragment {

    private OnFragmentInteractionListener mListener;

    public Sub_Opciones_envio() {
    }

    public static Sub_Opciones_envio newInstance() {
        return new Sub_Opciones_envio();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static boolean readyToDeliver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sub__opciones_envio, container, false);
        readyToDeliver = false;
        RadioGroup rg = v.findViewById(R.id.opc_envio_radio_grup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.env_casa:
                        readyToDeliver = false;
                        CreaPedido2.pedido.setExtra_domicilio(5.0f);
                        CreaPedido2.pedido.setExtra_local(0.0f);
                        CreaPedido2.pedido.setExtra_recoger(0.0f);
                        readyToDeliver = true;
                        break;
                    case R.id.env_local:
                        readyToDeliver = false;
                        CreaPedido2.pedido.setExtra_domicilio(0.0f);
                        CreaPedido2.pedido.setExtra_local(3.0f);
                        CreaPedido2.pedido.setExtra_recoger(0.0f);
                        readyToDeliver = true;
                        break;
                    case R.id.env_rec_local:
                        readyToDeliver = false;
                        CreaPedido2.pedido.setExtra_domicilio(0.0f);
                        CreaPedido2.pedido.setExtra_local(0.0f);
                        CreaPedido2.pedido.setExtra_recoger(1.0f);
                        readyToDeliver = true;
                        break;
                }
            }
        });

        return v;
    }

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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
