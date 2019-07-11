package com.awizom.reliablepackaging;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awizom.reliablepackaging.Adapter.OrderDetailsAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Helper.ProfileHelper;
import com.awizom.reliablepackaging.Model.MyProfileView;
import com.awizom.reliablepackaging.Model.Order;
import com.awizom.reliablepackaging.Model.OrderCount;
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MyProfile extends AppCompatActivity {

    private LinearLayout linearLayout;
    List<MyProfileView> profileViews;
    private TextView name, email, mobno, place, nameimage, totalordervalue, totalrunningorder, totalcompltdorder;
    private Button orders;
    private LinearLayout totalodr, runningodr, cmpltdodr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        initview();
    }

    private void initview() {

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Profile");
        toolbar.setBackgroundColor(Color.parseColor("#28B463"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        linearLayout = findViewById(R.id.linearlayout);
        totalodr = findViewById(R.id.totalOrder);
        runningodr = findViewById(R.id.runningOrder);
        cmpltdodr = findViewById(R.id.completedOrder);
        totalordervalue = findViewById(R.id.totalordervalue);
        totalrunningorder = findViewById(R.id.runningordervalue);
        totalcompltdorder = findViewById(R.id.completedordervalue);
        totalodr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyOrderList.class);
                intent.putExtra("Ordertype", "TotalOdr");
                intent.putExtra("HeaderName", "Total Order");
                startActivity(intent);
            }
        });
        cmpltdodr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyOrderList.class);
                intent.putExtra("Ordertype", "CompletedOdr");
                intent.putExtra("HeaderName", "Completed Order");
                startActivity(intent);
            }
        });
        runningodr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyOrderList.class);
                intent.putExtra("Ordertype", "RunningOdr");
                intent.putExtra("HeaderName", "Running Order");
                startActivity(intent);
            }
        });
        linearLayout.setOnTouchListener(new OnSwipeTouchListener(MyProfile.this) {
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
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobno = findViewById(R.id.mobno);
        place = findViewById(R.id.place);
        orders = findViewById(R.id.orders);
        nameimage = findViewById(R.id.img);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getmyProfile();
        getMyOrderCount();

    }

    private void getMyOrderCount() {

        String clientid = String.valueOf(SharedPrefManager.getInstance(this).getUser().getClientID());
        try {

            String result = new ProfileHelper.GetOrderCount().execute(clientid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<OrderCount>() {
            }.getType();
            OrderCount orderCount = new Gson().fromJson(result, listType);

            String totalorder = String.valueOf(orderCount.getTotalstauscount());
            String runningorder = String.valueOf(orderCount.getTotalrunningstatus());
            String completedorder = String.valueOf(orderCount.getTotalcompletedcount());

            totalordervalue.setText(totalorder.toString());
            totalrunningorder.setText(runningorder.toString());
            totalcompltdorder.setText(completedorder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
    private void getmyProfile() {

        String clientid = String.valueOf(SharedPrefManager.getInstance(this).getUser().getClientID());
        try {

            String result = new ProfileHelper.GETMyProfile().execute(clientid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<MyProfileView>() {
            }.getType();
            MyProfileView myProfileView = new Gson().fromJson(result, listType);
            String nameview = myProfileView.getName().toString();
            String Email = myProfileView.getEmail().toString();
            String PhoneNumber = String.valueOf(myProfileView.getPhoneNumber().toString());
            String pincode = String.valueOf(myProfileView.getPinCode());
            String billingaddredss = String.valueOf(myProfileView.getBillingAdddress().toString());
            name.setText(nameview.toString());
            email.setText(Email.toString());
            mobno.setText(PhoneNumber.toString());
            place.setText(billingaddredss.toString() + " " + pincode);
            nameimage.setText(nameview.toString().split("")[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
