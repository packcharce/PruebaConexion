/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.fragments.SubFragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.probas.pruebaconexion.ClasesBasicas.Lasania;
import com.probas.pruebaconexion.CreaPedido2;
import com.probas.pruebaconexion.MainActivity;
import com.probas.pruebaconexion.R;
import com.probas.pruebaconexion.fragments.ClickListener;
import com.probas.pruebaconexion.fragments.MyAdapter;

import java.util.ArrayList;

/**
 * * Fragmento para pedir Lasañas
 */
public class Sub_Lasania extends Fragment {

    private static final String ARG_PARAM1 = "lasania";
    private static final int TIPO_LASANIA = 6;

    private static ArrayList<Lasania> listaLasania;

    private OnFragmentInteractionListener mListener;

    public Sub_Lasania() {
    }

    public static Sub_Lasania newInstance() {
        return new Sub_Lasania();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listaLasania = (ArrayList<Lasania>) MainActivity.listaLas;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sub_lasania, container, false);
        Bundle pasaDatos = new Bundle();

        final RecyclerView mRecyclerView = v.findViewById(R.id.rec_lasania_pedido);


        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        pasaDatos.putSerializable(ARG_PARAM1, (ArrayList<Lasania>) MainActivity.listaLas);


        RecyclerView.Adapter mAdapter = new MyAdapter(pasaDatos, TIPO_LASANIA, new ClickListener() {
            @Override
            public void onPositionClicked(View v, int position) {
                if(v.getId() == R.id.anadir) {
                    CreaPedido2.pedido.getListaLas().add(new Lasania(listaLasania.get(position)));
                }
                else if (v.getId() == R.id.quitar && CreaPedido2.pedido.getListaLas().size() > 0){
                    CreaPedido2.pedido.quitaLas(listaLasania.get(position).getNombre());
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
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
        CreaPedido2.pedido.getListaLas().clear();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
