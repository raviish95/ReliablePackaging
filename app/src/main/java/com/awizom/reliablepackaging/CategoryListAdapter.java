package com.awizom.reliablepackaging;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.Config.AppConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {
    public static final int SELECT_PHOTO = 100;
    String imagestr;
    Intent intent;
    String catalogName;
    AutoCompleteTextView categoryNames;
    ImageView imageView;
    String result = "";
    private List<Catalog> categorylist;
    private Context mCtx;

    public CategoryListAdapter(Context baseContext, List<Catalog> categorylist) {
        this.categorylist = categorylist;
        this.mCtx = baseContext;

    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Catalog c = categorylist.get(position);
        holder.category.setText(c.getCategory());
        holder.catalogid.setText(String.valueOf(c.getCatalogID()));
        holder.imglinkurl.setText(c.getImage());
        imagestr = AppConfig.BASE_URL + c.getImage();
        holder.catalogname.setText(c.getCatalogName());

        try {
            if (c.getImage() == null) {

            } else {
                Glide.with(mCtx)
                        .load(imagestr)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(holder.categoryImage);


                // Glide.with(mCtx).load(imagestr).into(holder.categoryImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String categorynem = holder.category.getText().toString();
        final String cetlogId = holder.catalogid.getText().toString();
        final String cetlogName = holder.catalogname.getText().toString();
        final String imglnk = AppConfig.BASE_URL + holder.imglinkurl.getText().toString();






    }



    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_categorylist, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView category, seccategory, catalogid, catalogname, imglinkurl;
        public ImageView categoryImage, seccategoryImage;
        private List<Catalog> catalogList;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);

            category = (TextView) view.findViewById(R.id.categoryName);
            imglinkurl = (TextView) view.findViewById(R.id.imgLink);
            categoryImage = (ImageView) view.findViewById(R.id.categoryImage);
            catalogname = (TextView) view.findViewById(R.id.catalogname);
            catalogid = (TextView) view.findViewById(R.id.catalogId);


        }


    }

}