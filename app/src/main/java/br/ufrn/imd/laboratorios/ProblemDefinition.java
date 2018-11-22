package br.ufrn.imd.laboratorios;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProblemDefinition extends ListActivity {

    public class ProblemType {
        public String title;
        public String description;

        public ProblemType(String title, String description) {
            this.title = title;
            this.description = description;
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
        problemsTypes.add(new ProblemType("Computador não liga", "Ao pressionar o botão de iniciar o computador não dá sinal de inicialização."));
        problemsTypes.add(new ProblemType("Monitor não liga", "O gabinete aparenta estar ligado, mas o monitor não liga."));
        problemsTypes.add(new ProblemType("Não consigo logar", "O cabo de rede está devidamente conectado mas não consigo acessar minha conta."));
        problemsTypes.add(new ProblemType("Disco está próximo do limite de armazenamento", "Mensagem mostrada durante a inicialização diz que o computador está perto de atingir a capacidade de armazenamento."));
        problemsTypes.add(new ProblemType("Outro problema", "Você poderá detalhar o problema na tela seguinte"));
    }
}
