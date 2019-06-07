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

import com.awizom.reliablepackaging.Config.AppConfig;
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.awizom.reliablepackaging.OrderDetails;
import com.awizom.reliablepackaging.R;
import com.awizom.reliablepackaging.SelectDesign;
import com.bumptech.glide.Glide;

import dmax.dialog.SpotsDialog;
import uk.co.senab.photoview.PhotoViewAttacher;

public class OrderDetailsAdapter extends RecyclerView.Adapter<com.awizom.reliablepackaging.Adapter.OrderDetailsAdapter.MyViewHolder> {


    private List<OrderDetailsView> orderDetails;
    private Context mCtx;
    private String imagelinkurl;
    private AlertDialog progressDialog;
    public OrderDetailsAdapter(Context baseContext, List<OrderDetailsView> orderList, String imageLink) {
        this.orderDetails = orderList;
        this.mCtx = baseContext;
        this.imagelinkurl = imageLink;
    }

    @Override
    public void onBindViewHolder(final com.awizom.reliablepackaging.Adapter.OrderDetailsAdapter.MyViewHolder holder, final int position) {
        OrderDetailsView c = orderDetails.get(position);
        holder.product.setText(c.getJobName().toString());
        holder.order_no.setText("Order No- " + String.valueOf(c.getOrderNo()));
        holder.weight.setText("Weight - " + String.valueOf(c.getWeight()));
        holder.orderid.setText(String.valueOf(c.getOrderId()));
        holder.amount.setText(" \u20B9" + String.valueOf(c.getTotalAmount()).toString());

        try {
            holder.image_linkurl.setText(AppConfig.BASE_URL + c.getImageUrl().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!(c.getImageUrl().toString() == null)) {
                holder.checking.setText("filled");
                Glide.with(mCtx).load(AppConfig.BASE_URL + c.getImageUrl().toString()).into(holder.productdesign);

            } else {
                holder.productdesign.setImageResource(R.drawable.desgin_notapprove);
                holder.checking.setText("blank");
            }
        } catch (Exception e) {
            holder.productdesign.setImageResource(R.drawable.desgin_notapprove);
            e.printStackTrace();
        }

        holder.productdesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (holder.checking.getText().toString().equals("filled")) {
                    //Toast.makeText(mCtx,"filled",Toast.LENGTH_LONG).show();
                    openZommImage(holder.image_linkurl.getText().toString(), mCtx);

                } else {
                     progressDialog.show();
                    Intent intent = new Intent(mCtx, SelectDesign.class);
                    intent.putExtra("OrderId", holder.orderid.getText().toString());
                    mCtx.startActivity(intent);
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
    private void openZommImage(String imagelinkid, Context mCtx) {

        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        String image = imagelinkid;
        final View dialogView = inflater.inflate(R.layout.image_view_alert, null);
        ImageView zoomImageView = dialogView.findViewById(R.id.zoomImage);
        ImageView close = dialogView.findViewById(R.id.close);

        Glide.with(mCtx).load(image).into(zoomImageView);
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(zoomImageView);
        pAttacher.update();
        dialogBuilder.setView(dialogView);
        dialogView.setBackgroundColor(Color.parseColor("#F0F8FF"));
        final android.support.v7.app.AlertDialog b = dialogBuilder.create();
        b.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
    }


    @Override
    public int getItemCount() {

        return orderDetails.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public OrderDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_orderdetails, parent, false);

        return new OrderDetailsAdapter.MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView product, weight, orderid, amount,image_linkurl, order_no,checking;
        public ImageView productdesign;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            progressDialog = new SpotsDialog(mCtx, R.style.Custom);
            image_linkurl=view.findViewById(R.id.image_link);
            product = (TextView) view.findViewById(R.id.prod_name);
            productdesign = (ImageView) view.findViewById(R.id.productdesign);
            weight = (TextView) view.findViewById(R.id.weight);
            orderid = view.findViewById(R.id.orderid);
            amount = view.findViewById(R.id.amount);
            checking = view.findViewById(R.id.checking);
            order_no = view.findViewById(R.id.order_no);
        }


    }

}