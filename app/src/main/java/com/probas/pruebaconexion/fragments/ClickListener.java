/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion.fragments;

import android.view.View;

/**
 * Interfaz para poder saber que boton ha sido pulsado dentro de un RecyclerView
 */
public interface ClickListener {
    void onPositionClicked(View v, int position);
}
