package com.probas.pruebaconexion.fragments.SubFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.probas.pruebaconexion.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Opciones_Pago.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Opciones_Pago#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Opciones_Pago extends android.app.Fragment implements AdapterView.OnItemSelectedListener {


    private OnFragmentInteractionListener mListener;

    public Opciones_Pago() {
        // Required empty public constructor
    }

    public static Opciones_Pago newInstance() {
        return new Opciones_Pago();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    LinearLayout paypalCont, tarjCont, efectCont;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_opciones_pago, container, false);
        Spinner metoPago = view.findViewById(R.id.spMetPago);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.metodosPago, android.R.layout.simple_spinner_dropdown_item);
        metoPago.setAdapter(adapter);
        metoPago.setOnItemSelectedListener(this);
        getActivity().setTitle("Opciones de Pago");

        //TODO meter el precio en el textview efectivo para preguntar si tiene cambio

        paypalCont = view.findViewById(R.id.contPaypal);
        tarjCont = view.findViewById(R.id.contTarjeta);
        efectCont = view.findViewById(R.id.contEfec);
        return view;
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                break;
            case 2:
                efectCont.setVisibility(View.GONE);
                tarjCont.setVisibility(View.GONE);
                paypalCont.setVisibility(View.VISIBLE);
                break;
            case 1:
                efectCont.setVisibility(View.GONE);
                paypalCont.setVisibility(View.GONE);
                tarjCont.setVisibility(View.VISIBLE);
                break;
            case 3:
                paypalCont.setVisibility(View.GONE);
                tarjCont.setVisibility(View.GONE);
                efectCont.setVisibility(View.VISIBLE);
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
}
