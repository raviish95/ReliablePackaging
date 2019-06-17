package com.awizom.reliablepackaging;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.awizom.reliablepackaging.Adapter.OrderDetailsAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
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
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Orderid = getIntent().getStringExtra("OrderId");
        imageLink = getIntent().getStringExtra("ImageLink");
        initview();
        verticalSeekBar = findViewById(R.id.capacity_seek);
        if (Build.VERSION.SDK_INT >= 11) {
            // will update the "progress" propriety of seekbar until it reaches progress
            ObjectAnimator animation = ObjectAnimator.ofInt(verticalSeekBar, "progress", 3);
            animation.setDuration(1050); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
            verticalSeekBar.setEnabled(false);

        } else {
            verticalSeekBar.setProgress(2);

        }
        verticalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (seekBar.isSelected()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        seekBar.setTickMark(getResources().getDrawable(R.drawable.close_blue));
                    }
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        seekBar.setTickMark(getResources().getDrawable(R.drawable.close_blue));
                    }
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (seekBar.isSelected()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        seekBar.setTickMark(getResources().getDrawable(R.drawable.close_blue));
                    }
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        seekBar.setTickMark(getResources().getDrawable(R.drawable.close_blue));
                    }
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    seekBar.setTickMark(getResources().getDrawable(R.drawable.close_blue));
                }
            }
        });


    }

    private void initview() {

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Order Details");
        toolbar.setBackgroundColor(getResources().getColor(R.color.yellow));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
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