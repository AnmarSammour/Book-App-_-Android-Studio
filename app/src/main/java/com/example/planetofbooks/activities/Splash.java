package com.example.planetofbooks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.planetofbooks.R;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView i = (ImageView) findViewById(R.id.lottie);

        Glide.with(this).load(R.raw.splashh).into(i);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Delay and Start Activity
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3500);//startActivity after 3.5seconds


    }

}
