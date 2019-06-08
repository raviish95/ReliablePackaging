package com.awizom.reliablepackaging;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.awizom.reliablepackaging.Adapter.NotificationListAdapter;
import com.awizom.reliablepackaging.Adapter.OrderListAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Helper.ProfileHelper;
import com.awizom.reliablepackaging.Model.NotificationModel;
import com.awizom.reliablepackaging.Model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<NotificationModel> notificationModelList;
    NotificationListAdapter notificationListAdapter;
    private AlertDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
    }

    private void initView() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Notifications");
        toolbar.setBackgroundColor(Color.parseColor("#87CEFA"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new SpotsDialog(this, R.style.Custom);
        getNotifications();
    }

    private void getNotifications() {
        String clientid = String.valueOf(SharedPrefManager.getInstance(this).getUser().getClientID());
        try {

            String result = new ProfileHelper.GETNotifications().execute(clientid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<NotificationModel>>() {
            }.getType();
            notificationModelList = new Gson().fromJson(result, listType);
            notificationListAdapter = new NotificationListAdapter(NotificationActivity.this, notificationModelList);

            recyclerView.setAdapter(notificationListAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
