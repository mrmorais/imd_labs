package br.ufrn.imd.laboratorios;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.ufrn.imd.laboratorios.data.Database;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar homeToolbar = findViewById(R.id.home_screen_toolbar);
        setSupportActionBar(homeToolbar);

        getSupportActionBar().setTitle("");


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.exit_menu_action:
                if (Database.removeUserData(getApplicationContext())) {
                    Intent loginScreenIntent = new Intent(this, LoginScreen.class);
                    startActivity(loginScreenIntent);

                    finish();
                }
                return true;
        }

        return false;
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
}
