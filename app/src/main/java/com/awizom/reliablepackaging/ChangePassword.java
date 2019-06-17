package com.awizom.reliablepackaging;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.awizom.reliablepackaging.Helper.ProfileHelper;
import com.awizom.reliablepackaging.Model.ResultModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import dmax.dialog.SpotsDialog;

public class ChangePassword extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private String userid, username, result = "";
    private TextInputEditText oldPass, newPass, cnfrmPass;
    private Button subMitButton;
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initview();
    }

    private void initview() {

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Change Password");
        toolbar.setBackgroundColor(getResources().getColor(R.color.yellow));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressDialog = new SpotsDialog(this, R.style.Custom);
        subMitButton = findViewById(R.id.submitButton);
        oldPass = findViewById(R.id.old_pwd);
        newPass = findViewById(R.id.new_pwd);
        cnfrmPass = findViewById(R.id.confirm_pwd);
        userid = getIntent().getStringExtra("UserID");
        username = SharedPrefManager.getInstance(this).getUser().UserName.toString();
        relativeLayout = findViewById(R.id.rootView);
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(ChangePassword.this) {
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
        subMitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                changepasswordmethod();
            }
        });
    }

    private void changepasswordmethod() {

        if (oldPass.getText().toString().isEmpty()) {
            progressDialog.dismiss();
            oldPass.setError("Please enter valid password");
            oldPass.requestFocus();
        } else if (newPass.getText().toString().isEmpty()) {
            progressDialog.dismiss();
            newPass.setError("Please enter new password");
            newPass.requestFocus();
        } else if (newPass.getText().toString().length() < 6) {
            progressDialog.dismiss();
            newPass.setError("Password length should be minimum 6");
            newPass.requestFocus();
        } else if (cnfrmPass.getText().toString().isEmpty()) {
            cnfrmPass.setError("Please enter confirm password");
            cnfrmPass.requestFocus();
        } else if (cnfrmPass.getText().toString().length() < 6) {
            progressDialog.dismiss();
            cnfrmPass.setError("Password length should be minimum 6");
            cnfrmPass.requestFocus();
        } else if (!(cnfrmPass.getText().toString().equals(newPass.getText().toString()))) {
            progressDialog.dismiss();
            cnfrmPass.setError("Confirm password should be same as new password");
            cnfrmPass.requestFocus();
        } else {

            try {
                result = new ProfileHelper.Changepasswordmethod().execute(userid.toString(), username.toString(), oldPass.getText().toString(), newPass.getText().toString()).get();
                if (result.isEmpty()) {
                    Toast.makeText(ChangePassword.this, "Invalid request", Toast.LENGTH_SHORT).show();
                    result = new ProfileHelper.Changepasswordmethod().execute(userid.toString(), username.toString(), oldPass.getText().toString(), newPass.getText().toString()).get();
                } else {
                    Gson gson = new Gson();
                    Type getType = new TypeToken<ResultModel>() {
                    }.getType();
                    ResultModel resultModel = new Gson().fromJson(result, getType);
                    if (resultModel.getStatus().equals("Password Changed")) {
                        Toast.makeText(ChangePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, HomePage.class);
                        startActivity(intent);
                        dismissmethod();
                    } else {
                        progressDialog.dismiss();
                        oldPass.setError("Please enter valid password");
                        oldPass.requestFocus();
                        // Toast.makeText(ChangePassword.this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
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
