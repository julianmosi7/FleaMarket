package com.calculate.fleamarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AgentActivity extends AppCompatActivity {
    String search_1_txt;
    double price_txt;
    String search_2_txt;
    double longitude_txt;
    double latitude_txt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        //Intent intent = getIntent();
        //Bundle bundle = intent.getExtras();
        //article = (Article) bundle.getSerializable("article");
        show();

        super.onCreate(savedInstanceState);
    }

    public void show(){
        final TextView search_1 = findViewById(R.id.search_1);
        final TextView price = findViewById(R.id.price);
        final TextView search_2 = findViewById(R.id.search_2);
        final TextView longitude = findViewById(R.id.longitude);
        final TextView latitude = findViewById(R.id.latitude);

        search_1_txt = search_1.getText().toString();
        price_txt = Double.parseDouble(price.getText().toString());
        search_2_txt = search_2.getText().toString();
        longitude_txt = Double.parseDouble(longitude.getText().toString());
        latitude_txt = Double.parseDouble(latitude.getText().toString());

    }
}
