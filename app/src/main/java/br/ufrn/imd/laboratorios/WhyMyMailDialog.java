package br.ufrn.imd.laboratorios;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * This dialog explains the method used to authenticate the user on IMD Labs
 */
public class WhyMyMailDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.why_my_mail_label)
                .setMessage(R.string.why_my_mail_body)
                .setPositiveButton("ENTENDI", null);
        return builder.create();
    }
}
