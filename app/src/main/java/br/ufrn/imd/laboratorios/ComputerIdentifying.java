package br.ufrn.imd.laboratorios;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ComputerIdentifying extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_computer_identifying);

        Toolbar tb = findViewById(R.id.computer_identifying_toolbar);
        tb.setTitle(R.string.title_computer_identifying);
        tb.setTitleTextColor(getResources().getColor(android.R.color.white));


        setSupportActionBar(tb);
    }
}
