package com.example.examen1;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    private List<Journal> journalList;
    private Context context;

    public JournalAdapter(Context context, List<Journal> journalList) {
        this.context = context;
        this.journalList = journalList;
    }

    @Override
    public JournalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_journal, parent, false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JournalViewHolder holder, int position) {
        Journal journal = journalList.get(position);
        holder.textViewName.setText(journal.getName());
        holder.textViewAbbreviation.setText(journal.getAbbreviation());

        // Limpiar la descripción de las etiquetas HTML
        String description = Html.fromHtml(journal.getDescription()).toString();
        holder.textViewDescription.setText(description);

        // Cargar la imagen de la portada con Picasso
        Picasso.get().load(journal.getPortada()).into(holder.imageViewPortada);
    }


    @Override
    public int getItemCount() {
        return journalList.size();
    }

    class JournalViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPortada;
        TextView textViewName;
        TextView textViewAbbreviation;
        TextView textViewDescription;

        JournalViewHolder(View itemView) {
            super(itemView);
            imageViewPortada = itemView.findViewById(R.id.imageViewPortada);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAbbreviation = itemView.findViewById(R.id.textViewAbbreviation);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}
