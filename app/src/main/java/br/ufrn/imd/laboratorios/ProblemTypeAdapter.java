package br.ufrn.imd.laboratorios;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ProblemTypeAdapter extends ArrayAdapter<ProblemDefinition.ProblemType> {
    private Context context;
    private List<ProblemDefinition.ProblemType> problemTypes = null;

    public ProblemTypeAdapter(@NonNull Context context, @NonNull List<ProblemDefinition.ProblemType> objects) {
        super(context, 0, objects);
        this.problemTypes = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProblemDefinition.ProblemType problemType = problemTypes.get(position);

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.problem_type_item, null);

        TextView title = convertView.findViewById(R.id.title_problem_type_item);
        TextView description = convertView.findViewById(R.id.description_problem_type_item);

        title.setText(problemType.title);
        description.setText(problemType.description);

        return convertView;
    }
}
