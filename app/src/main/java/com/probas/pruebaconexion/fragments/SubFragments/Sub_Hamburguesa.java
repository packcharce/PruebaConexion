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

import com.probas.pruebaconexion.ClasesBasicas.Hamburguesa;
import com.probas.pruebaconexion.CreaPedido2;
import com.probas.pruebaconexion.MainActivity;
import com.probas.pruebaconexion.R;
import com.probas.pruebaconexion.fragments.ClickListener;
import com.probas.pruebaconexion.fragments.MyAdapter;

import java.util.ArrayList;

/**
 * Fragmento para pedir hamburguesas
 */
public class Sub_Hamburguesa extends Fragment {
    private static final String ARG_PARAM1 = "hamburguesas";
    private static final int TIPO_HAMBURGUESAS = 3;

    private static ArrayList<Hamburguesa> listaHamburguesas;

    private OnFragmentInteractionListener mListener;

    public Sub_Hamburguesa() {
    }

    public static Sub_Hamburguesa newInstance() {
        return new Sub_Hamburguesa();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listaHamburguesas = (ArrayList<Hamburguesa>) MainActivity.listaHamb;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sub_hamburguesas, container, false);
        Bundle pasaDatos = new Bundle();

        final RecyclerView mRecyclerView = v.findViewById(R.id.rec_hamburguesas_pedido);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        pasaDatos.putSerializable(ARG_PARAM1, (ArrayList<Hamburguesa>) MainActivity.listaHamb);


        RecyclerView.Adapter mAdapter = new MyAdapter(pasaDatos, TIPO_HAMBURGUESAS, new ClickListener() {
            @Override
            public void onPositionClicked(View v, int position) {
                if(v.getId() == R.id.anadir) {
                    CreaPedido2.pedido.getListaHamb().add(new Hamburguesa(listaHamburguesas.get(position)));
                }else if (v.getId() == R.id.quitar && CreaPedido2.pedido.getListaHamb().size() > 0){
                    CreaPedido2.pedido.quitaHamb(listaHamburguesas.get(position).getNombre());
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
        CreaPedido2.pedido.getListaHamb().clear();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
