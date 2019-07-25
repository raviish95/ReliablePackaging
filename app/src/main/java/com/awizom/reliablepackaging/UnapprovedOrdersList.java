package com.awizom.reliablepackaging;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awizom.reliablepackaging.Adapter.MyOrderListAdapter;
import com.awizom.reliablepackaging.Adapter.UnapprovedOrderListAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class UnapprovedOrdersList extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView no_internet;
    boolean connected = false;
    List<Order> orderlist;
    UnapprovedOrderListAdapter adapterOrderList;
    LinearLayout linearLayout;
    Snackbar snackbar;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unapproved_orders_list);
        initview();
    }

    private void initview() {
        String headername = getIntent().getStringExtra("HeaderName");
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(headername.toString());
        toolbar.setBackgroundColor(Color.parseColor("#45B39D"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        LinearLayout linearLayout = findViewById(R.id.OrderDataFunc);
        if (headername.equals("My Job")) {
            linearLayout.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
        }
        no_internet = findViewById(R.id.no_internet);
        linearLayout = findViewById(R.id.linearlayout);

        snackbar = Snackbar.make(linearLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initview();
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.scrollToPosition(0);
        recyclerView.smoothScrollToPosition(0);
        linearLayout = findViewById(R.id.linearlayout);
        checkinternet();
    }

    private void checkinternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            no_internet.setVisibility(View.GONE);

            connected = true;



                getMyunapprovedOrderList();

            //    Toast.makeText(getApplicationContext(), "Internet is On", Toast.LENGTH_SHORT).show();
        } else {
            connected = false;
            no_internet.setVisibility(View.VISIBLE);
            snackbar.show();

        }
    }

    private void getMyunapprovedOrderList() {
        String clientid = String.valueOf(SharedPrefManager.getInstance(this).getUser().getClientID());
        try {
            //  Toast.makeText(getApplicationContext(), "deviceid->" + FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_LONG).show();
            String result = new OrderHelper.GETMyUnapprovedOrder().execute(clientid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            orderlist = new Gson().fromJson(result, listType);
            adapterOrderList = new UnapprovedOrderListAdapter(UnapprovedOrdersList.this, orderlist);
            recyclerView.setAdapter(adapterOrderList);
            swipeRefreshLayout.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}