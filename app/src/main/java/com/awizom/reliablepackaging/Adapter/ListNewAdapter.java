package com.awizom.reliablepackaging.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.awizom.reliablepackaging.R;

import java.util.List;

public class ListNewAdapter extends ArrayAdapter<Object> {

    private int resourceLayout;
    private Context mContext;

    public ListNewAdapter(Context context, int resource, Object[] items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.adapter_itemlist, null);
        }

        Object p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.productname);


            tt1.setText(p.toString());


        }

        return v;
    }

}