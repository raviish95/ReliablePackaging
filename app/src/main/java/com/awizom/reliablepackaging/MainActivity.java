package com.awizom.reliablepackaging;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private ImageView reliableimageview;
    private TextView reliableTextView;
    private Button loginbutton;
    private TextInputEditText userid, password;
    private ProgressBar loadingProgressBar;
    private RelativeLayout rootView, afterAnimationView;

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

                reliableimageview.setImageResource(R.drawable.reliable);

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
                }


            }
        });
    }

    private void startAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = reliableimageview.animate();
        viewPropertyAnimator.x(300f);
        viewPropertyAnimator.y(100f);
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