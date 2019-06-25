package com.awizom.reliablepackaging.Adapter;

import android.content.Context;

import android.content.Intent;
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

    // Constructor
    public GridImageAdapter(Context c) {
        mContext = c;
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

        if (convertView == null) {
            imageView = new ImageView(mContext);

            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,String.valueOf(position)+"position",Toast.LENGTH_LONG).show();
                if (position==0)
                {

                    Intent intent=new Intent(mContext, MyProfile.class);
                    mContext.startActivity(intent);
                }
                else if(position==1)
                {
                    Intent intent=new Intent(mContext, MyOrderList.class);
                    mContext.startActivity(intent);

                }
                else if(position==2)
                {
                    Intent intent=new Intent(mContext, MyAccount.class);
                    mContext.startActivity(intent);

                }
                else if(position==3)
                {
                    Intent intent=new Intent(mContext, MyAccount.class);
                    mContext.startActivity(intent);

                }
            }
        });
        return imageView;

    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
         R.drawable.customer_image,R.drawable.icons8shopping,R.drawable.icons8money,R.drawable.icons8blueprint
    };

}