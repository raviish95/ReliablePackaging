package com.awizom.reliablepackaging;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.Adapter.MyOrderListAdapter;
import com.awizom.reliablepackaging.Adapter.NewRecyclerAdapter;
import com.awizom.reliablepackaging.Adapter.OrderListAdapter;
import com.awizom.reliablepackaging.Adapter.RebookListChooseAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RebookFillOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<Order> orderlist;
    RebookListChooseAdapter customAdapter;
    ArrayList<String> itemnamelist = new ArrayList<String>();
    private ArrayList<String> valueOfproductname = new ArrayList<String>();
    private ArrayList<String> valueOfproductid = new ArrayList<String>();
    private ArrayList<String> valueOfweight = new ArrayList<String>();
    private ArrayList<String> valueofLayerType = new ArrayList<String>();
    private ArrayList<String> valueofPackType = new ArrayList<String>();
    private ArrayList<String> valueofNotiType = new ArrayList<String>();
    Button order;
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rebook_fill_order);
        initview();
    }

    private void initview() {

        itemnamelist = (ArrayList<String>) getIntent().getSerializableExtra("itemslist");
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Fill order details");
        toolbar.setBackgroundColor(Color.parseColor("#094B6C"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        rl = findViewById(R.id.rl);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        order = findViewById(R.id.orders);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* Object[] itemNameList= itemnamelist.toArray();*/
        getOrderforRebook();
       /* customAdapter = new NewRecyclerAdapter(this,itemNameList, order);
        recyclerView.setAdapter(customAdapter);*/
    }

    private void getOrderforRebook() {

        String Length = String.valueOf(itemnamelist.toArray().length);
        try {
            // Toast.makeText(getApplicationContext(), "deviceid->" + FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_LONG).show();
            String result = new OrderHelper.GETMyChooseRebookOrder().execute(itemnamelist.toString(), Length).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            orderlist = new Gson().fromJson(result, listType);
            customAdapter = new RebookListChooseAdapter(RebookFillOrderActivity.this, orderlist, order, recyclerView);
            recyclerView.setAdapter(customAdapter);

            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < orderlist.size(); i++) {
                        View view = recyclerView.getChildAt(i);


                        EditText weightkg = view.findViewById(R.id.weight_kg);
                        String wkg = weightkg.getText().toString();
                        if (wkg.isEmpty()) {
                            weightkg.setError("Empty");
                            weightkg.requestFocus();
                            return;
                        } else {
                            valueOfweight.add(wkg.toString());
                        }

                        TextView productname = (TextView) view.findViewById(R.id.productname);
                        String Pname = productname.getText().toString();
                        if (Pname.isEmpty()) {
                            productname.setError("Empty");
                            productname.requestFocus();

                        } else {
                            valueOfproductname.add(Pname.toString());
                        }

                        TextView orderid = view.findViewById(R.id.orderId);
                        String Oid = orderid.getText().toString();
                        if (Oid.isEmpty()) {
                            orderid.setError("Empty");
                            orderid.requestFocus();
                        } else {
                            valueOfproductid.add(Oid.toString());
                        }

                        Spinner spinner = view.findViewById(R.id.layertype);
                        String spinnervl = "1";
                        if (spinner.getSelectedItemPosition() == 0) {
                            spinnervl = "1".toString();
                        }
                        {
                            spinnervl = "2".toString();
                        }
                        valueofLayerType.add(spinnervl.toString());


                        Spinner spinnerpack = view.findViewById(R.id.packType);
                        String spinnerpackvl = "1";
                        if (spinner.getSelectedItemPosition() == 0) {
                            spinnerpackvl = "Pouching".toString();
                        }
                        {
                            spinnerpackvl ="Roll".toString();
                        }
                        valueofPackType.add(spinnerpackvl.toString());

                        Switch switchnoti=findViewById(R.id.notify);
                        String cheknotify;
                        if(switchnoti.isChecked())
                        {
                            cheknotify = "Yes".toString();
                        }
                        else
                        {
                            cheknotify = "No".toString();
                        }
                        valueofNotiType.add(cheknotify.toString());
                    }

                    try {
                        String length = String.valueOf(valueOfproductname.size());
                        String results = new OrderHelper.POSTRebookPreOrder().execute(valueOfproductname.toString().toString(), valueOfproductid.toString(), valueOfweight.toString(), valueofLayerType.toString(), valueofPackType.toString(), length.toString(), valueofNotiType.toString()).get();

                        if (results.isEmpty()) {
                            Toast.makeText(RebookFillOrderActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
                            results = new OrderHelper.POSTRebookPreOrder().execute(valueOfproductname.toString().toString(), valueOfproductid.toString(), valueOfweight.toString(), valueofLayerType.toString(), valueofPackType.toString(), length.toString(), valueofNotiType.toString()).get();


                        } else {
                            Toast.makeText(RebookFillOrderActivity.this, "Successfully Re-Booked Order", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RebookFillOrderActivity.this, HomePage.class);
                            startActivity(intent);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
