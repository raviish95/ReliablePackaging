package com.awizom.reliablepackaging;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.awizom.reliablepackaging.Helper.OrderHelper;

public class FeedbackActivity extends AppCompatActivity {

    private EditText feedEditetet;
    private Button feedSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initview();

    }

    private void initview() {

        feedEditetet = findViewById(R.id.feedback);
        feedSubmit = findViewById(R.id.submit);
        feedSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientid = String.valueOf(SharedPrefManager.getInstance(FeedbackActivity.this).getUser().getClientID());
                if (feedEditetet.getText().toString().isEmpty()) {
                    feedEditetet.setError("Feedback Should be not empty");
                    feedEditetet.requestFocus();
                } else {


                    try {
                        String result = new OrderHelper.POSTCompanyFeedback().execute(clientid, feedEditetet.getText().toString()).get();
                        if (result.isEmpty()) {
                            Toast.makeText(FeedbackActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
                            result = new OrderHelper.POSTCompanyFeedback().execute(clientid, feedEditetet.getText().toString()).get();
                        } else {
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
}
