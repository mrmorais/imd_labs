package br.ufrn.imd.laboratorios;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity implements AccountUpdateReceiver.AccountUpdateListenner {
    private EditText emailTextField;
    private AccountUpdateReceiver accountUpdateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        emailTextField = findViewById(R.id.email_txt);
        accountUpdateReceiver = new AccountUpdateReceiver(this);

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

    public void sendValidationEmail(View view) {
        String targetEmail = emailTextField.getText().toString();

        if (targetEmail.length() > 0 && targetEmail.contains("@") && targetEmail.contains(".com")) {
            sendValidationEmail(targetEmail);
            Toast.makeText(getApplicationContext(), "Enviamos um e-mail para você. Siga os passos descritos lá!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "E-mail inválido. Por favor, digite corretamente seu e-mail.", Toast.LENGTH_LONG).show();
        }
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

    private void sendValidationEmail(String email) {
        String fbToken = FirebaseInstanceId.getInstance().getToken();

        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("uid", fbToken);

        FirebaseFunctions functions = FirebaseFunctions.getInstance();
        functions
            .getHttpsCallable("validateUserMail")
            .call(data)
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("FirebaseFunction", "failed");
                }
            })
            .addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
                @Override
                public void onSuccess(HttpsCallableResult httpsCallableResult) {
                    Log.d("FirebaseFunction", "success");
                }
            });
    }

    @Override
    public void onAccountUpdate() {
        Toast.makeText(getApplicationContext(), "Autorização concedida", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (accountUpdateReceiver != null) {
            IntentFilter intentFilter = new IntentFilter("br.ufrn.imd.laboratorios.ACCOUNT_GRANT");
            registerReceiver(accountUpdateReceiver, intentFilter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (accountUpdateReceiver != null) {
            unregisterReceiver(accountUpdateReceiver);
        }
    }
}
