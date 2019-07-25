package com.awizom.reliablepackaging.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.AddOrderActivity;
import com.awizom.reliablepackaging.Config.AppConfig;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.HomePage;
import com.awizom.reliablepackaging.Model.Order;
import com.awizom.reliablepackaging.OrderDetails;
import com.awizom.reliablepackaging.R;
import com.awizom.reliablepackaging.RebookFillOrderActivity;
import com.awizom.reliablepackaging.RebookOrderActivity;
import com.awizom.reliablepackaging.SelectDesign;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import dmax.dialog.SpotsDialog;
import uk.co.senab.photoview.PhotoViewAttacher;

public class RebookOrderListAdapter extends RecyclerView.Adapter<RebookOrderListAdapter.MyViewHolder> {

    String imagestr;
    private List<Order> orderList;
    private Context mCtx;
    private AlertDialog progressDialog;

    private ArrayList<String> itemnamelist = new ArrayList<String>();
    private ArrayList<String> orderidlist = new ArrayList<String>();
    Button rebook;

    public RebookOrderListAdapter(Context baseContext, List<Order> orderList, Button rebook) {
        this.orderList = orderList;
        this.mCtx = baseContext;
        this.rebook = rebook;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Order c = orderList.get(position);
        holder.product.setText(c.getJobName());

        holder.weight.setText("Weight - " + String.valueOf(c.getWeight()));
        holder.orderid.setText(String.valueOf(c.getOrderId()));
        holder.layer_type.setText(c.getLayerName().toString());
        try {
            holder.imglinkurl.setText(AppConfig.BASE_URL + c.getImageUrl().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!(c.getImageUrl().toString() == null)) {
                holder.checking.setText("filled");
                Glide.with(mCtx).load(AppConfig.BASE_URL + c.getImageUrl().toString()).into(holder.categoryImage);

            } else {
                holder.categoryImage.setImageResource(R.drawable.desgin_notapprove);
                holder.checking.setText("blank");
            }
        } catch (Exception e) {
            holder.categoryImage.setImageResource(R.drawable.desgin_notapprove);
            e.printStackTrace();
        }


        rebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 progressDialog.show();
                Intent intent = new Intent(mCtx, RebookFillOrderActivity.class);
                intent.putExtra("itemslist", orderidlist);
                mCtx.startActivity(intent);
                dismissmethod();
                //   openItemConfirmDialog((itemnamelist.toArray()));


            }
        });
        holder.itemlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Toast.makeText(mCtx, "check", Toast.LENGTH_LONG).show();
                if (isChecked) {
                    orderidlist.add(holder.orderid.getText().toString());

                    itemnamelist.add(holder.product.getText().toString() + ">" + holder.orderid.getText().toString());

                } else {
                    orderidlist.remove(holder.orderid.getText().toString());
                    itemnamelist.remove(holder.product.getText().toString() + ">" + holder.orderid.getText().toString());
                }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx,"rebook order",Toast.LENGTH_LONG).show();
                if (holder.itemlist.isChecked()) {
                    holder.itemlist.setChecked(false);
                } else {
                    holder.itemlist.setChecked(true);
                }// ShowConfirmRebookOrderDialog(holder.orderid.getText().toString(), mCtx);

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


    private void PostReorder(String orderId) {


        try {
            String result = new OrderHelper.POSTRebookOrder().execute(orderId.toString()).get();
            if (result.isEmpty()) {
                Toast.makeText(mCtx, "Invalid request", Toast.LENGTH_SHORT).show();
                result = new OrderHelper.POSTRebookOrder().execute(orderId.toString()).get();
            } else {
                Intent intent = new Intent(mCtx, HomePage.class);
                mCtx.startActivity(intent);
                Toast.makeText(mCtx, "Success request", Toast.LENGTH_SHORT).show();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {

        return orderList.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rebook_orderadapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView product, imglinkurl, weight, orderid, checking, layer_type;
        public ImageView categoryImage;
        private CheckBox itemlist;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            progressDialog = new SpotsDialog(mCtx, R.style.Custom);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.getContext();
            itemlist = view.findViewById(R.id.item_switch);
            imglinkurl = view.findViewById(R.id.image_link);
            product = (TextView) view.findViewById(R.id.prod_name);
            categoryImage = (ImageView) view.findViewById(R.id.categoryImage);
            weight = (TextView) view.findViewById(R.id.weight);
            orderid = view.findViewById(R.id.orderid);
            checking = view.findViewById(R.id.checking);
            layer_type = view.findViewById(R.id.layer);


        }


    }

}