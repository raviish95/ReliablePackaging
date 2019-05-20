package com.awizom.reliablepackaging;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class AddOrderActivity extends AppCompatActivity {

    private TextInputEditText Proudctname;
    private RadioGroup radioGroup;
    private RadioButton weight, piece;
    private TextView productype;

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
        radioGroup = findViewById(R.id.Product_Scale);
        Proudctname = findViewById(R.id.prod_name);
        weight = findViewById(R.id.radioWeight);
        piece = findViewById(R.id.radioPiece);
        productype = findViewById(R.id.itemweight);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Toast.makeText(getApplicationContext(), checkedId + " " + group, Toast.LENGTH_SHORT).show();
                if (checkedId == 2131230896) {

                    productype.setHint("Enter Piece");
                } else {
                    productype.setHint("Enter Weight");
                }

            }
        });
    }
}
