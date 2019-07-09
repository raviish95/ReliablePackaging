package com.awizom.reliablepackaging;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.awizom.reliablepackaging.Helper.OrderHelper;

import dmax.dialog.SpotsDialog;

public class FeedbackActivity extends AppCompatActivity {

    private EditText feedEditetet;
    private Button feedSubmit;
    private AlertDialog progressDialog;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initview();
    }

    private void initview() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Feedback");
        toolbar.setBackgroundColor(Color.parseColor("#517BFE"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        relativeLayout=findViewById(R.id.relative);
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(FeedbackActivity.this) {
            public void onSwipeTop() {
                //   Toast.makeText(RebookOrderActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                onBackPressed();
                // Toast.makeText(RebookOrderActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {

                //  Toast.makeText(RebookOrderActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                //     Toast.makeText(RebookOrderActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
        progressDialog = new SpotsDialog(this, R.style.Custom);
        feedEditetet = findViewById(R.id.feedback);
        feedSubmit = findViewById(R.id.submit);
        feedSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientid = String.valueOf(SharedPrefManager.getInstance(FeedbackActivity.this).getUser().getClientID());
                if (feedEditetet.getText().toString().isEmpty()) {
                    feedEditetet.setError("Feedback should be not empty");
                    feedEditetet.requestFocus();
                } else {

                    progressDialog.show();
                    try {
                        String result = new OrderHelper.POSTCompanyFeedback().execute(clientid, feedEditetet.getText().toString()).get();
                        if (result.isEmpty()) {
                            Toast.makeText(FeedbackActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
                            result = new OrderHelper.POSTCompanyFeedback().execute(clientid, feedEditetet.getText().toString()).get();
                            dismissmethod();
                        } else {
                            dismissmethod();
                            Toast.makeText(FeedbackActivity.this, "Feedback Submitted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(FeedbackActivity.this, HomePage.class);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void dismissmethod() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 100);
    }
}
