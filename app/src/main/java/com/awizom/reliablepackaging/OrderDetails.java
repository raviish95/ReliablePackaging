package com.awizom.reliablepackaging;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

public class OrderDetails extends AppCompatActivity {

    com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar verticalSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        initview();
        verticalSeekBar = findViewById(R.id.capacity_seek);


        if(android.os.Build.VERSION.SDK_INT >= 11){
            // will update the "progress" propriety of seekbar until it reaches progress
            ObjectAnimator animation = ObjectAnimator.ofInt(verticalSeekBar, "progress", 3);
            animation.setDuration(1050); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
            verticalSeekBar.setEnabled(false);
        }
        else
            verticalSeekBar.setProgress(2);
    }

    private void initview() {
        android.support.v7.widget.Toolbar toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Order Details");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}