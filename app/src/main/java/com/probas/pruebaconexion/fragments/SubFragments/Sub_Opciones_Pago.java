/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.fragments.SubFragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.probas.pruebaconexion.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sub_Opciones_Pago.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sub_Opciones_Pago#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sub_Opciones_Pago extends android.app.Fragment implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {


    public static boolean readyToPay;
    private OnFragmentInteractionListener mListener;
    private LinearLayout paypalCont;
    private LinearLayout tarjCont;
    private LinearLayout efectCont;
    private EditText emailPaypal, pass_paypal;
    private EditText numSec, numTarj;
    private EditText fechaCad;

    public Sub_Opciones_Pago() {
        // Required empty public constructor
    }

    public static Sub_Opciones_Pago newInstance() {
        return new Sub_Opciones_Pago();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sub_opciones_pago, container, false);
        Spinner metoPago = view.findViewById(R.id.spMetPago);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.metodosPago, android.R.layout.simple_spinner_dropdown_item);
        metoPago.setAdapter(adapter);
        metoPago.setOnItemSelectedListener(this);

        paypalCont = view.findViewById(R.id.contPaypal);
        tarjCont = view.findViewById(R.id.contTarjeta);
        efectCont = view.findViewById(R.id.contEfec);

        numTarj = view.findViewById(R.id.num_tarj);
        numSec = view.findViewById(R.id.num_seg);
        fechaCad = view.findViewById(R.id.fecha_caduc);

        emailPaypal = view.findViewById(R.id.email_paypal);
        pass_paypal = view.findViewById(R.id.pass_paypal);

        readyToPay = false;

        return view;
    }

    private void ponerListeners(int cont) {
        switch (cont) {
            case 0:
                emailPaypal.setOnFocusChangeListener(this);
                pass_paypal.setOnFocusChangeListener(this);
                break;
            case 1:
                numTarj.setOnFocusChangeListener(this);
                numSec.setOnFocusChangeListener(this);
                fechaCad.setOnFocusChangeListener(this);
                break;
        }
    }

    private void quitaListeners(int cont) {
        switch (cont) {
            case 0:
                emailPaypal.setOnFocusChangeListener(null);
                pass_paypal.setOnFocusChangeListener(null);
                break;
            case 1:
                numTarj.setOnFocusChangeListener(null);
                numSec.setOnFocusChangeListener(null);
                fechaCad.setOnFocusChangeListener(null);
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            try {
                switch (v.getId()) {
                    case R.id.fecha_caduc:
                        int mes = Integer.parseInt(String.format("%s%s", fechaCad.getText().charAt(0), fechaCad.getText().charAt(1)));
                        int anio = Integer.parseInt(String.format("%s%s", fechaCad.getText().charAt(3), fechaCad.getText().charAt(4)));

                        if (fechaCad.getText().charAt(2) == '/') {
                            if (mes > 12 || mes < 1) {
                                fechaCad.setError(getString(R.string.error_mes_opc_pago));
                                readyToPay = false;
                            } else {
                                int anio2 = Calendar.getInstance().get(Calendar.YEAR) % 2000;
                                int mes2 = Calendar.getInstance().get(Calendar.MONTH);
                                if (((anio == anio2) && (mes < (mes2 + 1))) || (anio < anio2)) {
                                    fechaCad.setError(getString(R.string.error_tarj_caducada_opc_pago));
                                    readyToPay = false;
                                } else {
                                    readyToPay = true;
                                }
                            }
                        } else {
                            readyToPay = false;
                            fechaCad.setError(getString(R.string.error_formato_opc_pago));
                        }

                        break;
                    case R.id.num_seg:
                        if (numSec.getText().length() != 3) {
                            numSec.setError(getString(R.string.error_longitud_3_opc_pago));
                            readyToPay = false;
                        } else {
                            readyToPay = true;
                        }
                        break;
                    case R.id.num_tarj:
                        if (numTarj.getText().length() != 16) {
                            numTarj.setError(getString(R.string.error_longitud_opc_pago));
                            readyToPay = false;
                        }
                        break;
                    case R.id.email_paypal:
                        if (!isValidEmail(emailPaypal.getText())) {
                            emailPaypal.setError("Formato de email incorrecto");
                            readyToPay = false;
                        } else {
                            readyToPay = true;
                        }
                        break;
                    case R.id.pass_paypal:
                        if (pass_paypal.getText().length() == 0) {
                            pass_paypal.setError("Contraseña incorrecta");
                            readyToPay = false;
                        } else {
                            readyToPay = true;
                        }
                        break;
                }

            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                fechaCad.setError(getString(R.string.error_formato_opc_pago));
                readyToPay = false;
            }
        }
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                efectCont.setVisibility(View.GONE);
                tarjCont.setVisibility(View.GONE);
                paypalCont.setVisibility(View.GONE);
                readyToPay = false;
                quitaListeners(0);
                quitaListeners(1);
                break;
            case 2:
                quitaListeners(1);
                ponerListeners(0);
                readyToPay = false;
                efectCont.setVisibility(View.GONE);
                tarjCont.setVisibility(View.GONE);
                paypalCont.setVisibility(View.VISIBLE);
                break;
            case 1:
                quitaListeners(0);
                ponerListeners(1);
                readyToPay = false;
                efectCont.setVisibility(View.GONE);
                paypalCont.setVisibility(View.GONE);
                tarjCont.setVisibility(View.VISIBLE);
                break;
            case 3:
                quitaListeners(0);
                quitaListeners(1);
                readyToPay = true;
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
        void onFragmentInteraction(Uri uri);
    }
}
