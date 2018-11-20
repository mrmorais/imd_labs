package br.ufrn.imd.laboratorios;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AccountUpdateReceiver extends BroadcastReceiver {
    public interface AccountUpdateListenner {
        public void onAccountUpdate(Intent intent);
    }

    AccountUpdateListenner listenner;

    public AccountUpdateReceiver(AccountUpdateListenner listenner) {
        this.listenner = listenner;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("br.ufrn.imd.laboratorios.ACCOUNT_GRANT")) {
            if (listenner != null) {
                listenner.onAccountUpdate(intent);
            }
        }
    }
}