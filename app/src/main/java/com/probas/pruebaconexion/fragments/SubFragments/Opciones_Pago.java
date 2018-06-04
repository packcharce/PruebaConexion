package com.probas.pruebaconexion.fragments.SubFragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
    EditText numTarj, numSec, fechaCad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_opciones_pago, container, false);
        Spinner metoPago = view.findViewById(R.id.spMetPago);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.metodosPago, android.R.layout.simple_spinner_dropdown_item);
        metoPago.setAdapter(adapter);
        metoPago.setOnItemSelectedListener(this);

        paypalCont = view.findViewById(R.id.contPaypal);
        tarjCont = view.findViewById(R.id.contTarjeta);
        efectCont = view.findViewById(R.id.contEfec);

        numTarj = view.findViewById(R.id.num_tarj);
        numTarj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (numTarj.getText().length() != 16)
                        numTarj.setError(getString(R.string.error_longitud_opc_pago));
                }
            }
        });

        numSec = view.findViewById(R.id.num_seg);
        numSec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (numSec.getText().length() != 3)
                        numSec.setError(getString(R.string.error_longitud_3_opc_pago));
                }
            }
        });

        fechaCad = view.findViewById(R.id.fecha_caduc);
        fechaCad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try {
                        int mes = Integer.parseInt(String.format("%s%s", fechaCad.getText().charAt(0), fechaCad.getText().charAt(1)));
                        int anio = Integer.parseInt(String.format("%s%s", fechaCad.getText().charAt(3), fechaCad.getText().charAt(4)));

                        if (fechaCad.getText().charAt(2) == '/') {
                            if (mes > 12 || mes < 1) {
                                fechaCad.setError(getString(R.string.error_mes_opc_pago));
                            } else {
                                Calendar c = Calendar.getInstance();
                                int anio2 = c.get(Calendar.YEAR) % 2000;
                                int mes2 = c.get(Calendar.MONTH);
                                if (((anio == anio2) && (mes < (mes2 + 1))) || (anio < anio2)) {
                                    fechaCad.setError(getString(R.string.error_tarj_caducada_opc_pago));
                                }
                            }
                        }else{
                            fechaCad.setError(getString(R.string.error_formato_opc_pago));
                        }
                    } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                        fechaCad.setError(getString(R.string.error_formato_opc_pago));
                    }
                }
            }
        });

        return view;
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
        void onFragmentInteraction(Uri uri);
    }
}
