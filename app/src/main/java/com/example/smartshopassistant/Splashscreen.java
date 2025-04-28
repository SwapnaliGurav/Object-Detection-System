package com.example.smartshopassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.smartshopassistant.Activity.Homescreen;

public class Splashscreen extends AppCompatActivity {

    ImageView appicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        appicon = findViewById(R.id.appicon);
        final androidx.cardview.widget.CardView iconContainer = findViewById(R.id.iconContainer);


        ObjectAnimator bounceAnimation = ObjectAnimator.ofFloat(iconContainer, "translationY", 0f, -70f, 0f);
        bounceAnimation.setDuration(1000);
        bounceAnimation.setRepeatCount(ObjectAnimator.INFINITE);
        bounceAnimation.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 5000);
    }
}