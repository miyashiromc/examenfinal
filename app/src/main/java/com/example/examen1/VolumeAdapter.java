package com.example.examen1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VolumeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Volume> volumeList;

    public VolumeAdapter(Context context, ArrayList<Volume> volumeList) {
        this.context = context;
        this.volumeList = volumeList;
    }

    @Override
    public int getCount() {
        return volumeList.size();
    }

    @Override
    public Object getItem(int position) {
        return volumeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_volume, parent, false);
        }

        Volume volume = volumeList.get(position);

        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
        TextView textViewYear = convertView.findViewById(R.id.textViewYear);
        TextView textViewVolume = convertView.findViewById(R.id.textViewVolume);
        ImageView imageViewCover = convertView.findViewById(R.id.imageViewCover);

        textViewTitle.setText(volume.getTitle());
        textViewYear.setText("Año: " + volume.getYear());
        textViewVolume.setText("Volumen: " + volume.getVolume());

        Picasso.get().load(volume.getCover()).into(imageViewCover);

        return convertView;
    }
}
