package com.calculate.fleamarket;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String username = "MoshamJul";
    private ListView listView;
    private ArrayAdapter<Article> adapter;
    private List<Article> articles = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);


        bindAdapterToListView(listView);
        showWebserviceTask();

    }

    public class WebserviceTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String username = strings[0];
            int password = 17333;
            String url = "http://eaustria.no-ip.biz/flohmarkt/flohmarkt.php?operation=get";
            String sJson = "";
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    sJson = readResponseStream(reader);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sJson;
        }

        private String readResponseStream(BufferedReader reader) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            outputRepoNames(s);
            super.onPostExecute(s);
        }

        private void outputRepoNames(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray array = jsonObject.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    jsonObject = array.getJSONObject(i);
                    int id = Integer.parseInt(jsonObject.getString("id"));
                    String name = jsonObject.getString("name");
                    double price = Double.parseDouble(jsonObject.getString("price"));
                    String username = jsonObject.getString("username");
                    String email = jsonObject.getString("email");
                    String phone = jsonObject.getString("phone");
                    Article article = new Article(id, name, price, username, email, phone);
                    articles.add(article);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    }


        private void bindAdapterToListView(ListView lv) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, articles);
            lv.setAdapter(adapter);
        }


    public void showWebserviceTask(){
        WebserviceTask task = new WebserviceTask();        
        task.execute(username);
    }

    public void dialog_add(){
        final View vDialog = getLayoutInflater().inflate(R.layout.dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Add new Article");
        final EditText txtdescription = vDialog.findViewById(R.id.description);
        final EditText txtprice = vDialog.findViewById(R.id.price);
        final EditText txtusername = vDialog.findViewById(R.id.username);
        final EditText txtemail = vDialog.findViewById(R.id.email);
        final EditText txttelephone = vDialog.findViewById(R.id.telephone);
        alert.setView(vDialog);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //add
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();
    }


}
