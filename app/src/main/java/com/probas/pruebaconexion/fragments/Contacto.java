/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.probas.pruebaconexion.R;

/**
 * Clase con mapa para los datos de contacto de la empresa
 */
public class Contacto extends android.app.Fragment implements OnMapReadyCallback {

    private MapView mapa;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    private OnFragmentInteractionListener mListener;

    public Contacto() {
        // Required empty public constructor
    }

    public static Contacto newInstance() {
        return new Contacto();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contacto, container, false);

        mapa = v.findViewById(R.id.mapa_contacto);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapa.onCreate(mapViewBundle);
        mapa.getMapAsync(this);

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
                    + getString(R.string.excep_notice_dialog_listener));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMinZoomPreference(15);
        googleMap.setMaxZoomPreference(35);

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);

        LatLng fp = new LatLng(43.4576312, -3.809886);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(fp);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(fp));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapa.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapa.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapa.onStop();
    }
    @Override
    public void onPause() {
        mapa.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy() {
        mapa.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapa.onLowMemory();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapa.onSaveInstanceState(mapViewBundle);
    }

}
