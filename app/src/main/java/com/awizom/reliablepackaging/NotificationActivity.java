package com.awizom.reliablepackaging;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
    }

    private void initView() {
        android.support.v7.widget.Toolbar toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
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
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
