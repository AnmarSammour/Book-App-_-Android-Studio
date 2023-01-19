package com.example.planetofbooks.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.planetofbooks.R;

public class ContactUsFragment extends Fragment {

    public static final int REQUEST_CALL = 101;

    TextView num,instagram,email;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_us_fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        num = (TextView) view.findViewById(R.id.phoneText);
        instagram = (TextView) view.findViewById(R.id.instagram);
        email = (TextView) view.findViewById(R.id.emailText);



        num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall();

            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeInstagramCall("https://www.instagram.com/planetofbooksapp/");

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeEmailCall();
            }
        });
    }

    private void makeEmailCall() {

        email.setText(Html.fromHtml("<a href=\"mailto:planetofbooksapp@gmail.com\">planetofbooksapp@gmail.com</a>"));
        email.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void makeInstagramCall(String s) {



        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    private void makePhoneCall()
    {

        String number = num.getText().toString();
        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);

        }else{
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }

    }
}