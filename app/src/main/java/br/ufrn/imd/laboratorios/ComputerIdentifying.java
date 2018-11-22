package br.ufrn.imd.laboratorios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ComputerIdentifying extends AppCompatActivity {

    private EditText computerReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_computer_identifying);

        computerReference = findViewById(R.id.computer_reference_txt);
    }

    void identifyDevice(View view) {
        // go on database and search a device
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String reference = computerReference.getText().toString();

        db.collection("devices")
                .whereArrayContains("references", reference)
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshots) {
                        if (!querySnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : querySnapshots) {
                                Intent intent = new Intent(ComputerIdentifying.this, ProblemDefinition.class);
                                intent.putExtra("device", document.getId());

                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Computador não encontrado", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
