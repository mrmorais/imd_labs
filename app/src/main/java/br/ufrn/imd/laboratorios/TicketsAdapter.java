package br.ufrn.imd.laboratorios;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TicketsAdapter extends ArrayAdapter<HomeScreen.Ticket> {
    private Context context;
    private List<HomeScreen.Ticket> tickets = null;

    public TicketsAdapter(@NonNull Context context, @NonNull List<HomeScreen.Ticket> objects) {
        super(context, 0, objects);
        this.context = context;
        this.tickets = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HomeScreen.Ticket ticket = tickets.get(position);

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.ticket_item, null);

        TextView ticketTitle = convertView.findViewById(R.id.ticket_title);
        TextView ticketDesc = convertView.findViewById(R.id.ticket_desc);
        FloatingActionButton ballon = convertView.findViewById(R.id.ticket_balloon);

        ballon.setBackgroundColor(ticket.color);
        ticketTitle.setText(ticket.device);
        ticketDesc.setText(ticket.status);

        return convertView;
    }
}
