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
import android.widget.Toast;

import com.awizom.reliablepackaging.Config.AppConfig;
import com.awizom.reliablepackaging.Model.Order;
import com.awizom.reliablepackaging.OrderDetails;
import com.awizom.reliablepackaging.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class RebookOrderListAdapter extends RecyclerView.Adapter<RebookOrderListAdapter.MyViewHolder> {

    String imagestr;
    private List<Order> orderList;
    private Context mCtx;

    public RebookOrderListAdapter(Context baseContext, List<Order> orderList) {
        this.orderList = orderList;
        this.mCtx = baseContext;

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Order c = orderList.get(position);
        holder.product_name.setText(c.getProductName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx,"rebook order",Toast.LENGTH_LONG).show();
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

        public TextView product_name;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);

            product_name = (TextView) view.findViewById(R.id.prod_name);



        }


    }

}