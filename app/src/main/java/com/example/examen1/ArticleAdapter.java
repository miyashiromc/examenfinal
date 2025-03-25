package com.example.examen1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Article> articleList;

    public ArticleAdapter(Context context, ArrayList<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        }

        Article article = articleList.get(position);

        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
        TextView textViewSection = convertView.findViewById(R.id.textViewSection);
        TextView textViewDoi = convertView.findViewById(R.id.textViewDoi);

        textViewTitle.setText(article.getTitle());
        textViewSection.setText("Sección: " + article.getSection());
        textViewDoi.setText("DOI: " + article.getDoi());

        return convertView;
    }
}
