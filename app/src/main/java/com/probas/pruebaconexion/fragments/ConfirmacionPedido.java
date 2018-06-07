package com.probas.pruebaconexion.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.probas.pruebaconexion.CreaPedido2;
import com.probas.pruebaconexion.R;

import java.util.Locale;


public class ConfirmacionPedido extends DialogFragment {

    private NoticeDialogListener mListener;

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + getString(R.string.excep_notice_dialog_listener));
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CreaPedido2.pedido.calculaTotal();
        builder.setMessage(String.format(Locale.FRANCE,"%s %.2f€", getString(R.string.msq_crea_pedido_dialogo_confped), CreaPedido2.pedido.getTotal()))
                .setPositiveButton(R.string.msg_si_confpedido, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(ConfirmacionPedido.this);
                    }
                })
                .setNegativeButton(R.string.msq_no_confpedido, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(ConfirmacionPedido.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}