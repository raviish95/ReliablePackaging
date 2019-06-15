package com.awizom.reliablepackaging.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.awizom.reliablepackaging.HomePage;
import com.awizom.reliablepackaging.R;
import com.awizom.reliablepackaging.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 200;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        initView();
    }

    private void initView() {
           new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (!(SharedPrefManager.getInstance(SplashActivity.this).getUser().getClientID() == 0)) {
                        Intent intent = new Intent(SplashActivity.this, HomePage.class);
                        startActivity(intent);
                    }else {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                }
            }, SPLASH_TIME_OUT);

        }

    }

