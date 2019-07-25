package com.awizom.reliablepackaging.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.Config.AppConfig;
import com.awizom.reliablepackaging.Model.Order;
import com.awizom.reliablepackaging.R;
import com.awizom.reliablepackaging.RebookFillOrderActivity;
import com.bumptech.glide.Glide;

import dmax.dialog.SpotsDialog;

public class RebookListChooseAdapter extends RecyclerView.Adapter<RebookListChooseAdapter.MyViewHolder> {

    private List<Order> orderList;
    private Context mCtx;
    private AlertDialog progressDialog;
    private Button add;
    String[] layerlist = {"2 Layer", "3 Layer"};
    String[] packtypelist = {"Pouching", "Roll"};
    private ArrayList<String> valueOfweigh = new ArrayList<String>();
    private ArrayList<String> valueOfproductname = new ArrayList<String>();
    private ArrayList<String> valueOfproductid = new ArrayList<String>();
    private String layervalue = "0", packtypevalue = "";
    private ArrayList<String> valueofLayerType = new ArrayList<String>();
    private ArrayList<String> valueofPackType = new ArrayList<String>();
    private ArrayList<String> valueofNotiType = new ArrayList<String>();
    private ArrayList<String> orderdetailslist = new ArrayList<String>();
    String cheknotify = "No";
    RecyclerView recyclerView;

    private static final int PICK_FROM_GALLERY = 1;

    public RebookListChooseAdapter(Context baseContext, List<Order> orderList, Button order, RecyclerView recyclerView) {
        this.orderList = orderList;
        this.mCtx = baseContext;
        this.add = order;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Order c = orderList.get(position);
        holder.weight.setText(String.valueOf(c.getWeight()));
        holder.productname.setText(c.getJobName().toString());
        holder.orderid.setText(String.valueOf(c.getOrderId()));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mCtx, android.R.layout.simple_dropdown_item_1line, layerlist);
        holder.layertype.setAdapter(arrayAdapter);
        final ArrayAdapter<String> arrayAdapterpack = new ArrayAdapter<String>(mCtx, android.R.layout.simple_dropdown_item_1line, packtypelist);
        holder.packtype.setAdapter(arrayAdapterpack);
        try{
            holder.imgurl.setText(c.getImageUrl().toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        final String weights = holder.weight.getText().toString();
        try {
            Glide.with(mCtx).load(AppConfig.BASE_URL + c.getImageUrl().toString()).into(holder.designImg);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (c.getLayerName().toString().equals("2 Layer")) {

            holder.layertype.setSelection(0);

        } else {
            holder.layertype.setSelection(1);
        }
        if (c.getPackType().toString().equals("Pouching")) {
            holder.packtype.setSelection(0);
        } else {
            holder.packtype.setSelection(1);
        }
        holder.changeimg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.changeimg.isChecked()) {
                    holder.uploadImg.setVisibility(View.VISIBLE);
                } else {
                    holder.uploadImg.setVisibility(View.GONE);
                }
            }
        });
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getItemId(position);
            }
        });
      /*  holder.uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) mCtx, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        ((RebookFillOrderActivity)mCtx).startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

        holder.weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!holder.weight.getText().toString().isEmpty()) {
                        valueOfweigh.add(holder.weight.getText().toString());
                        valueOfproductname.add(holder.productname.getText().toString());
                        valueOfproductid.add(holder.orderid.getText().toString());
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

        holder.packtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.layertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String layertypedetails = parent.getItemAtPosition(position).toString();
                if (layertypedetails.equals("2 Layer")) {
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
        });
      /*  add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int lengthof = orderList.size();
                for (int i = 0; i < lengthof; i++) {
                    valueofNotiType.add("No");
                }

                {
                    progressDialog.show();
                    orderdetailslist.add(valueOfproductname.toString() + valueOfweigh.toString() + valueOfproductid.toString() + valueofLayerType.toString() + valueofPackType.toString());
                    //    Toast.makeText(mContext, orderdetailslist.toString(), Toast.LENGTH_LONG).show();
                    try {
                        String length = String.valueOf(valueOfproductname.size());
                        String result = new OrderHelper.POSTRebookPreOrder().execute(valueOfproductname.toString().toString(), valueOfproductid.toString(), valueOfweigh.toString(), valueofLayerType.toString(), valueofPackType.toString(), length.toString(), valueofNotiType.toString()).get();

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
        });*/


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

        return orderList.size();

    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_rebookchoose, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public EditText weight;
        TextView productname, orderid, imgurl;
        Spinner layertype, packtype;
        Switch switchnoti;
        CheckBox changeimg;
        Button uploadImg;
        LinearLayout ll,linear;
        ImageView designImg;


        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            progressDialog = new SpotsDialog(mCtx, R.style.Custom);
            switchnoti = view.findViewById(R.id.notify);
            imgurl = view.findViewById(R.id.imgurl);
            productname = view.findViewById(R.id.productname);
            designImg = view.findViewById(R.id.selectdesgin);
            ll = view.findViewById(R.id.ll);
            linear=view.findViewById(R.id.linearmain);
            changeimg = view.findViewById(R.id.changecheck);
            uploadImg = view.findViewById(R.id.uploadImg);
            layertype = view.findViewById(R.id.layertype);
            packtype = view.findViewById(R.id.packType);
            weight = view.findViewById(R.id.weight_kg);
            orderid = view.findViewById(R.id.orderId);
        }


    }

}