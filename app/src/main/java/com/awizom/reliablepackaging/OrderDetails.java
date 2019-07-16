package com.awizom.reliablepackaging;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.Adapter.OrderDetailsAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.awizom.reliablepackaging.Model.TrackingStatus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBarWrapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

 /*created by ravi on 18/05/2019*/
public class OrderDetails extends AppCompatActivity {

    com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar verticalSeekBar;
    private LinearLayout linearLayout;
    private RecyclerView recyclerViewOrder;
    String Orderid;
    OrderDetailsAdapter adapterOrderDetails;
    List<OrderDetailsView> orderdetails;
    TrackingStatus trackingStatuses;
    String imageLink;
    ScrollView scrollView;
    private AlertDialog progressDialog;
    RelativeLayout relativeLayout;
    TextView cylinderpre, printing, inspection, lamination, slitting, pouching, packaging, dispatch,expand;
    int trackinProgress = 0, secondaryProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Orderid = getIntent().getStringExtra("OrderId");
        imageLink = getIntent().getStringExtra("ImageLink");
        verticalSeekBar = findViewById(R.id.capacity_seek);
        initview();

    }

    private void initview() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Order Details");
        toolbar.setBackgroundColor(Color.parseColor("#212F3D"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        expand=findViewById(R.id.expand);
        cylinderpre = findViewById(R.id.cylinderprep);
        printing = findViewById(R.id.printing);
        inspection = findViewById(R.id.inspection);
        lamination = findViewById(R.id.lamination);
        slitting = findViewById(R.id.slitting);
        pouching = findViewById(R.id.pouching);
        packaging = findViewById(R.id.packaging);
        dispatch = findViewById(R.id.dispatch);
        relativeLayout = findViewById(R.id.footer);
        scrollView = findViewById(R.id.scrollView);
        expand.setText(Html.fromHtml("<u>"+"Expand Track..>"+"</u>"));
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderDetails.this,ExpandTrackActivity.class);
                intent.putExtra("OrderId",Orderid.toString());
                startActivity(intent);
            }
        });
     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                     if(scrollY<=oldScrollY)
                    {
                        //scroll up
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                    else {
                    relativeLayout.setVisibility(View.GONE);}
                }
            });
        }*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageLink.toString().equals("imagelink")) {
                    Intent intent = new Intent(OrderDetails.this, HomePage.class);
                    startActivity(intent);
                } else {
                    onBackPressed();
                }

            }
        });

        progressDialog = new SpotsDialog(OrderDetails.this, R.style.Custom);
        linearLayout = findViewById(R.id.linearlayout);
        linearLayout.setOnTouchListener(new OnSwipeTouchListener(OrderDetails.this) {
            public void onSwipeTop() {
                //   Toast.makeText(RebookOrderActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                onBackPressed();
                // Toast.makeText(RebookOrderActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {

                //  Toast.makeText(RebookOrderActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                //     Toast.makeText(RebookOrderActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
        recyclerViewOrder = findViewById(R.id.recyclerOrderdetails);
        recyclerViewOrder.setHasFixedSize(true);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));
        getOrderDetails();
        getTracking();
        if (Build.VERSION.SDK_INT >= 11) {
            ObjectAnimator animation = ObjectAnimator.ofInt(verticalSeekBar, "progress", trackinProgress);
            verticalSeekBar.setSecondaryProgress(secondaryProgress);
            animation.setDuration(1050); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
            verticalSeekBar.setEnabled(false);

        } else {

            verticalSeekBar.setProgress(3);
        }
    }

    private void getTracking() {
        try {
            String result = new OrderHelper.GETMyOrderTracking().execute(Orderid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<TrackingStatus>() {
            }.getType();
            trackingStatuses = new Gson().fromJson(result, listType);

            //dispatch color
            if (trackingStatuses.getDispatchStatus() == 3) {
                dispatch.setTextColor(getResources().getColor(R.color.green));
            } else if (trackingStatuses.getDispatchStatus() == 3) {
                dispatch.setTextColor(getResources().getColor(R.color.yellow));
                blink(dispatch);
            }
            //pouching color
            if (trackingStatuses.getPouchingStatus() == 3) {
                pouching.setTextColor(getResources().getColor(R.color.green));

            } else  if (trackingStatuses.getPouchingStatus() == 2){
                pouching.setTextColor(getResources().getColor(R.color.yellow));
                blink(pouching);
            }
            //slitting color
            if (trackingStatuses.getSlittingstatus() == 3) {
                slitting.setTextColor(getResources().getColor(R.color.green));

            } else if (trackingStatuses.getSlittingstatus() == 2){
                slitting.setTextColor(getResources().getColor(R.color.yellow));
                blink(slitting);
            }


            //lamination color
            if (trackingStatuses.getLaminationStatus() == 3) {
                lamination.setTextColor(getResources().getColor(R.color.green));
            } else if (trackingStatuses.getLaminationStatus() == 2){
                lamination.setTextColor(getResources().getColor(R.color.yellow));
                blink(lamination);
            }

            //inspection color

            if (trackingStatuses.getInspectionStatus() == 3) {
                inspection.setTextColor(getResources().getColor(R.color.green));
            } else if(trackingStatuses.getInspectionStatus() == 2) {
                inspection.setTextColor(getResources().getColor(R.color.yellow));
                blink(inspection);
            }


            //printi color
            if (trackingStatuses.getPrintstatus() == 3) {
                printing.setTextColor(getResources().getColor(R.color.green));
            } else  if (trackingStatuses.getPrintstatus() == 2){
                printing.setTextColor(getResources().getColor(R.color.yellow));
                blink(printing);
            }

            //cylinder color

            if (trackingStatuses.getCylinderStatus() == 3) {
                cylinderpre.setTextColor(getResources().getColor(R.color.green));
            } else if (trackingStatuses.getCylinderStatus() == 2){
                cylinderpre.setTextColor(getResources().getColor(R.color.yellow));
                blink(cylinderpre);
            }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            /*                           TRACK SEEKBAR                          */


           /* dispatch track*/
            if (!(trackingStatuses.getDispatchStatus() == 0)) {
                trackinProgress = 6;
                secondaryProgress = trackinProgress + 1;


            }
            /* pouching track*/
            else if (!(trackingStatuses.getPouchingStatus() == 0)) {
                trackinProgress = 4;
                secondaryProgress = trackinProgress + 1;

            }
            /* slitting track*/
            else if (!(trackingStatuses.getSlittingstatus() == 0)) {
                trackinProgress = 3;
                secondaryProgress = trackinProgress + 1;

            }
            /* lamination track*/
            else if (!(trackingStatuses.getLaminationStatus() == 0)) {
                trackinProgress = 2;
                secondaryProgress = trackinProgress + 1;

            }

            /* inspection track*/
            else if (!(trackingStatuses.getInspectionStatus() == 0)) {
                trackinProgress = 1;
                secondaryProgress = trackinProgress + 1;

            }

            /* print track*/
            else if (!(trackingStatuses.getPrintstatus() == 0)) {
                trackinProgress = 0;
                secondaryProgress = trackinProgress + 1;

            }

            /* cylinder track*/
            else if (!(trackingStatuses.getCylinderStatus() == 0)) {
                trackinProgress = 0;
                secondaryProgress = trackinProgress;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void blink(final TextView textView) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 400;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if(textView.getVisibility() == View.VISIBLE){
                            textView.setVisibility(View.INVISIBLE);
                        }else{
                            textView.setVisibility(View.VISIBLE);
                        }
                        blink(textView);
                    }
                });
            }
        }).start();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (imageLink.toString().equals("imagelink")) {
                Intent intent = new Intent(OrderDetails.this, HomePage.class);
                startActivity(intent);
            } else {
                onBackPressed();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getOrderDetails() {
        try {
            String result = new OrderHelper.GETMyOrderDetails().execute(Orderid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<OrderDetailsView>>() {
            }.getType();
            orderdetails = new Gson().fromJson(result, listType);
            adapterOrderDetails = new OrderDetailsAdapter(OrderDetails.this, orderdetails, imageLink);
            recyclerViewOrder.setAdapter(adapterOrderDetails);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}