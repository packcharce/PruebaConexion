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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sub_Pasta.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sub_Pasta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sub_Pasta extends Fragment {
    private static final String ARG_PARAM1 = "pasta";
    private static final int TIPO_PASTA = 5;

    private static ArrayList<Pasta> listaPasta;

    private OnFragmentInteractionListener mListener;

    public Sub_Pasta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Sub_bebidas.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sub_pasta, container, false);
        Bundle pasaDatos = new Bundle();

        final RecyclerView mRecyclerView = v.findViewById(R.id.rec_pasta_pedido);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
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
}
