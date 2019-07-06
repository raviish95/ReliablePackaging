package com.awizom.reliablepackaging;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.chip.ChipGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.Adapter.DesignCheckboxAdapter;
import com.awizom.reliablepackaging.Adapter.DesignDetailsAdapter;
import com.awizom.reliablepackaging.Adapter.OrderDetailsAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Model.DesignDetails;
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;

public class SelectDesign extends AppCompatActivity {

    private RecyclerView recyclerView,recyclerViewDesign;
    private String orderid;
    List<DesignDetails> designDetails;
    DesignDetailsAdapter adapterdesigndetails;
    DesignCheckboxAdapter designCheckboxAdapter;
    private CheckBox agreeMent;
    private Button feedbackSubmit;
    private EditText feedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_design);
        orderid = getIntent().getStringExtra("OrderId");
        getApproveDesign();
        initview();
    }

    private void initview() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select Design");
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
        recyclerViewDesign=findViewById(R.id.recyclerforDesign);
        recyclerViewDesign.setHasFixedSize(true);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(SelectDesign.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDesign.setLayoutManager(horizontalLayoutManager);
        feedbackSubmit = findViewById(R.id.submit);
        agreeMent = findViewById(R.id.agreement);
        feedBack = findViewById(R.id.feedback);
        feedBack.setEnabled(false);

        agreeMent.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                      if (agreeMent.isChecked()) {
                          recyclerViewDesign.setVisibility(View.VISIBLE);
                          feedBack.setEnabled(true);
                          feedBack.setHint("Enter your own design details");
                      } else {
                          recyclerViewDesign.setVisibility(View.GONE);
                          feedBack.setHint("Check on declaration");
                          feedBack.setEnabled(false);
                      }
                  }
              }
        );
     /*   feedbackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agreeMent.isChecked()) {
                    if (!feedBack.getText().toString().isEmpty()) {

                        String designid = "";
                        String isApproved = "false";

                        postApprovedesign(designid.toString(), orderid.toString(), isApproved.toString(), feedBack.getText().toString());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "First check in declaration checkbox", Toast.LENGTH_LONG).show();
                }


            }
        });*/
        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getDesignList();
    }

    private void getApproveDesign() {

        try {
            String result = new OrderHelper.GETDesignApproveList().execute(orderid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<DesignDetails>>() {
            }.getType();

            if(result.equals("true"))
            {
                Toast.makeText(getApplicationContext(),"You have already approved your design",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(this,OrderDetails.class);
                intent.putExtra("ImageLink","imagelink");
                intent.putExtra("OrderId",orderid.toString());
                startActivity(intent);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postApprovedesign(String designId,  String isapproved, String remArks,String length) {

        try {
            String result = new OrderHelper.POSTDesignFeedback().execute(designId, orderid.toString(), isapproved, remArks,length).get();
            if (result.isEmpty()) {
                Toast.makeText(this, "Invalid request", Toast.LENGTH_SHORT).show();
                result = new OrderHelper.POSTDesignFeedback().execute(designId, orderid.toString(), isapproved, remArks,length).get();
            } else {
                showApproveDesign();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showApproveDesign() {

        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.design_approved, null);
        Button okay = dialogView.findViewById(R.id.ok);
        TextView textView = dialogView.findViewById(R.id.text);
        textView.setText("Feedback Submitted");
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectDesign.this, HomePage.class);
                startActivity(intent);
            }
        });
        dialogBuilder.setView(dialogView);
        final android.support.v7.app.AlertDialog b = dialogBuilder.create();
        b.show();

    }

    private void getDesignList() {
        try {
            String result = new OrderHelper.GETMDesignList().execute(orderid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<DesignDetails>>() {
            }.getType();
            designDetails = new Gson().fromJson(result, listType);
            adapterdesigndetails = new DesignDetailsAdapter(SelectDesign.this, designDetails);
            designCheckboxAdapter=new DesignCheckboxAdapter(this,designDetails,feedbackSubmit,feedBack,orderid.toString());
            recyclerViewDesign.setAdapter(designCheckboxAdapter);
            recyclerView.setAdapter(adapterdesigndetails);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
