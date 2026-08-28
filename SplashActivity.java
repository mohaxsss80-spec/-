package com.nataejna.app;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView watermark = findViewById(R.id.watermark);
        TextView developerLabel = findViewById(R.id.developerLabel);

        // Slow floating movement for background name
        ObjectAnimator floatX = ObjectAnimator.ofFloat(watermark, "translationX", -40f, 40f);
        floatX.setDuration(6000);
        floatX.setRepeatMode(ValueAnimator.REVERSE);
        floatX.setRepeatCount(ValueAnimator.INFINITE);
        floatX.setInterpolator(new AccelerateDecelerateInterpolator());
        floatX.start();

        ObjectAnimator floatY = ObjectAnimator.ofFloat(watermark, "translationY", -25f, 25f);
        floatY.setDuration(4500);
        floatY.setRepeatMode(ValueAnimator.REVERSE);
        floatY.setRepeatCount(ValueAnimator.INFINITE);
        floatY.setInterpolator(new AccelerateDecelerateInterpolator());
        floatY.start();

        // Subtle pulse on developer name
        ObjectAnimator pulse = ObjectAnimator.ofFloat(developerLabel, "alpha", 0.5f, 1f);
        pulse.setDuration(1200);
        pulse.setRepeatMode(ValueAnimator.REVERSE);
        pulse.setRepeatCount(ValueAnimator.INFINITE);
        pulse.setInterpolator(new LinearInterpolator());
        pulse.start();

        // Go to main after delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 2200);
    }
}
