package com.awizom.reliablepackaging;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.awizom.reliablepackaging.Config.AppConfig;
import com.awizom.reliablepackaging.Helper.ProfileHelper;
import com.awizom.reliablepackaging.Model.LegalDocumentModel;
import com.awizom.reliablepackaging.Model.MyProfileView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import uk.co.senab.photoview.PhotoViewAttacher;

public class LegalDocument extends AppCompatActivity {

    private ImageView legal_image;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    Drawable drawable;
    Bitmap bitmap;
    String ImagePath;
    Uri URI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_document);
        initview();
    }

    private void initview() {
        legal_image = findViewById(R.id.legaldoc);

        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        legal_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mScaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Legal Document");
        toolbar.setBackgroundColor(Color.parseColor("#BF720F"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getLegalDoc();

    }

    private void getLegalDoc() {

        String clientid = String.valueOf(SharedPrefManager.getInstance(this).getUser().getClientID());
        try {

            String result = new ProfileHelper.GETMyLegalDoc().execute(clientid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<LegalDocumentModel>() {
            }.getType();
            LegalDocumentModel legalDocumentModel = new Gson().fromJson(result, listType);
            String imagestring = legalDocumentModel.getURL().toString();
            try {
                Glide.with(this).load(AppConfig.BASE_URL + imagestring.toString()).asBitmap().into(legal_image);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f,
                    Math.min(mScaleFactor, 10.0f));
            legal_image.setScaleX(mScaleFactor);
            legal_image.setScaleY(mScaleFactor);
            return true;
        }
    }
}
