package com.awizom.reliablepackaging.Adapter;

import java.lang.reflect.Type;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.ChangePassword;
import com.awizom.reliablepackaging.Config.AppConfig;
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

import dmax.dialog.SpotsDialog;
import uk.co.senab.photoview.PhotoViewAttacher;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    private List<NotificationModel> notificationModelList;
    private Context mCtx;
    String result = "";
    private AlertDialog progressDialog;
    public NotificationListAdapter(Context baseContext, List<NotificationModel> notificationModelList) {
        this.notificationModelList = notificationModelList;
        this.mCtx = baseContext;

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        NotificationModel c = notificationModelList.get(position);
        holder.orderid.setText(String.valueOf(c.getOrderId()));
        holder.notification_id.setText(String.valueOf(c.getNotificationID()));
        holder.notification_type.setText(c.getType().toString());
        if (c.isRead()) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        holder.notification_body.setText(c.getBody().toString());
        holder.notification_date.setText(String.valueOf(c.getDate().toString()).split("T")[0]);
         if(c.getType().toString().equals("Order"))
         {
             holder.noti_typeimage.setImageResource(R.drawable.shopping_cart);
         }
         else if(c.getType().toString().equals("Design"))

         {
             holder.noti_typeimage.setImageResource(R.drawable.design_noti);
         }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if (holder.notification_type.getText().toString().equals("Order")) {
                    readNotification(holder.notification_id.getText().toString());
                    Intent intent = new Intent(mCtx, OrderDetails.class);
                    intent.putExtra("OrderId", holder.orderid.getText().toString());
                    intent.putExtra("ImageLink", "imagelink");
                    mCtx.startActivity(intent);
                    dismissmethod();

                } else if (holder.notification_type.getText().toString().equals("Design")) {
                    readNotification(holder.notification_id.getText().toString());
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

    private void readNotification(String noti_id) {

        try {
            result = new ProfileHelper.PostReadNotification().execute(noti_id).get();
            if (result.isEmpty()) {
                Toast.makeText(mCtx, "Invalid request", Toast.LENGTH_SHORT).show();
                result = new ProfileHelper.PostReadNotification().execute(noti_id).get();
            } else {
                Gson gson = new Gson();
                Type getType = new TypeToken<ResultModel>() {
                }.getType();
                ResultModel resultModel = new Gson().fromJson(result, getType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return notificationModelList.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notificationlist, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView notification_body, notification_date, notification_type, orderid, notification_id;
        CardView cardView;
        ImageView noti_typeimage;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            progressDialog = new SpotsDialog(mCtx, R.style.Custom);
            notification_body = view.findViewById(R.id.noti_body);
            notification_id = view.findViewById(R.id.noti_ID);
            orderid = view.findViewById(R.id.noti_orderid);
            notification_date = view.findViewById(R.id.noti_date);
            cardView = view.findViewById(R.id.card_view);
            notification_type = view.findViewById(R.id.noti_type);
            noti_typeimage=view.findViewById(R.id.noti_typeImage);
        }


    }

}