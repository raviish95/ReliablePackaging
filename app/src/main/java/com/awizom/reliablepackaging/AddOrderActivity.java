package com.awizom.reliablepackaging;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import dmax.dialog.SpotsDialog;

public class AddOrderActivity extends AppCompatActivity {

    private TextInputEditText productname, itemweight;
    private RadioGroup radioGroup;
    private RadioButton weight, piece;
    private TextView productype;
    private Button submitOrder;
    private RelativeLayout relativeLayout;
    private MaterialBetterSpinner layertype, packType;
    private String layervalue = "0", weightvalue;
    private String packtypevalue = "";
    String[] layerlist = {"2 Layer", "3 Layer"};
    String[] packlist = {"Pouching", "Roll"};
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        initview();
    }

    private void initview() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Order");
        toolbar.setBackgroundColor(Color.parseColor("#094B6C"));
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
        progressDialog = new SpotsDialog(AddOrderActivity.this, R.style.Custom);
        itemweight = findViewById(R.id.itemWeight);
        layertype = findViewById(R.id.layerType);
        packType = findViewById(R.id.packType);
        final ArrayAdapter<String> arrayAdapterpack = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, packlist);
        packType.setAdapter(arrayAdapterpack);
        packType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String packtypedetails = parent.getItemAtPosition(position).toString();
                if (packtypedetails.equals("Pouching")) {
                    //Toast.makeText(getApplicationContext(), "" + createComplaint, Toast.LENGTH_SHORT).show();
                    packtypevalue = "Pouching".toString();
                    //valueofLayerType.add(layervalue.toString());
                } else {
                    packtypevalue = "Roll".toString();
                    //valueofLayerType.add(layervalue.toString());
                }
            }
        });
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, layerlist);
        layertype.setAdapter(arrayAdapter);
        layertype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String layertypedetails = parent.getItemAtPosition(position).toString();
                if (layertypedetails.equals("2 Layer")) {
                    //Toast.makeText(getApplicationContext(), "" + createComplaint, Toast.LENGTH_SHORT).show();
                    layervalue = "1".toString();
                    //valueofLayerType.add(layervalue.toString());
                } else {
                    layervalue = "2".toString();
                    //valueofLayerType.add(layervalue.toString());
                }
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
                progressDialog.show();
                if (productname.getText().toString().isEmpty()) {
                    progressDialog.dismiss();
                    productname.setError("Product Name should be not blank");
                    productname.requestFocus();
                } else if (productype.getText().toString().isEmpty()) {
                    progressDialog.dismiss();
                    productype.setError("Enter your product scale");
                    productype.requestFocus();
                } else if (layervalue.toString().equals("0")) {
                    progressDialog.dismiss();
                    layertype.setError("Layer type should be not blank");
                    layertype.requestFocus();
                } else if (packtypevalue.toString().equals("")) {
                    progressDialog.dismiss();
                    packType.setError("Pack type should be not blank");
                    packType.requestFocus();
                } else {
                    final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(AddOrderActivity.this);
                    dialogBuilder.setCancelable(false);
                    LayoutInflater inflater = LayoutInflater.from(AddOrderActivity.this);
                    final View dialogView = inflater.inflate(R.layout.notification_want, null);
                    final AlertDialog progressDialogs= new SpotsDialog(AddOrderActivity.this, R.style.Custom);
                    Button yesnoti = dialogView.findViewById(R.id.yes);
                    Button nonoti = dialogView.findViewById(R.id.no);
                    ImageView close = dialogView.findViewById(R.id.close);
                    TextView textView = dialogView.findViewById(R.id.text);
                    yesnoti.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressDialogs.show();
                            String notif = "Yes";
                            CreateOrder(notif);
                        }
                    });

                    dialogBuilder.setView(dialogView);
                    final android.support.v7.app.AlertDialog b = dialogBuilder.create();
                    b.show();
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            b.dismiss();
                            progressDialog.dismiss();
                        }
                    });
                    nonoti.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressDialogs.show();
                            String notif = "No";
                            CreateOrder(notif);
                        }
                    });


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

    private void CreateOrder(String notif) {

        progressDialog.show();
        String clientId = String.valueOf(SharedPrefManager.getInstance(this).getUser().getClientID());
        String userid = SharedPrefManager.getInstance(this).getUser().getUserID();

        try {

            String result = new OrderHelper.POSTCreateOrder().execute(clientId, productname.getText().toString(), layervalue.toString(), itemweight.getText().toString(), userid, packtypevalue.toString(), notif.toString()).get();
            if (result.isEmpty()) {
                  dismismethod();
                Toast.makeText(this, "Invalid request", Toast.LENGTH_SHORT).show();
                result = new OrderHelper.POSTCreateOrder().execute(clientId, productname.getText().toString(), layervalue.toString(), itemweight.getText().toString(), userid, packtypevalue.toString()).get();
            } else {
                  dismismethod();
                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);
                Toast.makeText(this, "Success request", Toast.LENGTH_SHORT).show();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dismismethod() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 100);
    }

}
