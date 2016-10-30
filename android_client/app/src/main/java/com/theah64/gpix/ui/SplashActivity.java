package com.theah64.gpix.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.theah64.gpix.R;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }, SPLASH_DELAY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        super.finish();
    }
}
