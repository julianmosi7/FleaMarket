package com.calculate.fleamarket;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    Article article;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        article = (Article) bundle.getSerializable("article");
        show();

        Button b = findViewById(R.id.call);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhoneCall();
            }
        });

    }

    public void show(){
        final TextView description = findViewById(R.id.bezeichnung);
        final TextView price = findViewById(R.id.preis);
        final TextView username = findViewById(R.id.benutzername);
        final TextView email = findViewById(R.id.email);
        final TextView telephone = findViewById(R.id.telefon);

        description.setText(article.getDescription());
        price.setText(String.valueOf(article.getPrice()));
        username.setText(article.getUsername());
        email.setText(article.getEmail());
        telephone.setText(article.getTelephone());

    }

    public void openPhoneCall(){
        String phone = "tel:(+43) 069911343415";
        Uri uri = Uri.parse(phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(intent);
    }
}
