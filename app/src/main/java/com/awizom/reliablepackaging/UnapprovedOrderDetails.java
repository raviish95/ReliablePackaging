package com.awizom.reliablepackaging;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.Adapter.OrderDetailsAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.awizom.reliablepackaging.Model.TrackingStatus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBarWrapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/*created by ravi on 25/07/2019*/
public class UnapprovedOrderDetails extends AppCompatActivity {

    com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar verticalSeekBar;
    private LinearLayout linearLayout;
    private RecyclerView recyclerViewOrder;
    String Orderid;
    OrderDetailsAdapter adapterOrderDetails;
    List<OrderDetailsView> orderdetails;

    String imageLink;
    ScrollView scrollView;
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unapproved_order_details);
        Orderid = getIntent().getStringExtra("OrderId");
        imageLink = getIntent().getStringExtra("ImageLink");
        verticalSeekBar = findViewById(R.id.capacity_seek);
        initview();

    }

    private void initview() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Unapproved Order Details");
        toolbar.setBackgroundColor(Color.parseColor("#212F3D"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageLink.toString().equals("imagelink")) {
                    Intent intent = new Intent(UnapprovedOrderDetails.this, HomePage.class);
                    startActivity(intent);
                } else {
                    onBackPressed();
                }

            }
        });

        progressDialog = new SpotsDialog(UnapprovedOrderDetails.this, R.style.Custom);
        linearLayout = findViewById(R.id.linearlayout);
        linearLayout.setOnTouchListener(new OnSwipeTouchListener(UnapprovedOrderDetails.this) {
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
                Intent intent = new Intent(UnapprovedOrderDetails.this, HomePage.class);
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
            adapterOrderDetails = new OrderDetailsAdapter(UnapprovedOrderDetails.this, orderdetails, imageLink);
            recyclerViewOrder.setAdapter(adapterOrderDetails);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
