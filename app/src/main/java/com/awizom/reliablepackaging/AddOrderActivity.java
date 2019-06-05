package com.awizom.reliablepackaging;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.awizom.reliablepackaging.Helper.OrderHelper;

public class AddOrderActivity extends AppCompatActivity {

    private TextInputEditText productname, itemweight;
    private RadioGroup radioGroup;
    private RadioButton weight, piece;
    private TextView productype;
    private Button submitOrder;
    private RelativeLayout relativeLayout;
    private Spinner layertype;
    private String layervalue = "1", weightvalue;
    String[] layerlist = {"Two Layer", "Three Layer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        initview();
    }

    private void initview() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Order");
        toolbar.setBackgroundColor(Color.parseColor("#87CEFA"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddOrderActivity.this, HomePage.class);
                startActivity(intent);
            }
        });
        itemweight = findViewById(R.id.itemWeight);
        layertype = findViewById(R.id.layerType);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, layerlist);
        layertype.setAdapter(arrayAdapter);
        layertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String layertypedetails = parent.getItemAtPosition(position).toString();
                if (layertypedetails.equals("Two Layer")) {
                    //Toast.makeText(getApplicationContext(), "" + createComplaint, Toast.LENGTH_SHORT).show();
                    layervalue = "1";

                } else {
                    layervalue = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        relativeLayout = findViewById(R.id.rootView);
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(AddOrderActivity.this) {
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
        radioGroup = findViewById(R.id.Product_Scale);
        productname = findViewById(R.id.prod_name);
        submitOrder = findViewById(R.id.SubmitOrder);
        weight = findViewById(R.id.radioWeight);
        piece = findViewById(R.id.radioPiece);
        productype = findViewById(R.id.itemWeight);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Toast.makeText(getApplicationContext(), checkedId + " " + group, Toast.LENGTH_SHORT).show();

                if (piece.isChecked()) {
                    productype.setHint("Enter Piece");
                } else {
                    productype.setHint("Enter Weight");
                }
            }
        });
        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productname.getText().toString().isEmpty()) {
                    productname.setError("Product Name should be not blank");
                    productname.requestFocus();
                } else if (productype.getText().toString().isEmpty()) {
                    productype.setError("Enter your product scale");
                    productype.requestFocus();
                } else {
                    CreateOrder();
                }
            }
        });
        productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productname.setCursorVisible(true);
            }
        });


    }

    private void CreateOrder() {

        String clientId = String.valueOf(SharedPrefManager.getInstance(this).getUser().getClientID());
        String userid = SharedPrefManager.getInstance(this).getUser().getUserID();

        try {
            String result = new OrderHelper.POSTCreateOrder().execute(clientId, productname.getText().toString(), layervalue.toString(), itemweight.getText().toString(), userid).get();
            if (result.isEmpty()) {
                Toast.makeText(this, "Invalid request", Toast.LENGTH_SHORT).show();
                result = new OrderHelper.POSTCreateOrder().execute(clientId, productname.getText().toString(), layervalue.toString(), itemweight.getText().toString()).get();
            } else {
                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);
                Toast.makeText(this, "Success request", Toast.LENGTH_SHORT).show();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
