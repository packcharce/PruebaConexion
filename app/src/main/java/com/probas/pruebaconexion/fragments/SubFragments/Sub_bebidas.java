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

import com.probas.pruebaconexion.Bebida;
import com.probas.pruebaconexion.CreaPedido2;
import com.probas.pruebaconexion.MainActivity;
import com.probas.pruebaconexion.R;
import com.probas.pruebaconexion.fragments.ClickListener;
import com.probas.pruebaconexion.fragments.MyAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sub_bebidas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sub_bebidas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sub_bebidas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "bebidas";
    private static final int TIPO_BEBIDAS = 2;

    public static ArrayList<Bebida> listaBebidas;

    private OnFragmentInteractionListener mListener;

    public Sub_bebidas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Sub_bebidas.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sub_bebidas, container, false);
        Bundle pasaDatos = new Bundle();

        final RecyclerView mRecyclerView = v.findViewById(R.id.rec_bebidas_pedido);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        pasaDatos.putSerializable(ARG_PARAM1, (ArrayList<Bebida>) MainActivity.listaBebs);


        RecyclerView.Adapter mAdapter = new MyAdapter(pasaDatos, TIPO_BEBIDAS, new ClickListener() {
            @Override
            public void onPositionClicked(View v, int position) {
                if(v.getId() == R.id.anadir) {
                    CreaPedido2.pedido.getListaBebs().add(new Bebida(listaBebidas.get(position)));
                }else if (v.getId() == R.id.quitar && CreaPedido2.pedido.getListaBebs().size() > 0){
                    CreaPedido2.pedido.quitaBebida(MainActivity.listaBebs.get(position).getNombre());
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
        CreaPedido2.pedido.getListaBebs().clear();
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
