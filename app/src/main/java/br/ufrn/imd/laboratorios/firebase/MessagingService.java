package br.ufrn.imd.laboratorios.firebase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("FirebaseMessage", "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Map data = remoteMessage.getData();
            if (data.get("type").equals("account")) {
                if (data.get("action").equals("grant")) {
                    try {
                        String grantStr = "grant";
                        FileOutputStream fos = openFileOutput("ac_mem", Context.MODE_PRIVATE);
                        fos.write(grantStr.getBytes());
                        fos.close();
                    } catch (IOException e) {
                        // Sorry
                    }

                    Intent intent = new Intent();
                    intent.setAction("br.ufrn.imd.laboratorios.ACCOUNT_GRANT");
                    sendBroadcast(intent);
                }
            }
            Log.d("FirebaseMessage", "Message payload: " + remoteMessage.getData());
        }
    }
}
