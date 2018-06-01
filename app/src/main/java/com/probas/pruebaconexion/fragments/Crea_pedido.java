package com.probas.pruebaconexion.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.probas.pruebaconexion.Pedido;
import com.probas.pruebaconexion.Pizza;
import com.probas.pruebaconexion.R;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Ensalada;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Hamburguesa;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Lasania;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_Pasta;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_bebidas;
import com.probas.pruebaconexion.fragments.SubFragments.Sub_crea_pedido;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Crea_pedido.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Crea_pedido#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Crea_pedido extends android.app.Fragment implements
        Sub_crea_pedido.OnFragmentInteractionListener,
        Sub_bebidas.OnFragmentInteractionListener,
        Sub_Hamburguesa.OnFragmentInteractionListener,
        Sub_Lasania.OnFragmentInteractionListener,
        Sub_Ensalada.OnFragmentInteractionListener,
        Sub_Pasta.OnFragmentInteractionListener,
        ConfirmacionPedido.NoticeDialogListener
{

    private OnFragmentInteractionListener mListener;
    public static Pedido pedido;

    private int numeroDePizza;
    ArrayList<android.app.Fragment> listaFragments = new ArrayList<>();

    TextView contPizzas;
    Button siguiente;
    Button anterior;

    private int fasePedido, fasesTotales;
    private final int numeroFasesProtegidas = 5;


    public Crea_pedido() {
        // Required empty public constructor
    }

    public static Crea_pedido newInstance() {
        return new Crea_pedido();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fasePedido = 0;
        numeroDePizza=0;
        pedido = new Pedido();
        pedido.getListaPizzas().put(numeroDePizza, new Pizza("none"));

        listaFragments = new ArrayList<>();
        fasePedido = 0;
        numeroDePizza = 0;

        cargaFragments();
    }


    private void cargaFragments(){
        listaFragments.add(Sub_crea_pedido.newInstance(numeroDePizza));
        listaFragments.add(Sub_bebidas.newInstance());
        listaFragments.add(Sub_Hamburguesa.newInstance());
        listaFragments.add(Sub_Lasania.newInstance());
        listaFragments.add(Sub_Ensalada.newInstance());
        listaFragments.add(Sub_Pasta.newInstance());
        fasesTotales = listaFragments.size();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_crea_pedido, container, false);

        siguiente = v.findViewById(R.id.btnSig);
        anterior = v.findViewById(R.id.btnAnterior);
        Button anhadePizza = v.findViewById(R.id.btn_anhade_pizza);
        Button quitaPizza = v.findViewById(R.id.btn_quita_pizza);
        contPizzas = v.findViewById(R.id.tv_muestra_cant_pizzas);

        anhadePizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroDePizza++;
                pedido.getListaPizzas().put(numeroDePizza, new Pizza("none"));

                contPizzas.setText(
                        String.valueOf(Integer.parseInt(contPizzas.getText().toString()) + 1));

                listaFragments.add(fasesTotales - numeroFasesProtegidas, Sub_crea_pedido.newInstance(numeroDePizza));

                fasesTotales++;
            }
        });

        quitaPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pedido.getListaPizzas().size() > 1) {
                    if (fasePedido < fasesTotales - numeroFasesProtegidas) {
                        Object[] aux = pedido.getListaPizzas().keySet().toArray();
                        pedido.getListaPizzas().remove(aux[fasePedido]);

                        contPizzas.setText(
                                String.valueOf(Integer.parseInt(contPizzas.getText().toString()) - 1));

                        listaFragments.remove(fasePedido);
                        if (fasePedido == 0)
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.fragment2, listaFragments.get(fasePedido))
                                    .commit();
                        else
                            anterior.performClick();


                        fasesTotales--;
                    } else {
                        Toast.makeText(getActivity(), "Solo puedes borrar pizzas", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        anterior.setVisibility(View.GONE);
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fasePedido > 0) {
                    fasePedido--;

                    getChildFragmentManager().popBackStack();


                    if (fasePedido <= 0) anterior.setVisibility(View.GONE);
                }
                siguiente.setVisibility(View.VISIBLE);
            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fasePedido < listaFragments.size() - 1) {
                    fasePedido++;

                    getChildFragmentManager().beginTransaction()
                            .add(R.id.fragment2, listaFragments.get(fasePedido))
                            .hide(listaFragments.get(fasePedido - 1))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null)
                            .commit();


                } else {
                    DialogFragment dialog = new ConfirmacionPedido();
                    dialog.show(getChildFragmentManager(), "NoticeDialogFragment");
                }
                anterior.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
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

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
