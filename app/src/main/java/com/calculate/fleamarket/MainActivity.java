package com.calculate.fleamarket;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog_show(position);
            }
        });


        bindAdapterToListView(listView);
        showWebserviceTask();

    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menue, menu);
        return super.onCreateOptionsMenu(menu);
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id){
            case R.id.menu_add:
                dialog_add();
            case R.id.menu_preferences:
                Intent intent = new Intent(this, MySettingsActivity.class);
                startActivityForResult(intent, 0);
        }
        return super.onOptionsItemSelected(item);
     }

    public class WebserviceTask extends AsyncTask<String, Integer, String> {
        String art = "";
        String op;

        public WebserviceTask(String[] article){
            String operation = article[0];
            art = operation;
            if(operation.equals("add")){
                art = art + "&name=" + article[1] + "&price=" + article[2] + "&username=" + article[3] + "&password=" + article[4] + "&email=" + article[5] + "&phone=" + article[6];
            }
            System.out.println("------------");
            System.out.println(art);
            op = operation;
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = "http://eaustria.no-ip.biz/flohmarkt/flohmarkt.php?operation="+art;
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
            System.out.println(op);
           if(op.equals("add")){
               try {
                   JSONObject jsonObject = new JSONObject(s);
                   jsonObject.getString("code");
                   jsonObject.getString("data");
                   showWebserviceTask();
               } catch (JSONException ex) {
                   ex.printStackTrace();
               }
               adapter.notifyDataSetChanged();
           }else{
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
    }


        private void bindAdapterToListView(ListView lv) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, articles);
            lv.setAdapter(adapter);
        }


    public void showWebserviceTask(){
        String[] art = new String[1];
        art[0] = "get";
        WebserviceTask task = new WebserviceTask(art);
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
        final String[] art = new String[10];


        alert.setView(vDialog);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                art[0] = "add";
                art[1] = txtdescription.getText().toString();
                art[2] = txtprice.getText().toString();
                art[3] = txtusername.getText().toString();
                art[4] = prefs.getString("password", "17333");
                art[5] = txtemail.getText().toString();
                art[6] = txttelephone.getText().toString();
                WebserviceTask task = new WebserviceTask(art);
                task.execute();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        alert.show();
    }

    public void dialog_show(int position){
        final View vDialog = getLayoutInflater().inflate(R.layout.view, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        Article article = articles.get(position);

        final TextView description = vDialog.findViewById(R.id.bezeichnung);
        final TextView price = vDialog.findViewById(R.id.preis);
        final TextView username = vDialog.findViewById(R.id.benutzername);
        final TextView email = vDialog.findViewById(R.id.email);
        final TextView telephone = vDialog.findViewById(R.id.telefon);
        alert.setView(vDialog);


        description.setText(article.getDescription());
        price.setText(String.valueOf(article.getPrice()));
        username.setText(article.getUsername());
        email.setText(article.getEmail());
        telephone.setText(article.getTelephone());


       alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

           }
       });



        alert.show();
    }


}
