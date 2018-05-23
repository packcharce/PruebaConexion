package com.probas.pruebaconexion.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.probas.pruebaconexion.MainActivity;
import com.probas.pruebaconexion.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Datos_cliente.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Datos_cliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Datos_cliente extends Fragment {


    private EditText
            editTextNombre,
            editTextApellido1,
            editTextTlfno,
            editTextCalle,
            editTextPortal,
            editTextPiso,
            editTextPuerta,
            editTextUrbanizacion,
            editTextCodPostal;
    private Button buttonRegistro;

    private OnFragmentInteractionListener mListener;

    public Datos_cliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Datos_cliente.
     */
    // TODO: Rename and change types and number of parameters
    public static Datos_cliente newInstance() {
        Datos_cliente fragment = new Datos_cliente();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos_cliente, container, false);

        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextApellido1 = view.findViewById(R.id.editTextApellido1);
        editTextTlfno = view.findViewById(R.id.editTextTlfno);
        editTextCalle = view.findViewById(R.id.editTextCalle);
        editTextPortal = view.findViewById(R.id.editTextPortal);
        editTextPiso = view.findViewById(R.id.editTextPiso);
        editTextPuerta = view.findViewById(R.id.editTextPuerta);
        editTextUrbanizacion = view.findViewById(R.id.editTextUrbanizacion);
        editTextCodPostal = view.findViewById(R.id.editTextCodPostal);

        editTextNombre.setText(MainActivity.clienteActivo.getNombre());
        editTextApellido1.setText(MainActivity.clienteActivo.getApellido());
        editTextTlfno.setText(MainActivity.clienteActivo.getTlfno());
        editTextCalle.setText(MainActivity.clienteActivo.getCalle());
        editTextPortal.setText(MainActivity.clienteActivo.getPortal());
        editTextPiso.setText(MainActivity.clienteActivo.getPiso());
        editTextPuerta.setText(MainActivity.clienteActivo.getPuerta());
        editTextUrbanizacion.setText(MainActivity.clienteActivo.getUrbanizacion());
        editTextCodPostal.setText(MainActivity.clienteActivo.getCodigoPostal());

        buttonRegistro = view.findViewById(R.id.buttonAddUpdate);

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO poner metodo update
                //crearCliente();
                System.out.println("Actualizado");
            }
        });
        getActivity().setTitle("Mi Perfil");
        // Inflate the layout for this fragment
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
