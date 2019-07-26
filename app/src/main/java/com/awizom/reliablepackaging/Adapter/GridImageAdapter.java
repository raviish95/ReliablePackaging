package com.awizom.reliablepackaging.Adapter;

import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.MyAccount;
import com.awizom.reliablepackaging.MyOrderList;
import com.awizom.reliablepackaging.MyProfile;
import com.awizom.reliablepackaging.R;
import com.awizom.reliablepackaging.TodayDispatchOrder;
import com.awizom.reliablepackaging.UnapprovedOrdersList;

import dmax.dialog.SpotsDialog;

public class GridImageAdapter extends BaseAdapter {
    private Context mContext;
    LayoutInflater inflter;
    private AlertDialog progressDialog;
    // Constructor
    public GridImageAdapter(Context c) {
        mContext = c;
        inflter = (LayoutInflater.from(c));
        progressDialog = new SpotsDialog(mContext, R.style.Custom);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextView tv1;
        convertView = inflter.inflate(R.layout.activity_gridview, null);
       /* if (convertView == null) {
            imageView = new ImageView(mContext);

            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }*/

        imageView = (ImageView) convertView.findViewById(R.id.icon); // get the reference of ImageView
        tv1 = convertView.findViewById(R.id.textview);
        imageView.setImageResource(mThumbIds[position]);
        tv1.setText(imagename[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     Toast.makeText(mContext,String.valueOf(position)+"position",Toast.LENGTH_LONG).show();
                if (position == 0) {
                    progressDialog.show();
                    Intent intent = new Intent(mContext, MyProfile.class);
                    mContext.startActivity(intent);
                    dismissmethod();
                } else if (position == 1) {
                    progressDialog.show();
                    Intent intent = new Intent(mContext, MyOrderList.class);
                    intent.putExtra("Ordertype","ds");
                    intent.putExtra("HeaderName", "My Order");
                    mContext.startActivity(intent);
                    dismissmethod();
                } else if (position == 2) {
                    progressDialog.show();
                    Intent intent = new Intent(mContext, MyAccount.class);
                    mContext.startActivity(intent);
                    dismissmethod();
                } else if (position == 3) {
                    progressDialog.show();
                    Intent intent = new Intent(mContext, MyOrderList.class);
                    intent.putExtra("Ordertype","ds");
                    intent.putExtra("HeaderName", "My Job");
                    mContext.startActivity(intent);
                    dismissmethod();
                }
                else if (position == 4) {
                    progressDialog.show();
                    Intent intent = new Intent(mContext, UnapprovedOrdersList.class);
                    intent.putExtra("Ordertype","ds");
                    intent.putExtra("HeaderName", "Pending order's design");
                    mContext.startActivity(intent);
                    dismissmethod();
                }
                else if (position == 5) {
                    progressDialog.show();
                    Intent intent = new Intent(mContext, TodayDispatchOrder.class);
                    intent.putExtra("Ordertype", "TodayDispatch");
                    intent.putExtra("HeaderName", "Today Dispatch");
                    mContext.startActivity(intent);
                    dismissmethod();
                }
            }
        });
        return convertView;

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

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.resume, R.drawable.icons8purchaseorder100, R.drawable.icons8money100, R.drawable.icons8myjob100, R.drawable.pending_design,R.drawable.today_dispatch
    };
    public String[] imagename = {
            "My Profile", "My Order", "My Account", "My Job",  "Pending designs","Today Dispatch"
    };

}