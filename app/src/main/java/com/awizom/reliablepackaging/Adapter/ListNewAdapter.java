package com.awizom.reliablepackaging.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.HomePage;
import com.awizom.reliablepackaging.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class ListNewAdapter extends ArrayAdapter<Object> {

    private int resourceLayout;
    private Context mContext;
    String[] layerlist = {"Two Layer", "Three Layer"};
    String[] packtypelist = {"Pouching", "Roll"};
    private ArrayList<String> orderdetailslist = new ArrayList<String>();
    Button buildpositivbutto;
    private String layervalue = "0", packtypevalue = "";
    private android.app.AlertDialog progressDialog;
    private ArrayList<String> valueOfEditText = new ArrayList<String>();
    private ArrayList<String> valueOfproductname = new ArrayList<String>();
    private ArrayList<String> valueOfproductid = new ArrayList<String>();
    private ArrayList<String> valueofLayerType = new ArrayList<String>();
    private ArrayList<String> valueofPackType = new ArrayList<String>();
    Object[] itemslist;

    public ListNewAdapter(Context context, int resource, Object[] items, Button order) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.itemslist = items;
        this.buildpositivbutto = order;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View v = convertView;
        holder = new ViewHolder();
        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.adapter_itemlist, null);
        }

        Object p = getItem(position);
        progressDialog = new SpotsDialog(mContext, R.style.Custom);
        if (p != null) {
            holder.tt1 = (TextView) v.findViewById(R.id.productname);
            final int listLength = itemslist.length;
            holder.productid = v.findViewById(R.id.productid);
            holder.productid.setText(p.toString().split(">")[1]);
            holder.tt1.setText(p.toString().split(">")[0]);
            holder.et2 = (EditText) v.findViewById(R.id.weight_kg);

            holder.et2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (!holder.et2.getText().toString().isEmpty()) {
                            valueOfEditText.add(holder.et2.getText().toString());
                            valueOfproductname.add(holder.tt1.getText().toString());
                            valueOfproductid.add(holder.productid.getText().toString());
                        } else {
                            Toast.makeText(mContext, "Please fill all the values", Toast.LENGTH_LONG).show();
                        }
                        //     Toast.makeText(mContext, holder.et2.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            holder.layertype = v.findViewById(R.id.layertype);
            holder.packtype = v.findViewById(R.id.packType);

            final ArrayAdapter<String> arrayAdapterpack = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, packtypelist);
            holder.packtype.setAdapter(arrayAdapterpack);
            holder.packtype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String layertypedetails = parent.getItemAtPosition(position).toString();
                    if (layertypedetails.equals("Pouching")) {
                        //Toast.makeText(getApplicationContext(), "" + createComplaint, Toast.LENGTH_SHORT).show();
                        packtypevalue = "Pouching".toString();
                        valueofPackType.add(packtypevalue.toString());
                    } else {
                        packtypevalue = "Roll".toString();
                        valueofPackType.add(packtypevalue.toString());
                    }
                }
            });

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, layerlist);
            holder.layertype.setAdapter(arrayAdapter);
            holder.layertype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String layertypedetails = parent.getItemAtPosition(position).toString();
                    if (layertypedetails.equals("Two Layer")) {
                        //Toast.makeText(getApplicationContext(), "" + createComplaint, Toast.LENGTH_SHORT).show();
                        layervalue = "1".toString();
                        valueofLayerType.add(layervalue.toString());
                    } else {
                        layervalue = "2".toString();
                        valueofLayerType.add(layervalue.toString());
                    }
                }
            });

            buildpositivbutto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    progressDialog.show();
                    orderdetailslist.add(valueOfproductname.toString() + valueOfEditText.toString() + valueOfproductid.toString() + valueofLayerType.toString() + valueofPackType.toString());
                    //    Toast.makeText(mContext, orderdetailslist.toString(), Toast.LENGTH_LONG).show();
                    try {
                        String length = String.valueOf(valueOfproductname.size());
                        String result = new OrderHelper.POSTRebookPreOrder().execute(valueOfproductname.toString().toString(), valueOfproductid.toString(), valueOfEditText.toString(), valueofLayerType.toString(), valueofPackType.toString(), length.toString()).get();

                        if (result.isEmpty()) {
                            Toast.makeText(mContext, "Invalid request", Toast.LENGTH_SHORT).show();
                            result = new OrderHelper.POSTRebookPreOrder().execute(orderdetailslist.toString()).get();
                            dismissmethod();
                        } else {
                            Toast.makeText(mContext, "Successfully Re-Booked Order", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, HomePage.class);
                            mContext.startActivity(intent);
                            dismissmethod();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        dismissmethod();
                    }
                    dismissmethod();


                }
            });


          /*  holder.layertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String layertypedetails = parent.getItemAtPosition(position).toString();
                    if (layertypedetails.equals("Two Layer")) {
                        //Toast.makeText(getApplicationContext(), "" + createComplaint, Toast.LENGTH_SHORT).show();
                        layervalue = "1".toString();
                        valueofLayerType.add(layervalue.toString());
                    } else {
                        layervalue = "2".toString();
                        valueofLayerType.add(layervalue.toString());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/


        }

        return v;
    }

    private void dismissmethod() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 100);
    }


    public void getorderlist() {

        orderdetailslist.toString();
    }


    private class ViewHolder {
        EditText et2;
        TextView tt1, productid;
        MaterialBetterSpinner layertype, packtype;

    }
}