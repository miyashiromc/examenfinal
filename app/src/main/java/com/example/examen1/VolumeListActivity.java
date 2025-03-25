package com.example.examen1;

import android.content.Intent;
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

public class VolumeListActivity extends AppCompatActivity {

    private ListView listViewVolumes;
    private VolumeAdapter volumeAdapter;
    private ArrayList<Volume> volumeList;
    private static final String VOLUME_API_URL = "https://revistas.uteq.edu.ec/ws/issues.php?j_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_list);

        listViewVolumes = findViewById(R.id.listViewVolumes);
        volumeList = new ArrayList<>();

        // Obtener el ID de la revista pasada desde la actividad anterior
        String journalId = getIntent().getStringExtra("journal_id");

        // Llamar a la API para obtener los volúmenes de la revista seleccionada
        new FetchVolumesTask().execute(VOLUME_API_URL + journalId);

        // Configurar el clic para redirigir al seleccionar un volumen
        listViewVolumes.setOnItemClickListener((parent, view, position, id) -> {
            String issueId = volumeList.get(position).getVolume(); // Usamos el volumen como issue_id
            // Redirigir a ArticleListActivity con el i_id de la edición seleccionada
            Intent intent = new Intent(VolumeListActivity.this, ArticleListActivity.class);
            intent.putExtra("issue_id", issueId); // Pasamos el issue_id
            startActivity(intent);
        });
    }

    private class FetchVolumesTask extends AsyncTask<String, Void, ArrayList<Volume>> {

        @Override
        protected ArrayList<Volume> doInBackground(String... urls) {
            ArrayList<Volume> volumes = new ArrayList<>();
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

                    // Parsear los volúmenes
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject issue = jsonArray.getJSONObject(i);
                        String volume = issue.getString("volume");
                        String title = issue.getString("title");
                        String year = issue.getString("year");
                        String cover = issue.optString("cover"); // Si hay una portada
                        volumes.add(new Volume(volume, title, year, cover));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return volumes;
        }

        @Override
        protected void onPostExecute(ArrayList<Volume> volumes) {
            if (volumes.isEmpty()) {
                Toast.makeText(VolumeListActivity.this, "No se encontraron volúmenes", Toast.LENGTH_SHORT).show();
            } else {
                volumeAdapter = new VolumeAdapter(VolumeListActivity.this, volumes);
                listViewVolumes.setAdapter(volumeAdapter);
            }
        }
    }
}
