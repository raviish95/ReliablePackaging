package com.awizom.reliablepackaging.Adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

public class OrderDetailsAdapter extends RecyclerView.Adapter<com.awizom.reliablepackaging.Adapter.OrderDetailsAdapter.MyViewHolder> {


    private List<OrderDetailsView> orderDetails;
    private Context mCtx;
    private String imagelinkurl;

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
            Glide.with(mCtx).load(AppConfig.BASE_URL + c.getImageUrl().toString()).into(holder.productdesign);
        } catch (Exception e) {
            holder.productdesign.setImageResource(R.drawable.desgin_notapprove);
        }
        holder.productdesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, SelectDesign.class);
                intent.putExtra("OrderId", holder.orderid.getText().toString());
                mCtx.startActivity(intent);
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

        public TextView product, weight, orderid, amount, order_no;
        public ImageView productdesign;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);

            product = (TextView) view.findViewById(R.id.prod_name);
            productdesign = (ImageView) view.findViewById(R.id.productdesign);
            weight = (TextView) view.findViewById(R.id.weight);
            orderid = view.findViewById(R.id.orderid);
            amount = view.findViewById(R.id.amount);
            order_no = view.findViewById(R.id.order_no);
        }


    }

}