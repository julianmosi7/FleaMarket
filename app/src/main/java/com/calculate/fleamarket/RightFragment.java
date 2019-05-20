package com.calculate.fleamarket;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RightFragment extends Fragment {
    public final static String TAG = RightFragment.class.getSimpleName();
    Article article;
    String mail;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: entered");
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        intializeViews(view);
        show(article);
        return view;
    }

    private void intializeViews(View view) {
        Log.d(TAG, "intializeViews: entered");
        this.view = view;
        Button b = view.findViewById(R.id.call);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhoneCall();
            }
        });

        Button c = view.findViewById(R.id.email_to);
        c.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openEmail();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: entered");
    }

    public void show(Article article){
        final TextView description = view.findViewById(R.id.bezeichnung);
        final TextView price = view.findViewById(R.id.preis);
        final TextView username = view.findViewById(R.id.benutzername);
        final TextView email = view.findViewById(R.id.email);
        final TextView telephone = view.findViewById(R.id.telefon);

        description.setText(article.getDescription());
        price.setText(String.valueOf(article.getPrice()));
        username.setText(article.getUsername());
        email.setText(article.getEmail());
        telephone.setText(article.getTelephone());
        mail = email.getText().toString();

    }

    public void openPhoneCall(){
        String phone = "tel:(+43) 069911343415";
        Uri uri = Uri.parse(phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(intent);

    }

    public void openEmail() {
        String email = mail;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, "Message:");

        try{
            startActivity(intent.createChooser(intent, "Send mail..."));
        }catch(ActivityNotFoundException ex){

        }

    }

}
