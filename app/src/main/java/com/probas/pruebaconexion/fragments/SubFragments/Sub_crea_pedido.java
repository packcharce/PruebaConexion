package com.probas.pruebaconexion.fragments.SubFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Space;
import android.widget.Switch;
import android.widget.TextView;

import com.probas.pruebaconexion.Bebida;
import com.probas.pruebaconexion.Ensalada;
import com.probas.pruebaconexion.Hamburguesa;
import com.probas.pruebaconexion.Ingrediente;
import com.probas.pruebaconexion.Lasania;
import com.probas.pruebaconexion.MainActivity;
import com.probas.pruebaconexion.Pasta;
import com.probas.pruebaconexion.R;
import com.probas.pruebaconexion.fragments.ClickListener;
import com.probas.pruebaconexion.fragments.Crea_pedido;
import com.probas.pruebaconexion.fragments.MyAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sub_crea_pedido.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sub_crea_pedido#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sub_crea_pedido extends Fragment {

    // TODO: Rename and change types of parameters
    private static final String ARG_PARAM1 = "ingredientes";
    private static final String ARG_PARAM2 = "bebidas";
    private static final String ARG_PARAM3 = "ensaladas";
    private static final String ARG_PARAM4 = "hamburguesas";
    private static final String ARG_PARAM5 = "pasta";
    private static final String ARG_PARAM6 = "lasania";

    public static ArrayList<Hamburguesa> listaHamb;
    public static ArrayList<Lasania> listaLas;
    public static ArrayList<Ensalada> listaEnsa;

    public static ArrayList<Pasta> listaPasta;
    public static ArrayList<Ingrediente> listaIngredientes;

    private OnFragmentInteractionListener mListener;

    private int numeroDePizza=-1;

    public Sub_crea_pedido() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment Sub_crea_pedido.
     */
    // TODO: Rename and change types and number of parameters
    public static Sub_crea_pedido newInstance(int numeroDePizza) {
        Sub_crea_pedido fragment = new Sub_crea_pedido();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, numeroDePizza);
        /*
        args.putSerializable(ARG_PARAM2, bebidas);
        args.putSerializable(ARG_PARAM3, ensaladas);
        args.putSerializable(ARG_PARAM4, hamburguesas);
        args.putSerializable(ARG_PARAM5, pastas);
        args.putSerializable(ARG_PARAM6, lasanias);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && numeroDePizza == -1) {/*
            listaHamb = (ArrayList<Hamburguesa>) getArguments().getSerializable(ARG_PARAM4);
            listaEnsa = (ArrayList<Ensalada>) getArguments().getSerializable(ARG_PARAM3);
            listaPasta = (ArrayList<Pasta>) getArguments().getSerializable(ARG_PARAM5);
            listaBebs = (ArrayList<Bebida>) getArguments().getSerializable(ARG_PARAM2);
            listaLas = (ArrayList<Lasania>) getArguments().getSerializable(ARG_PARAM6);*/
            numeroDePizza = getArguments().getInt(ARG_PARAM1);
        }
        listaIngredientes = (ArrayList<Ingrediente>) MainActivity.listaIngredientes;
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_sub_crea_pedido, container, false);
        Bundle pasaDatos = new Bundle();

        final RecyclerView mRecyclerView = v.findViewById(R.id.rec_ingredientes_pedido);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        pasaDatos.putSerializable(ARG_PARAM1, listaIngredientes);


        final RecyclerView mRecyclerView2 = v.findViewById(R.id.rec_segunda_mitad);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView2.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(v.getContext());
        mRecyclerView2.setLayoutManager(mLayoutManager2);

        // specify an adapter (see also next example)
        RecyclerView.Adapter mAdapter = new MyAdapter(pasaDatos, 1, new ClickListener() {
            @Override
            public void onPositionClicked(View v, int position) {

                if(v.getId() == R.id.anadir) {
                    Ingrediente i = new Ingrediente(listaIngredientes.get(position));

                    RecyclerView rc = (RecyclerView) v.getParent().getParent();
                    //if(switchMitades.isChecked()) {
                    if (rc.getId() == R.id.rec_segunda_mitad)
                        i.setMitad((byte) 2);
                    //}
                    if (rc.getId() == R.id.rec_ingredientes_pedido)
                        i.setMitad((byte) 1);
                    //System.out.println("Ingredientessss " + rc.getId() + " " + v.toString());
                    Crea_pedido.pedido.getListaPizzas().get(numeroDePizza).agregaIngrediente(i);
                }else if (v.getId() == R.id.quitar && Crea_pedido.pedido.getListaPizzas().get(numeroDePizza).getListaIngredientes().size() > 0){
                    Crea_pedido.pedido.getListaPizzas().get(numeroDePizza).quitaIngrediente(listaIngredientes.get(position).getNombre());
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView2.setAdapter(mAdapter);



        final TextView tituloPrimeraMitad = v.findViewById(R.id.titulo_primera_mitad);
        final TextView tituloSegundaMitad = v.findViewById(R.id.titulo_segunda_mitad);
        Switch switchMitades = v.findViewById(R.id.switchMitades);
        final Space space = v.findViewById(R.id.space_bot_crea_pedido);
        switchMitades.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    tituloPrimeraMitad.setText("Ingredientes Pizza Completa");
                    tituloSegundaMitad.setVisibility(View.GONE);
                    mRecyclerView2.setVisibility(View.GONE);
                    space.setVisibility(View.GONE);
                }
                else {
                    //TODO quitar valores de contadores de segunda mitad
                    tituloPrimeraMitad.setText("Ingredientes Primera Mitad");
                    tituloSegundaMitad.setVisibility(View.VISIBLE);
                    mRecyclerView2.setVisibility(View.VISIBLE);
                    space.setVisibility(View.VISIBLE);
                }
            }
        });

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
