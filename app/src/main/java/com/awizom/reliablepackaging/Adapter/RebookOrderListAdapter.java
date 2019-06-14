package com.awizom.reliablepackaging.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.AddOrderActivity;
import com.awizom.reliablepackaging.Config.AppConfig;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.HomePage;
import com.awizom.reliablepackaging.Model.Order;
import com.awizom.reliablepackaging.OrderDetails;
import com.awizom.reliablepackaging.R;
import com.awizom.reliablepackaging.RebookOrderActivity;
import com.awizom.reliablepackaging.SelectDesign;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import dmax.dialog.SpotsDialog;
import uk.co.senab.photoview.PhotoViewAttacher;

public class RebookOrderListAdapter extends RecyclerView.Adapter<RebookOrderListAdapter.MyViewHolder> {

    String imagestr;
    private List<Order> orderList;
    private Context mCtx;
    private AlertDialog progressDialog;

    private ArrayList<String> itemnamelist = new ArrayList<String>();
    private ArrayList<String> orderidlist = new ArrayList<String>();
    Button rebook;

    public RebookOrderListAdapter(Context baseContext, List<Order> orderList, Button rebook) {
        this.orderList = orderList;
        this.mCtx = baseContext;
        this.rebook = rebook;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Order c = orderList.get(position);
        holder.product.setText(c.getJobName());

        holder.weight.setText("Weight - " + String.valueOf(c.getWeight()));

        holder.orderid.setText(String.valueOf(c.getOrderId()));
        try {
            holder.imglinkurl.setText(AppConfig.BASE_URL + c.getImageUrl().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!(c.getImageUrl().toString() == null)) {
                holder.checking.setText("filled");
                Glide.with(mCtx).load(AppConfig.BASE_URL + c.getImageUrl().toString()).into(holder.categoryImage);

            } else {
                holder.categoryImage.setImageResource(R.drawable.desgin_notapprove);
                holder.checking.setText("blank");
            }
        } catch (Exception e) {
            holder.categoryImage.setImageResource(R.drawable.desgin_notapprove);
            e.printStackTrace();
        }


     /*   holder.categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checking.getText().toString().equals("filled")) {
                    //Toast.makeText(mCtx,"filled",Toast.LENGTH_LONG).show();
                    openZommImage(holder.imglinkurl.getText().toString(), mCtx);

                } else {

                    Intent intent = new Intent(mCtx, SelectDesign.class);
                    intent.putExtra("OrderId", holder.orderid.getText().toString());
                    mCtx.startActivity(intent);
                }
            }
        });*/
        rebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openItemConfirmDialog((itemnamelist.toArray()));
                Toast.makeText(mCtx, itemnamelist.toString(), Toast.LENGTH_LONG).show();

            }
        });
        holder.itemlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(mCtx, "check", Toast.LENGTH_LONG).show();
                if (isChecked) {
                    itemnamelist.add(holder.product.getText().toString() + ">" + holder.orderid.getText().toString());

                } else {
                    itemnamelist.remove(holder.product.getText().toString() + ">" + holder.orderid.getText().toString());

                }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx,"rebook order",Toast.LENGTH_LONG).show();
                ShowConfirmRebookOrderDialog(holder.orderid.getText().toString(), mCtx);

            }
        });
    }

    private void openItemConfirmDialog(Object[] itemNameList) {


        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(mCtx);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        final View dialogView = inflater.inflate(R.layout.dialog_openordercinfirm, null);
        dialogBuilder.setIcon(R.drawable.reliables);
      /*  dialogBuilder.setNeutralButtonIcon(mCtx.getResources().getDrawable(R.drawable.close_blue));
*/

       /* dialogBuilder.setPositiveButtonIcon(mCtx.getResources().getDrawable(R.drawable.check_box_green_24dp));

*/
        ListView listView = dialogView.findViewById(R.id.listView);
        Button order=dialogView.findViewById(R.id.orders);
        final ListNewAdapter customAdapter = new ListNewAdapter(mCtx, R.layout.adapter_itemlist, itemNameList, order);

        listView.setAdapter(customAdapter);
       /* dialogBuilder.setPositiveButton("Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

              customAdapter.getorderlist();


            }
        });*/
        dialogBuilder.setCancelable(true);

        dialogBuilder.setView(dialogView);

        final android.support.v7.app.AlertDialog b = dialogBuilder.create();
        b.show();

    }

    private void ShowConfirmRebookOrderDialog(final String orderid, Context context) {
        progressDialog = new SpotsDialog(context, R.style.Custom);
        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(mCtx);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        final View dialogView = inflater.inflate(R.layout.dialog_reoorder, null);

        Button ok = dialogView.findViewById(R.id.btn_okay);
        Button cancel = dialogView.findViewById(R.id.btn_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                PostReorder(orderid.toString());
            }
        });

        dialogBuilder.setView(dialogView);

        final android.support.v7.app.AlertDialog b = dialogBuilder.create();
        b.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.setCancelable(true);
                b.dismiss();
            }
        });
    }

    private void PostReorder(String orderId) {


        try {
            String result = new OrderHelper.POSTRebookOrder().execute(orderId.toString()).get();
            if (result.isEmpty()) {
                Toast.makeText(mCtx, "Invalid request", Toast.LENGTH_SHORT).show();
                result = new OrderHelper.POSTRebookOrder().execute(orderId.toString()).get();
            } else {
                Intent intent = new Intent(mCtx, HomePage.class);
                mCtx.startActivity(intent);
                Toast.makeText(mCtx, "Success request", Toast.LENGTH_SHORT).show();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {

        return orderList.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rebook_orderadapter, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView product, imglinkurl, weight, orderid, checking, layer_type;
        public ImageView categoryImage;
        private CheckBox itemlist;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            itemlist = view.findViewById(R.id.item_switch);
            imglinkurl = view.findViewById(R.id.image_link);
            product = (TextView) view.findViewById(R.id.prod_name);
            categoryImage = (ImageView) view.findViewById(R.id.categoryImage);
            weight = (TextView) view.findViewById(R.id.weight);
            orderid = view.findViewById(R.id.orderid);
            checking = view.findViewById(R.id.checking);
            layer_type = view.findViewById(R.id.layer);


        }


    }

}