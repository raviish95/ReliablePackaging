package com.awizom.reliablepackaging.Adapter;

import android.content.Context;

import android.content.Intent;
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

public class GridImageAdapter extends BaseAdapter {
    private Context mContext;
    LayoutInflater inflter;

    // Constructor
    public GridImageAdapter(Context c) {
        mContext = c;
        inflter = (LayoutInflater.from(c));
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
                    Intent intent = new Intent(mContext, MyProfile.class);
                    mContext.startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(mContext, MyOrderList.class);
                    intent.putExtra("HeaderName", "My Order");
                    mContext.startActivity(intent);

                } else if (position == 2) {
                    Intent intent = new Intent(mContext, MyAccount.class);
                    mContext.startActivity(intent);

                } else if (position == 3) {
                    Intent intent = new Intent(mContext, MyOrderList.class);
                    intent.putExtra("HeaderName", "My Job");
                    mContext.startActivity(intent);
                }
            }
        });
        return convertView;

    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.customer_image, R.drawable.icons8purchaseorder100, R.drawable.icons8money100, R.drawable.icons8myjob100
    };
    public String[] imagename = {
            "My Profile", "My Order", "My Account", "My Job"
    };

}