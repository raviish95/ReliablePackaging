package com.awizom.reliablepackaging.login;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.Toast;

import com.awizom.reliablepackaging.Helper.ProfileHelper;
import com.awizom.reliablepackaging.HomePage;
import com.awizom.reliablepackaging.Model.LoginModel;
import com.awizom.reliablepackaging.R;
import com.awizom.reliablepackaging.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import dmax.dialog.SpotsDialog;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private ImageView reliableimageview;
    private TextView reliableTextView;
    private Button loginbutton;
    private TextInputEditText userName, password;
    private ProgressBar loadingProgressBar;
    private RelativeLayout rootView, afterAnimationView;
    private LinearLayout linearLayout;
    Snackbar snackbar;
    String result = "";
    boolean connected = false;
    private AlertDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        if (!(SharedPrefManager.getInstance(this).getUser().getClientID() == 0)) {
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }
        initViews();
        new CountDownTimer(50000, 2000) {

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

        progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
        reliableimageview = findViewById(R.id.reliableimage);
        reliableTextView = findViewById(R.id.reliablepackaging);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        linearLayout = findViewById(R.id.linearlayout);
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
        userName = findViewById(R.id.username);
        password = findViewById(R.id.passwordEditText);
        loginbutton = findViewById(R.id.loginButton);
        rootView = findViewById(R.id.rootView);
        afterAnimationView = findViewById(R.id.afterAnimationView);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if (userName.getText().toString().isEmpty() || userName.getText().toString().contains(" ")) {
                    progressDialog.dismiss();
                    userName.setError("Please Enter valid UserId");
                    userName.requestFocus();
                } else if (password.getText().toString().isEmpty() || password.getText().toString().contains(" ")) {
                    progressDialog.dismiss();
                    password.setError("Please Enter valid Password");
                    password.requestFocus();
                } else {

                    try {
                        result = new ProfileHelper.LogIn().execute(userName.getText().toString(), password.getText().toString()).get();
                        if (result.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
                            result = new ProfileHelper.LogIn().execute(userName.getText().toString(), password.getText().toString()).get();
                        } else {

                            Gson gson = new Gson();
                            Type listType = new TypeToken<LoginModel>() {
                            }.getType();
                            LoginModel loginModel = new Gson().fromJson(result, listType);
                            String clientId = String.valueOf(loginModel.getClientID());

                            if (!clientId.equals("0")) {
                                String userID=loginModel.getUserID().toString();
                                LoginModel loginModel1 = new LoginModel();
                                loginModel1.ClientID = Integer.parseInt((clientId));
                                loginModel1.UserID=String.valueOf(userID.toString());
                                loginModel1.UserName=userName.getText().toString();
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(loginModel1);
                                Intent intent = new Intent(MainActivity.this, HomePage.class);
                                intent.putExtra("ClientId", clientId.toString());
                                intent.putExtra("UserName", userName.getText().toString());
                                startActivity(intent);
                             //   progressDialog.dismiss();
                            } else {
                                progressDialog.dismiss();
                                userName.setError("UserId or Password is Wrong");
                                password.setError("UserId or Password is Wrong");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        viewPropertyAnimator.x(270f);
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