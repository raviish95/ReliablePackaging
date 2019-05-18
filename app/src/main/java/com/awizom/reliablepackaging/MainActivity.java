package com.awizom.reliablepackaging;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private ImageView reliableimageview;
    private TextView reliableTextView;
    private Button loginbutton;
    private TextInputEditText userid, password;
    private ProgressBar loadingProgressBar;
    private RelativeLayout rootView, afterAnimationView;
    private LinearLayout linearLayout;
    Snackbar snackbar;
    boolean connected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initViews();
        new CountDownTimer(5000, 1000) {

            @SuppressLint("ResourceType")
            @Override
            public void onTick(long millisUntilFinished) {
                reliableTextView.setVisibility(GONE);
                loadingProgressBar.setVisibility(GONE);

                reliableimageview.setImageResource(R.drawable.reliables);

                startAnimation();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void initViews() {
        reliableimageview = findViewById(R.id.reliableimage);
        reliableTextView = findViewById(R.id.reliablepackaging);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        linearLayout=findViewById(R.id.linearlayout);
        snackbar = Snackbar.make(linearLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initViews();
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        checkInternet();
        userid = findViewById(R.id.userid);
        password = findViewById(R.id.passwordEditText);
        loginbutton = findViewById(R.id.loginButton);
        rootView = findViewById(R.id.rootView);
        afterAnimationView = findViewById(R.id.afterAnimationView);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userid.getText().toString().isEmpty() || userid.getText().toString().contains(" ")) {
                    userid.setError("Please Enter valid UserId");
                    userid.requestFocus();
                } else if (password.getText().toString().isEmpty() || password.getText().toString().contains(" ")) {
                    password.setError("Please Enter valid Password");
                    password.requestFocus();
                } else {
                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void checkInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            //    Toast.makeText(getApplicationContext(), "Internet is On", Toast.LENGTH_SHORT).show();
        } else {
            connected = false;
            snackbar.show();
        }
    }


    private void startAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = reliableimageview.animate();
        viewPropertyAnimator.x(260f);
        viewPropertyAnimator.y(150f);
        viewPropertyAnimator.setDuration(1000);
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                afterAnimationView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}