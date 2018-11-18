package br.ufrn.imd.laboratorios;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        createNotificationChannel();
    }

    public void openHowItWorksDialog(View view) {
        HowItWorksDialog dialog = new HowItWorksDialog();
        dialog.show(getSupportFragmentManager(), "how_it_works_dialog");
    }

    public void openWhyMyMail(View view) {
        WhyMyMailDialog dialog = new WhyMyMailDialog();
        dialog.show(getSupportFragmentManager(), "why_my_mail_dialog");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("main", "Canal padrão de notificação", importance);
            channel.setDescription("Todas as notificações do aplicativo serão exibidas através deste canal");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
