package com.probas.pruebaconexion.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.probas.pruebaconexion.R;

import java.util.ArrayList;


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


        RecyclerView mRecyclerView = v.findViewById(R.id.rec_view_misPedidos);

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
        RecyclerView.Adapter mAdapter = new MyAdapter(b, 0, new ClickListener() {
            @Override
            public void onPositionClicked(View v, int position) {
                System.out.println(position + " Mis Pedidos");
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        getActivity().setTitle("Historial de Pedidos");
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
                    + " must implement OnFragmentInteractionListener");
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

}
