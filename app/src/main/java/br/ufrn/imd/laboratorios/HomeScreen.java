package br.ufrn.imd.laboratorios;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    public class Ticket {
        public String device;
        public String status;
        public int color;

        public Ticket(String device, String status, int color) {
            this.device = device;
            this.status = status;
            this.color = color;
        }
    }

    ListView list;
    TicketsAdapter ticketsAdapter;
    List<Ticket> arrayList;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar homeToolbar = findViewById(R.id.home_screen_toolbar);
        setSupportActionBar(homeToolbar);

        getSupportActionBar().setTitle("");

        db = FirebaseFirestore.getInstance();

        list = findViewById(R.id.list);
        arrayList = new ArrayList<Ticket>();

        loadTicketsFromDatabase();

        ticketsAdapter = new TicketsAdapter(this, arrayList);
        list.setAdapter(ticketsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    public void openNewTicket(View view) {
        Intent computerIdentifyingIntent = new Intent(this, ComputerIdentifying.class);
        startActivity(computerIdentifyingIntent);
        finish();
    }

    public void loadTicketsFromDatabase() {
        db.collection("tickets")
                .whereEqualTo("user", FirebaseInstanceId.getInstance().getId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot ticket : queryDocumentSnapshots) {
                            String status = "Indefinido";
                            int balloonColor = android.R.color.darker_gray;

                            if (Integer.parseInt(ticket.getData().get("status").toString()) == 0) {
                                status = "Aguardando recepção";
                                balloonColor = android.R.color.holo_orange_light;
                            } else if (Integer.parseInt(ticket.getData().get("status").toString()) == 1) {
                                status = "Atendido com sucesso";
                                balloonColor = android.R.color.holo_green_light;
                            } else if (Integer.parseInt(ticket.getData().get("status").toString()) == 2) {
                                status = "Atendido sem sucesso";
                                balloonColor = android.R.color.holo_red_light;
                            }


                            final int finalBalloonColor = balloonColor;
                            final String finalStatus = status;
                            db.collection("devices")
                                    .document(ticket.getData().get("device").toString())
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot device) {
                                            if (device.exists()) {
                                                arrayList.add(new Ticket(
                                                        device.getData().get("name").toString(),
                                                        finalStatus,
                                                        finalBalloonColor
                                                ));
                                                ticketsAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
//                        ticketsAdapter.notifyDataSetChanged();
                    }
                });

    }
}
