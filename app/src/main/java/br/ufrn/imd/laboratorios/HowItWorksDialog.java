package br.ufrn.imd.laboratorios;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * The how it works display to the user a general definition of the problem and how the
 * application aims to solve it. It is a Dialog, that means that a little screen will be
 * showed above the current layout
 */
public class HowItWorksDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.how_it_works)
                .setMessage(R.string.dialog_how_it_works_body)
                .setPositiveButton("ENTENDI", null);
        return builder.create();
    }
}
