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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.laboratorios.data.Database;

public class LoginScreen extends AppCompatActivity implements AccountUpdateReceiver.AccountUpdateListenner {
    private EditText emailTextField;
    private AccountUpdateReceiver accountUpdateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen); // define layout

        emailTextField = findViewById(R.id.email_txt);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(FirebaseInstanceId.getInstance().getId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            if (documentSnapshot.getData().get("registered").equals("true")) {
                                goToHome();
                            }
                        }
                    }
                });

        // enable update receiver
        accountUpdateReceiver = new AccountUpdateReceiver(this);
        IntentFilter intentFilter = new IntentFilter("br.ufrn.imd.laboratorios.ACCOUNT_GRANT");
        registerReceiver(accountUpdateReceiver, intentFilter);

        createNotificationChannel();
    }

    /**
     * Open "how it works" dialog
     */
    public void openHowItWorksDialog(View view) {
        HowItWorksDialog dialog = new HowItWorksDialog();
        dialog.show(getSupportFragmentManager(), "how_it_works_dialog");
    }

    /**
     * Open "why my mail" dialog
     */
    public void openWhyMyMail(View view) {
        WhyMyMailDialog dialog = new WhyMyMailDialog();
        dialog.show(getSupportFragmentManager(), "why_my_mail_dialog");
    }

    /**
     * Trigger a mail sending
     */
    public void sendValidationEmail(View view) {
        String targetEmail = emailTextField.getText().toString();

        if (targetEmail.length() > 0 && targetEmail.contains("@") && targetEmail.contains(".com")) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, String> user = new HashMap<>();
            user.put("email", targetEmail);
            user.put("tokenID", FirebaseInstanceId.getInstance().getToken());
            user.put("registered", "false");

            db.collection("users")
                    .document(FirebaseInstanceId.getInstance().getId())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            sendValidationEmail(emailTextField.getText().toString(), FirebaseInstanceId.getInstance().getId());
                        }
                    });


            Toast.makeText(getApplicationContext(), "Enviamos um e-mail para você. Siga os passos descritos lá!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "E-mail inválido. Por favor, digite corretamente seu e-mail.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Call validating mail sending cloud function
     * @param email user's email
     */
    private void sendValidationEmail(String email, String userId) {

        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("uid", userId);

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

    /**
     * Create or override notification channel if device API version is greater than or equals to 28
     */
    private void createNotificationChannel() {
        // Notification channel is a feature for API 28
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("main", "Canal padrão de notificação", importance);
            channel.setDescription("Todas as notificações do aplicativo serão exibidas através deste canal");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Trigger when receives a Account Grant action
     */
    @Override
    public void onAccountUpdate(Intent intent) {
        Toast.makeText(getApplicationContext(), "E-mail verificado", Toast.LENGTH_LONG).show();

        goToHome();
    }

    /**
     * Unsubscribe account receiver
     */
    @Override
    protected void onDestroy() {
        if (accountUpdateReceiver != null) {
            unregisterReceiver(accountUpdateReceiver);
        }
        super.onDestroy();
    }

    private void goToHome() {
        Intent homeScreenIntent = new Intent(this, HomeScreen.class);
        startActivity(homeScreenIntent);

        finish();
    }
}
