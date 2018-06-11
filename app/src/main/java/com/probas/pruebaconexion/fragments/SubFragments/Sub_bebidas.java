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

import com.probas.pruebaconexion.ClasesBasicas.Bebida;
import com.probas.pruebaconexion.CreaPedido2;
import com.probas.pruebaconexion.MainActivity;
import com.probas.pruebaconexion.R;
import com.probas.pruebaconexion.fragments.ClickListener;
import com.probas.pruebaconexion.fragments.MyAdapter;

import java.util.ArrayList;

/**
 * Fragmento para pedir bebidas
 */
public class Sub_bebidas extends Fragment {
    private static final String ARG_PARAM1 = "bebidas";
    private static final int TIPO_BEBIDAS = 2;

    private static ArrayList<Bebida> listaBebidas;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public Sub_bebidas() {
        // Required empty public constructor
    }

    /**
     * Inicializador del fragment
     * @return
     */
    public static Sub_bebidas newInstance() {
        return new Sub_bebidas();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listaBebidas = (ArrayList<Bebida>) MainActivity.listaBebs;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sub_bebidas, container, false);


        Bundle pasaDatos = new Bundle();

        mRecyclerView = v.findViewById(R.id.rec_bebidas_pedido);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        pasaDatos.putSerializable(ARG_PARAM1, (ArrayList<Bebida>) MainActivity.listaBebs);


        mAdapter = new MyAdapter(pasaDatos, TIPO_BEBIDAS, new ClickListener() {
            @Override
            public void onPositionClicked(View v, int position) {
                if (v.getId() == R.id.anadir) {
                    CreaPedido2.pedido.getListaBebs().add(new Bebida(listaBebidas.get(position)));
                } else if (v.getId() == R.id.quitar && CreaPedido2.pedido.getListaBebs().size() > 0) {
                    CreaPedido2.pedido.quitaBebida(MainActivity.listaBebs.get(position).getNombre());
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
        CreaPedido2.pedido.getListaBebs().clear();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
