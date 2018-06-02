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

import com.probas.pruebaconexion.CreaPedido2;
import com.probas.pruebaconexion.Ensalada;
import com.probas.pruebaconexion.MainActivity;
import com.probas.pruebaconexion.R;
import com.probas.pruebaconexion.fragments.ClickListener;
import com.probas.pruebaconexion.fragments.MyAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sub_Ensalada.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sub_Ensalada#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sub_Ensalada extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "ensaladas";
    private static final int TIPO_ENSALADAS = 4;

    public static ArrayList<Ensalada> listaEnsaladas;

    private OnFragmentInteractionListener mListener;

    public Sub_Ensalada() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Sub_bebidas.
     */
    // TODO: Rename and change types and number of parameters
    public static Sub_Ensalada newInstance() {
        return new Sub_Ensalada();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listaEnsaladas = (ArrayList<Ensalada>) MainActivity.listaEnsa;
        super.onCreate(savedInstanceState);
    }

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sub_ensalada, container, false);
        Bundle pasaDatos = new Bundle();

        mRecyclerView = v.findViewById(R.id.rec_ensalada_pedido);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        pasaDatos.putSerializable(ARG_PARAM1, (ArrayList<Ensalada>) MainActivity.listaEnsa);


        mAdapter = new MyAdapter(pasaDatos, TIPO_ENSALADAS, new ClickListener() {
            @Override
            public void onPositionClicked(View v, int position) {
                if(v.getId() == R.id.anadir) {
                    CreaPedido2.pedido.getListaEnsa().add(new Ensalada(listaEnsaladas.get(position)));
                }
                else if (v.getId() == R.id.quitar && CreaPedido2.pedido.getListaEnsa().size() > 0){
                    CreaPedido2.pedido.quitaEnsa(listaEnsaladas.get(position).getNombre());
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return v;
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
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        CreaPedido2.pedido.getListaEnsa().clear();
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
