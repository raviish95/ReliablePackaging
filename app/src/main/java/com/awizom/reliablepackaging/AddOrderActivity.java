package com.awizom.reliablepackaging;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class AddOrderActivity extends AppCompatActivity {

    private TextInputEditText productname;
    private RadioGroup radioGroup;
    private RadioButton weight, piece;
    private TextView productype;
    private Button loginbutton;
    private RelativeLayout relativeLayout;

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
        relativeLayout=findViewById(R.id.rootView);
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
        loginbutton = findViewById(R.id.loginButton);
        weight = findViewById(R.id.radioWeight);
        piece = findViewById(R.id.radioPiece);
        productype = findViewById(R.id.itemweight);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productname.getText().toString().isEmpty()) {
                    productname.setError("Product Name should be not blank");
                    productname.requestFocus();
                } else if (productype.getText().toString().isEmpty()) {
                    productype.setError("Enter your product scale");
                    productype.requestFocus();
                }
            }
        });
        productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productname.setCursorVisible(true);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Toast.makeText(getApplicationContext(), checkedId + " " + group, Toast.LENGTH_SHORT).show();

               if(piece.isChecked())
               {
                   productype.setHint("Enter Piece");
               }
               else{
                   productype.setHint("Enter Weight");
               }
            }
        });
    }
}
