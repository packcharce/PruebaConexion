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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.probas.pruebaconexion.ClasesBasicas.Ingrediente;
import com.probas.pruebaconexion.CreaPedido2;
import com.probas.pruebaconexion.MainActivity;
import com.probas.pruebaconexion.R;
import com.probas.pruebaconexion.fragments.ClickListener;
import com.probas.pruebaconexion.fragments.MyAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sub_crea_pedido.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sub_crea_pedido#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sub_crea_pedido extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename and change types of parameters
    private static final String ARG_PARAM1 = "ingredientes";
    private static final int TIPO_INGREDIENTES = 1;

    private static ArrayList<Ingrediente> listaIngredientes;

    private OnFragmentInteractionListener mListener;

    private int numeroDePizza = 0;

    public Sub_crea_pedido() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Sub_crea_pedido.
     */
    // TODO: Rename and change types and number of parameters
    public static Sub_crea_pedido newInstance(int numeroDePizza) {
        Sub_crea_pedido fragment = new Sub_crea_pedido();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, numeroDePizza);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numeroDePizza = getArguments().getInt(ARG_PARAM1);
        }
        listaIngredientes = (ArrayList<Ingrediente>) MainActivity.listaIngredientes;
        creaPizzaPref();
    }

    RecyclerView mRecyclerView, mRecyclerView2;
    RecyclerView.LayoutManager mLayoutManager;
    Switch switchMitades;
    Spinner spinner;
    View v;
    TextView dividir, tituloGeneral, tituloSegMitad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_sub_crea_pedido, container, false);

        spinner = v.findViewById(R.id.spinner_pizzas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.listaPizzasSpinner, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        dividir = v.findViewById(R.id.dividir);
        tituloGeneral = v.findViewById(R.id.titulo_primera_mitad);
        tituloSegMitad = v.findViewById(R.id.titulo_segunda_mitad);
        dividir.setVisibility(View.GONE);
        tituloGeneral.setVisibility(View.GONE);

        switchMitades = v.findViewById(R.id.switchMitades);
        switchMitades.setVisibility(View.GONE);
        getActivity().setTitle(getString(R.string.tit_crear_pedido_crea_pedido));
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    RecyclerView.Adapter mAdapter;

    private void cargaRecyclers() {

        switchMitades.setVisibility(View.VISIBLE);
        dividir.setVisibility(View.VISIBLE);
        tituloGeneral.setVisibility(View.VISIBLE);
        mRecyclerView = v.findViewById(R.id.rec_ingredientes_pedido);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView2 = v.findViewById(R.id.rec_segunda_mitad);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView2.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(v.getContext());
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        mRecyclerView2.setVisibility(View.GONE);


        Bundle pasaDatos = new Bundle();

        pasaDatos.putSerializable(ARG_PARAM1, listaIngredientes);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(pasaDatos, TIPO_INGREDIENTES, new ClickListener() {
            @Override
            public void onPositionClicked(View v, int position) {

                if (v.getId() == R.id.anadir) {
                    Ingrediente i = new Ingrediente(listaIngredientes.get(position));

                    RecyclerView rc = (RecyclerView) v.getParent().getParent();
                    if (rc.getId() == R.id.rec_segunda_mitad)
                        i.setMitad((byte) 2);
                    if (rc.getId() == R.id.rec_ingredientes_pedido)
                        i.setMitad((byte) 1);
                    CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).agregaIngrediente(i);
                } else if (v.getId() == R.id.quitar && CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).getListaIngredientes().size() > 0) {
                    CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).quitaIngrediente(listaIngredientes.get(position).getNombre());
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView2.setAdapter(mAdapter);


        switchMitades.setChecked(false);
        final Space space = v.findViewById(R.id.space_bot_crea_pedido);
        space.setVisibility(View.GONE);
        switchMitades.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    tituloGeneral.setText(R.string.tit_ing_pizza_compl_crea_pedido);
                    tituloSegMitad.setVisibility(View.GONE);
                    mRecyclerView2.setVisibility(View.GONE);
                    space.setVisibility(View.GONE);
                    for (int i = CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).getListaIngredientes().size() - 1; i >= 0; i--) {
                        if (CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).getListaIngredientes().get(i).getMitad() != 1) {
                            CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).getListaIngredientes().remove(i);
                        }
                    }
                    mRecyclerView2.setAdapter(null);
                } else {
                    tituloGeneral.setText(R.string.tit_ing_prim_mitad_crea_pedido);
                    tituloSegMitad.setVisibility(View.VISIBLE);
                    mRecyclerView2.setAdapter(mAdapter);
                    mRecyclerView2.setVisibility(View.VISIBLE);
                    space.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    private void quitaRecyclers() {
        if (mRecyclerView2 != null && mRecyclerView != null) {
            dividir.setVisibility(View.GONE);
            tituloGeneral.setVisibility(View.GONE);
            tituloSegMitad.setVisibility(View.GONE);
            mRecyclerView.setAdapter(null);
            mRecyclerView2.setAdapter(null);
            switchMitades.setVisibility(View.GONE);
            CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).getListaIngredientes().clear();
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

    byte pizzaActual = -1;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                quitaIngrPizzaPref();
                pizzaActual = -1;
                break;
            case 1:
                quitaIngrPizzaPref();
                pizzaActual = 1;
                quitaRecyclers();
                cargaIngrPref(pizzaBarbacoa);
                CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).setNombre(getResources().getStringArray(R.array.listaPizzasSpinner)[position]);
                break;
            case 2:
                quitaIngrPizzaPref();
                pizzaActual = 2;
                quitaRecyclers();
                cargaIngrPref(pizzaMargarita);
                CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).setNombre(getResources().getStringArray(R.array.listaPizzasSpinner)[position]);
                break;
            case 3:
                quitaIngrPizzaPref();
                pizzaActual = 3;
                quitaRecyclers();
                cargaIngrPref(cQuesos);
                CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).setNombre(getResources().getStringArray(R.array.listaPizzasSpinner)[position]);
                break;
            case 4:
                quitaIngrPizzaPref();
                cargaRecyclers();
                CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).setNombre(getResources().getStringArray(R.array.listaPizzasSpinner)[position]);
                break;
        }


    }

    private void cargaIngrPref(List<Ingrediente> listaIngreds) {
        for (Ingrediente i : listaIngreds
                ) {
            CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).agregaIngrediente(i);
        }
    }

    List<Ingrediente> pizzaBarbacoa, pizzaMargarita, cQuesos;

    private void creaPizzaPref() {
        pizzaBarbacoa = new LinkedList<>();
        pizzaMargarita = new LinkedList<>();
        cQuesos = new LinkedList<>();

        for (Ingrediente i : listaIngredientes) {
            switch (i.getNombre()) {
                case "Queso mozzarela":
                    pizzaBarbacoa.add(i);
                    cQuesos.add(i);
                    break;
                case "4 Quesos":
                    cQuesos.add(i);
                    break;
                case "Carne Picada":
                    pizzaBarbacoa.add(i);
                    break;
                case "Bacon":
                    pizzaBarbacoa.add(i);
                    break;
                case "Tomate":
                    pizzaMargarita.add(i);
                    cQuesos.add(i);
                    pizzaBarbacoa.add(i);
                    break;
            }
        }
    }

    private void quitaIngrPizzaPref() {
        switch (pizzaActual){
            case 1:
                for (Ingrediente i : pizzaBarbacoa) {
                    CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).quitaIngrediente(i.getNombre());
                }
                break;
            case 2:
                for (Ingrediente i : pizzaMargarita) {
                    CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).quitaIngrediente(i.getNombre());
                }
                break;
            case 3:
                for (Ingrediente i : cQuesos) {
                    CreaPedido2.pedido.getListaPizzas().get(numeroDePizza).quitaIngrediente(i.getNombre());
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    @Override
    public void onPause() {
        super.onPause();
        if (mRecyclerView2 != null && switchMitades != null) {
            mRecyclerView2.setVisibility(View.GONE);
            switchMitades.setChecked(false);
        }
    }
}
