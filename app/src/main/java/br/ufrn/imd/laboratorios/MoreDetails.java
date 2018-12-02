package br.ufrn.imd.laboratorios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class MoreDetails extends AppCompatActivity {
    private EditText complementaryInfoTextField;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_complementary_information_screen);

        complementaryInfoTextField = findViewById(R.id.complementary_information_textfield);


    }

    public void submitDetailedInfo(View view) {
        String complementaryInfo = complementaryInfoTextField.getText().toString();
        Intent receivedIntent = getIntent();

        String deviceId = receivedIntent.getStringExtra("device");
        String problemType = receivedIntent.getStringExtra("problemType");

        openNewTicket(deviceId, complementaryInfo, problemType);
    }

    public void openNewTicket(String deviceId, String detailedInfo, String problemType) {
        final Map<String, Object> newTicket = new HashMap<>();
        newTicket.put("detailedInfo", detailedInfo);
        newTicket.put("device", deviceId);
        newTicket.put("problemType", problemType);
        newTicket.put("status", 0);

        // get the logged user

        db.collection("users")
            .document(FirebaseInstanceId.getInstance().getId())
            .get()
            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    if (documentSnapshot.getData().get("registered").equals("true")) {
                        newTicket.put("user", documentSnapshot.getId());

                        db.collection("tickets")
                            .add(newTicket)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "Novo ticket aberto!", Toast.LENGTH_SHORT).show();

                                    Intent goHomeIntent = new Intent(MoreDetails.this, HomeScreen.class);
                                    startActivity(goHomeIntent);
                                    finish();
                                }
                            });
                    }
                }
                }
            });
    }
}
