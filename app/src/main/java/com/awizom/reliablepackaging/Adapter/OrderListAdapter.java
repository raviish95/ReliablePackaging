package com.awizom.reliablepackaging.Adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.Config.AppConfig;
import com.awizom.reliablepackaging.Model.Order;
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.awizom.reliablepackaging.OrderDetails;
import com.awizom.reliablepackaging.R;
import com.awizom.reliablepackaging.SelectDesign;
import com.bumptech.glide.Glide;

import dmax.dialog.SpotsDialog;
import uk.co.senab.photoview.PhotoViewAttacher;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    String imagestr;
    private List<Order> orderList;
    private Context mCtx;
    private AlertDialog progressDialog;

    public OrderListAdapter(Context baseContext, List<Order> orderList) {
        this.orderList = orderList;
        this.mCtx = baseContext;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Order c = orderList.get(position);

        holder.product.setText(c.getJobName());
        holder.weight.setText("Weight - " + String.valueOf(c.getWeight()));

        try {
            holder.layer_type.setText(c.getLayerName().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.orderid.setText(String.valueOf(c.getOrderId()));
        try {
            holder.imglinkurl.setText(AppConfig.BASE_URL + c.getImageUrl().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!(c.getImageUrl().toString() == null)) {

                Glide.with(mCtx).load(AppConfig.BASE_URL + c.getImageUrl().toString()).into(holder.categoryImage);
                holder.checking.setText("filled");
            } else {
                holder.categoryImage.setImageResource(R.drawable.desgin_notapprove);
                holder.checking.setText("blank");
            }
        } catch (Exception e) {
            holder.categoryImage.setImageResource(R.drawable.desgin_notapprove);

            e.printStackTrace();
        }


    /*    holder.categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checking.getText().toString().equals("filled")) {
                    //Toast.makeText(mCtx,"filled",Toast.LENGTH_LONG).show();
                    openZommImage(holder.imglinkurl.getText().toString(), mCtx);

                } else {

                    Intent intent = new Intent(mCtx, SelectDesign.class);
                    intent.putExtra("OrderId", holder.orderid.getText().toString());
                    mCtx.startActivity(intent);
                }
            }
        });*/


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressDialog.show();
                Intent intent = new Intent(mCtx, OrderDetails.class);
                intent.putExtra("OrderId", holder.orderid.getText().toString());
                intent.putExtra("ImageLink", holder.imglinkurl.getText().toString());
                mCtx.startActivity(intent);
                dismissmethod();
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

        return orderList.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_categorylist, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView product, imglinkurl, weight, orderid, checking, layer_type;
        public ImageView categoryImage;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            progressDialog = new SpotsDialog(mCtx, R.style.Custom);
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