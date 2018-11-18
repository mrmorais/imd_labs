package br.ufrn.imd.laboratorios.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("FirebaseMessage", "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d("FirebaseMessage", "Message payload: " + remoteMessage.getData());
        }
    }
}
