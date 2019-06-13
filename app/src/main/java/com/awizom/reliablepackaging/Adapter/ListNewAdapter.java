package com.awizom.reliablepackaging.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.awizom.reliablepackaging.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;

public class ListNewAdapter extends ArrayAdapter<Object> {

    private int resourceLayout;
    private Context mContext;
    String[] layerlist = {"Two Layer", "Three Layer"};
    private MaterialBetterSpinner layertype;
    private String layervalue = "0";

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
            tt1.setText(p.toString().split(">")[0]);
                    }
        layertype = v.findViewById(R.id.layerType);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, layerlist);
        layertype.setAdapter(arrayAdapter);
        layertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String layertypedetails = parent.getItemAtPosition(position).toString();
                if (layertypedetails.equals("Two Layer")) {
                    //Toast.makeText(getApplicationContext(), "" + createComplaint, Toast.LENGTH_SHORT).show();
                    layervalue = "1";

                } else {
                    layervalue = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return v;
    }

}