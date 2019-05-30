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

import com.awizom.reliablepackaging.Model.Order;
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.awizom.reliablepackaging.OrderDetails;
import com.awizom.reliablepackaging.R;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    String imagestr;
    private List<Order> orderList;
    private Context mCtx;

    public OrderListAdapter(Context baseContext, List<Order> orderList) {
        this.orderList = orderList;
        this.mCtx = baseContext;

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Order c = orderList.get(position);
        holder.product.setText(c.getProductName());
        holder.weight.setText("Weight - " + String.valueOf(c.getWeight()));
        holder.orderid.setText(String.valueOf(c.getOrderId()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, OrderDetails.class);
                intent.putExtra("OrderId",holder.orderid.getText().toString());
                mCtx.startActivity(intent);
            }
        });


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

        public TextView product, imglinkurl, weight, orderid;
        public ImageView categoryImage;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);

            product = (TextView) view.findViewById(R.id.prod_name);
            categoryImage = (ImageView) view.findViewById(R.id.categoryImage);
            weight = (TextView) view.findViewById(R.id.weight);
            orderid = view.findViewById(R.id.orderid);
        }


    }

}