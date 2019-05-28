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
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MyProfile extends AppCompatActivity {

    private LinearLayout linearLayout;
    List<MyProfileView> profileViews;
    private TextView name, email, mobno, place;
    private Button orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initview();
    }

    private void initview() {

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Profile");
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
        linearLayout = findViewById(R.id.linearlayout);
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
        orders=findViewById(R.id.orders);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        getmyProfile();

    }

    private void getmyProfile() {

        String clientid = "3";
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
