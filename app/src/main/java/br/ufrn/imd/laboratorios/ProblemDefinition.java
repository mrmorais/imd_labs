package br.ufrn.imd.laboratorios;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProblemDefinition extends ListActivity {

    public class ProblemType {
        public String title;
        public String description;
        public String slug;

        public ProblemType(String title, String description, String slug) {
            this.title = title;
            this.description = description;
            this.slug = slug;
        }
    }

    private List<ProblemType> problemsTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_problem_definition_screen);

        problemsTypes = new ArrayList<ProblemType>();
        generateProblemsTypes();

        ProblemTypeAdapter adapter = new ProblemTypeAdapter(this, problemsTypes);

        setListAdapter(adapter);
    }

    private void generateProblemsTypes() {
        problemsTypes.add(new ProblemType("Computador não liga", "Ao pressionar o botão de iniciar o computador não dá sinal de inicialização.", "not_power_on"));
        problemsTypes.add(new ProblemType("Monitor não liga", "O gabinete aparenta estar ligado, mas o monitor não liga.", "monitor_not_power_on"));
        problemsTypes.add(new ProblemType("Não consigo logar", "O cabo de rede está devidamente conectado mas não consigo acessar minha conta.", "cant_log_on"));
        problemsTypes.add(new ProblemType("Disco está próximo do limite de armazenamento", "Mensagem mostrada durante a inicialização diz que o computador está perto de atingir a capacidade de armazenamento.", "hd_limit"));
        problemsTypes.add(new ProblemType("Outro problema", "Você poderá detalhar o problema na tela seguinte", "other"));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String problemType = problemsTypes.get(position).slug;

        Intent data = getIntent();
        data.putExtra("problemType", problemType);

        Intent passIntent = new Intent(ProblemDefinition.this, MoreDetails.class);
        passIntent.putExtras(data);

        startActivity(passIntent);
        finish();
    }
}
