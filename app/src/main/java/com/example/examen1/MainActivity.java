package com.example.examen1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private JournalAdapter adapter;
    private List<Journal> journalList = new ArrayList<>();
    private static final String TAG = "MainActivity";
    private static final String JOURNALS_URL = "https://revistas.uteq.edu.ec/ws/journals.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewJournals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JournalAdapter(this, journalList);
        recyclerView.setAdapter(adapter);

        // Consumir el servicio web para obtener las revistas
        new FetchJournalsTask().execute(JOURNALS_URL);

        // Configurar el clic en los ítems del RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String journalId = journalList.get(position).getJournalId();
                // Redirigir a VolumeListActivity con el journalId
                Intent intent = new Intent(MainActivity.this, VolumeListActivity.class);
                intent.putExtra("journal_id", journalId); // Pasamos el ID de la revista
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Acción para el clic largo (si es necesario)
            }
        }));
    }

    private class FetchJournalsTask extends AsyncTask<String, Void, List<Journal>> {

        @Override
        protected List<Journal> doInBackground(String... urls) {
            List<Journal> journals = new ArrayList<>();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonBuilder.append(line);
                    }
                    reader.close();
                    String json = jsonBuilder.toString();

                    // Parsear el JSON
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String id = obj.optString("journal_id");
                        String name = obj.optString("name");
                        String abbreviation = obj.optString("abbreviation");
                        String description = obj.optString("description");
                        String portada = obj.optString("portada");
                        journals.add(new Journal(id, name, abbreviation, description, portada));
                    }
                } else {
                    Log.e(TAG, "Código de respuesta: " + responseCode);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error al obtener las revistas", e);
            }
            return journals;
        }

        @Override
        protected void onPostExecute(List<Journal> journals) {
            if (journals != null && !journals.isEmpty()) {
                journalList.clear();
                journalList.addAll(journals);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity.this, "No se encontraron revistas.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
