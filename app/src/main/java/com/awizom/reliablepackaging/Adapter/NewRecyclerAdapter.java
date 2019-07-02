package com.awizom.reliablepackaging.Adapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.ChangePassword;
import com.awizom.reliablepackaging.Config.AppConfig;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Helper.ProfileHelper;
import com.awizom.reliablepackaging.HomePage;
import com.awizom.reliablepackaging.Model.NotificationModel;
import com.awizom.reliablepackaging.Model.Order;
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.awizom.reliablepackaging.Model.ResultModel;
import com.awizom.reliablepackaging.OrderDetails;
import com.awizom.reliablepackaging.R;
import com.awizom.reliablepackaging.SelectDesign;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import dmax.dialog.SpotsDialog;
import uk.co.senab.photoview.PhotoViewAttacher;

public class NewRecyclerAdapter extends RecyclerView.Adapter<NewRecyclerAdapter.MyViewHolder> {

    private Object[] notificationModelList;
    private Button rebookbutton;
    private Context mCtx;
    String result = "";
    String[] layerlist = {"Two Layer", "Three Layer"};
    String[] packtypelist = {"Pouching", "Roll"};
    private AlertDialog progressDialog;
    private ArrayList<String> valueOfEditText = new ArrayList<String>();
    private ArrayList<String> valueOfproductname = new ArrayList<String>();
    private ArrayList<String> valueOfproductid = new ArrayList<String>();
    private String layervalue = "0", packtypevalue = "";
    private ArrayList<String> valueofLayerType = new ArrayList<String>();
    private ArrayList<String> valueofPackType = new ArrayList<String>();
    private ArrayList<String> valueofNotiType = new ArrayList<String>();
    private ArrayList<String> orderdetailslist = new ArrayList<String>();
    String cheknotify = "No";

    public NewRecyclerAdapter(Context baseContext, Object[] notificationModelList, Button add) {
        this.notificationModelList = notificationModelList;
        this.mCtx = baseContext;
        this.rebookbutton = add;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        List list = Arrays.asList(notificationModelList);
        String data = String.valueOf(list.get(position));
        holder.productid.setText(data.toString().split(">")[1]);
        holder.tt1.setText(data.toString().split(">")[0]);
        holder.et2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!holder.et2.getText().toString().isEmpty()) {
                        valueOfEditText.add(holder.et2.getText().toString());
                        valueOfproductname.add(holder.tt1.getText().toString());
                        valueOfproductid.add(holder.productid.getText().toString());
                    } else {
                        Toast.makeText(mCtx, "Please fill all the values", Toast.LENGTH_LONG).show();
                    }
                    //     Toast.makeText(mContext, holder.et2.getText().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.switchnoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cheknotify = "Yes".toString();
                    //    valueofNotiType.add(cheknotify.toString());
                } else {
                    cheknotify = "No".toString();
                }
                valueofNotiType.add(cheknotify.toString());
            }

        });

        final ArrayAdapter<String> arrayAdapterpack = new ArrayAdapter<String>(mCtx, android.R.layout.simple_dropdown_item_1line, packtypelist);
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

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mCtx, android.R.layout.simple_dropdown_item_1line, layerlist);
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
        rebookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int lengthof = notificationModelList.length;
                for (int i = 0; i < lengthof; i++) {
                    valueofNotiType.add("No");
                }
                if (!((valueOfEditText.toArray().length == lengthof) || (valueofPackType.toArray().length == lengthof) || valueofLayerType.toArray().length == lengthof)) {
                    Toast toast = Toast.makeText(mCtx, "Please fill all the fields...", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    //To change the Background of Toast
                    view.setBackgroundColor(Color.WHITE);
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    //Shadow of the Of the Text Color
                    text.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
                    text.setTextColor(Color.RED);
                    text.setTextSize(20);
                    toast.show();
                } else {
                    progressDialog.show();
                    orderdetailslist.add(valueOfproductname.toString() + valueOfEditText.toString() + valueOfproductid.toString() + valueofLayerType.toString() + valueofPackType.toString());
                    //    Toast.makeText(mContext, orderdetailslist.toString(), Toast.LENGTH_LONG).show();
                    try {
                        String length = String.valueOf(valueOfproductname.size());
                        String result = new OrderHelper.POSTRebookPreOrder().execute(valueOfproductname.toString().toString(), valueOfproductid.toString(), valueOfEditText.toString(), valueofLayerType.toString(), valueofPackType.toString(), length.toString(), valueofNotiType.toString()).get();

                        if (result.isEmpty()) {
                            Toast.makeText(mCtx, "Invalid request", Toast.LENGTH_SHORT).show();
                            result = new OrderHelper.POSTRebookPreOrder().execute(orderdetailslist.toString()).get();
                            dismissmethod();
                        } else {
                            Toast.makeText(mCtx, "Successfully Re-Booked Order", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mCtx, HomePage.class);
                            mCtx.startActivity(intent);
                            dismissmethod();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        dismissmethod();
                    }
                    dismissmethod();


                }
            }
        });
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


    @Override
    public int getItemCount() {

        return notificationModelList.length;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_itemlist, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText et2;
        TextView tt1, productid;
        MaterialBetterSpinner layertype, packtype;
        Switch switchnoti;


        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            progressDialog = new SpotsDialog(mCtx, R.style.Custom);
            tt1 = view.findViewById(R.id.productname);
            productid = view.findViewById(R.id.productid);
            switchnoti = view.findViewById(R.id.notify);
            et2 = (EditText) view.findViewById(R.id.weight_kg);
            layertype = view.findViewById(R.id.layertype);
            packtype = view.findViewById(R.id.packType);

        }


    }

}