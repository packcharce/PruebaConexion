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

import com.probas.pruebaconexion.ClasesBasicas.Pasta;
import com.probas.pruebaconexion.CreaPedido2;
import com.probas.pruebaconexion.MainActivity;
import com.probas.pruebaconexion.R;
import com.probas.pruebaconexion.fragments.ClickListener;
import com.probas.pruebaconexion.fragments.MyAdapter;

import java.util.ArrayList;

/**
 * Fragmento para pedir pasta
 */
public class Sub_Pasta extends Fragment {
    private static final String ARG_PARAM1 = "pasta";
    private static final int TIPO_PASTA = 5;

    private static ArrayList<Pasta> listaPasta;

    private OnFragmentInteractionListener mListener;

    public Sub_Pasta() {
    }

    public static Sub_Pasta newInstance() {
        return new Sub_Pasta();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listaPasta = (ArrayList<Pasta>) MainActivity.listaPasta;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sub_pasta, container, false);
        Bundle pasaDatos = new Bundle();

        final RecyclerView mRecyclerView = v.findViewById(R.id.rec_pasta_pedido);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        pasaDatos.putSerializable(ARG_PARAM1, (ArrayList<Pasta>) MainActivity.listaPasta);


        RecyclerView.Adapter mAdapter = new MyAdapter(pasaDatos, TIPO_PASTA, new ClickListener() {
            @Override
            public void onPositionClicked(View v, int position) {
                if(v.getId() == R.id.anadir) {
                    CreaPedido2.pedido.getListaPasta().add(new Pasta(listaPasta.get(position)));
                }
                else if (v.getId() == R.id.quitar && CreaPedido2.pedido.getListaPasta().size() > 0){
                    CreaPedido2.pedido.quitaPasta(listaPasta.get(position).getNombre());
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return v;
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
        CreaPedido2.pedido.getListaPasta().clear();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
