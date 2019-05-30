package com.awizom.reliablepackaging.Adapter;

import java.lang.reflect.Type;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.AddOrderActivity;
import com.awizom.reliablepackaging.Config.AppConfig;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.HomePage;
import com.awizom.reliablepackaging.Model.DesignDetails;
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.awizom.reliablepackaging.OrderDetails;
import com.awizom.reliablepackaging.R;
import com.awizom.reliablepackaging.RebookOrderActivity;
import com.awizom.reliablepackaging.SelectDesign;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import uk.co.senab.photoview.PhotoViewAttacher;

public class DesignDetailsAdapter extends RecyclerView.Adapter<com.awizom.reliablepackaging.Adapter.DesignDetailsAdapter.MyViewHolder> {


    private List<DesignDetails> designDetails;
    private Context mCtx;

    public DesignDetailsAdapter(Context baseContext, List<DesignDetails> designDetails) {
        this.designDetails = designDetails;
        this.mCtx = baseContext;
    }

    @Override
    public void onBindViewHolder(final com.awizom.reliablepackaging.Adapter.DesignDetailsAdapter.MyViewHolder holder, final int position) {
        DesignDetails c = designDetails.get(position);
        String imglink = AppConfig.BASE_URL + c.getImageUrl().toString();
        holder.imagelinkid.setText(c.getImageUrl().toString());
        holder.designId.setText(String.valueOf(c.getDesignId()));
        holder.orderId.setText(String.valueOf(c.getOrderID()));
        Glide.with(mCtx).load(imglink).into(holder.productdesign);

        holder.productdesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openZommImage(holder.imagelinkid.getText().toString(), mCtx);
            }
        });
        holder.approvedesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtheConfirmApprovedlg(mCtx, holder.designId.getText().toString(), holder.orderId.getText().toString());
            }
        });

    }

    private void showtheConfirmApprovedlg(Context mContext, final String designid, final String orderid) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Approve Design");
        alertDialog.setMessage("Do you want to approve this design?");
        alertDialog.setIcon(R.drawable.print);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String isApproved = "true";
                        String remarks = "";
                        postApprovedesign(designid.toString(), orderid.toString(), isApproved.toString(), remarks.toString());

                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();


    }

    private void postApprovedesign(String designId, String orderId, String isapproved, String remArks) {

        try {
            String result = new OrderHelper.POSTDesignApprove().execute(designId, orderId, isapproved, remArks).get();
            if (result.isEmpty()) {
                Toast.makeText(this.mCtx, "Invalid request", Toast.LENGTH_SHORT).show();
                result = new OrderHelper.POSTDesignApprove().execute(designId, orderId, isapproved, remArks).get();
            } else {

                showApproveDesign();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showApproveDesign() {


        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(mCtx);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        final View dialogView = inflater.inflate(R.layout.design_approved, null);
        Button okay = dialogView.findViewById(R.id.ok);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, HomePage.class);
                mCtx.startActivity(intent);
            }
        });
        dialogBuilder.setView(dialogView);

        final android.support.v7.app.AlertDialog b = dialogBuilder.create();
        b.show();


    }


    @Override
    public int getItemCount() {

        return designDetails.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public DesignDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_designdetails, parent, false);

        return new DesignDetailsAdapter.MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView productdesign;
        public TextView imagelinkid, designId, orderId;
        public Button approvedesign;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            productdesign = (ImageView) view.findViewById(R.id.design);
            imagelinkid = view.findViewById(R.id.imagelinkid);
            approvedesign = view.findViewById(R.id.approveDesign);
            designId = view.findViewById(R.id.designId);
            orderId = view.findViewById(R.id.orderId);
        }


    }

    private void openZommImage(String imagelinkid, Context mContex) {

        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContex);

        LayoutInflater inflater = LayoutInflater.from(mContex);
        String image = AppConfig.BASE_URL + imagelinkid;
        final View dialogView = inflater.inflate(R.layout.image_view_alert, null);
        ImageView zoomImageView = dialogView.findViewById(R.id.zoomImage);
        ImageView close = dialogView.findViewById(R.id.close);

        Glide.with(mContex).load(image).into(zoomImageView);
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(zoomImageView);
        pAttacher.update();
        dialogBuilder.setView(dialogView);
        dialogView.setBackgroundColor(Color.parseColor("#F0F8FF"));
        final android.support.v7.app.AlertDialog b = dialogBuilder.create();
        b.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
    }

}
