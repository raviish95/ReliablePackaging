package com.awizom.reliablepackaging;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.awizom.reliablepackaging.Adapter.OrderListAdapter;
import com.awizom.reliablepackaging.Adapter.RebookOrderListAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class RebookOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Order> orderlist;
    private RebookOrderListAdapter adapterOrderList;
    private LinearLayout linearLayout;
    private Button rebook;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebook_order);
        initview();
    }

    private void initview() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Rebook Order");
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
        rebook = findViewById(R.id.addReOrder);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    initview();
                } catch (Exception e) {
                    e.printStackTrace();
                    // relativeLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        linearLayout = findViewById(R.id.linear);
        linearLayout.setOnTouchListener(new OnSwipeTouchListener(RebookOrderActivity.this) {
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

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.scrollToPosition(0);
        recyclerView.smoothScrollToPosition(0);

        getcategorylist();

    }

    private void getcategorylist() {

        String clientid = String.valueOf(SharedPrefManager.getInstance(this).getUser().getClientID());
        try {

            String result = new OrderHelper.GETMyOrder().execute(clientid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            orderlist = new Gson().fromJson(result, listType);
            adapterOrderList = new RebookOrderListAdapter(RebookOrderActivity.this, orderlist, rebook);
            recyclerView.setAdapter(adapterOrderList);
            swipeRefreshLayout.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
