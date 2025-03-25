package com.example.examen1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ArticleListActivity extends AppCompatActivity {

    private ListView listViewArticles;
    private ArticleAdapter articleAdapter;
    private ArrayList<Article> articleList;
    private static final String ARTICLE_API_URL = "https://revistas.uteq.edu.ec/ws/pubs.php?i_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        listViewArticles = findViewById(R.id.listViewArticles);
        articleList = new ArrayList<>();

        // Obtener el ID de la edición seleccionada desde el Intent
        String issueId = getIntent().getStringExtra("issue_id");

        // Llamar a la API para obtener los artículos de la edición seleccionada
        new FetchArticlesTask().execute(ARTICLE_API_URL + issueId);
    }

    private class FetchArticlesTask extends AsyncTask<String, Void, ArrayList<Article>> {

        @Override
        protected ArrayList<Article> doInBackground(String... urls) {
            ArrayList<Article> articles = new ArrayList<>();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonBuilder.append(line);
                    }
                    reader.close();

                    String json = jsonBuilder.toString();
                    JSONArray jsonArray = new JSONArray(json);

                    // Parsear los artículos
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject articleObject = jsonArray.getJSONObject(i);
                        String title = articleObject.optString("title");
                        String section = articleObject.optString("section");
                        String doi = articleObject.optString("doi");
                        String abstractText = articleObject.optString("abstract");

                        Article article = new Article(title, section, doi, abstractText);
                        articles.add(article);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return articles;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> articles) {
            if (articles.isEmpty()) {
                Toast.makeText(ArticleListActivity.this, "No se encontraron artículos", Toast.LENGTH_SHORT).show();
            } else {
                articleAdapter = new ArticleAdapter(ArticleListActivity.this, articles);
                listViewArticles.setAdapter(articleAdapter);
            }
        }
    }
}
