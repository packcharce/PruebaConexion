/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.fragments.SubFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.probas.pruebaconexion.CreaPedido2;
import com.probas.pruebaconexion.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sub_Opciones_envio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sub_Opciones_envio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sub_Opciones_envio extends android.app.Fragment {

    private OnFragmentInteractionListener mListener;

    public Sub_Opciones_envio() {
        // Required empty public constructor
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
                //CreaPedido2.pedido.calculaTotal();
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
        void onFragmentInteraction(Uri uri);
    }
}
