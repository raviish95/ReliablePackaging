package com.awizom.reliablepackaging;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.awizom.reliablepackaging.Adapter.OrderDetailsAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBarWrapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class OrderDetails extends AppCompatActivity {

    com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar verticalSeekBar;

    private LinearLayout linearLayout;
    private RecyclerView recyclerViewOrder;
    String Orderid;
    OrderDetailsAdapter adapterOrderDetails;
    List<OrderDetailsView> orderdetails;
    String imageLink;
    ScrollView scrollView;
    private AlertDialog progressDialog;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Orderid = getIntent().getStringExtra("OrderId");
        imageLink = getIntent().getStringExtra("ImageLink");
        initview();
        verticalSeekBar = findViewById(R.id.capacity_seek);

        if (Build.VERSION.SDK_INT >= 11) {
            ObjectAnimator animation = ObjectAnimator.ofInt(verticalSeekBar, "progress", 3);
            animation.setDuration(1050); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();

            verticalSeekBar.setEnabled(false);

        } else {

            verticalSeekBar.setProgress(3);

        }
    }

    private void initview() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Order Details");
        toolbar.setBackgroundColor(getResources().getColor(R.color.yellow));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        relativeLayout = findViewById(R.id.footer);
        scrollView = findViewById(R.id.scrollView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                     if(scrollY<=oldScrollY)
                    {
                        //scroll up
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                    else {
                    relativeLayout.setVisibility(View.GONE);}
                }
            });
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageLink.toString().equals("imagelink")) {
                    Intent intent = new Intent(OrderDetails.this, HomePage.class);
                    startActivity(intent);
                } else {
                    onBackPressed();
                }

            }
        });

        progressDialog = new SpotsDialog(OrderDetails.this, R.style.Custom);
        linearLayout = findViewById(R.id.linearlayout);
        linearLayout.setOnTouchListener(new OnSwipeTouchListener(OrderDetails.this) {
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
        recyclerViewOrder = findViewById(R.id.recyclerOrderdetails);
        recyclerViewOrder.setHasFixedSize(true);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));
        getOrderDetails();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (imageLink.toString().equals("imagelink")) {
                Intent intent = new Intent(OrderDetails.this, HomePage.class);
                startActivity(intent);
            } else {
                onBackPressed();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getOrderDetails() {

        try {
            String result = new OrderHelper.GETMyOrderDetails().execute(Orderid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<OrderDetailsView>>() {
            }.getType();
            orderdetails = new Gson().fromJson(result, listType);
            adapterOrderDetails = new OrderDetailsAdapter(OrderDetails.this, orderdetails, imageLink);
            recyclerViewOrder.setAdapter(adapterOrderDetails);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}