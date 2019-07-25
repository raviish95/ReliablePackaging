package com.awizom.reliablepackaging;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.Adapter.RebookListChooseAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dmax.dialog.SpotsDialog;

public class RebookFillOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<Order> orderlist;
    RebookListChooseAdapter customAdapter;
    ArrayList<String> itemnamelist = new ArrayList<String>();
    private ArrayList<String> valueOfproductname = new ArrayList<String>();
    private ArrayList<String> valueOfproductid = new ArrayList<String>();
    private ArrayList<String> valueOfweight = new ArrayList<String>();
    private ArrayList<String> valueofLayerType = new ArrayList<String>();
    private ArrayList<String> valueofPackType = new ArrayList<String>();
    private ArrayList<String> valueofNotiType = new ArrayList<String>();
    private ArrayList<String> valueremarksdesign = new ArrayList<String>();
    private ArrayList<String> valuedesignbase = new ArrayList<String>();
    private ArrayList<String> valueofclientid = new ArrayList<String>();
    private ArrayList<String> valueofdesignname = new ArrayList<String>();
    Button order;
    RelativeLayout rl;
    private AlertDialog progressDialog;
    private static final int PICK_FROM_GALLERY = 1;
    ImageView imgvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebook_fill_order);
        initview();
    }

    private void initview() {

        itemnamelist = (ArrayList<String>) getIntent().getSerializableExtra("itemslist");
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getContext();
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Fill order details");
        toolbar.setBackgroundColor(Color.parseColor("#094B6C"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        rl = findViewById(R.id.rl);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        order = findViewById(R.id.orders);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getOrderforRebook();

    }

    private void getOrderforRebook() {

        String Length = String.valueOf(itemnamelist.toArray().length);
        try {
            // Toast.makeText(getApplicationContext(), "deviceid->" + FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_LONG).show();
            String result = new OrderHelper.GETMyChooseRebookOrder().execute(itemnamelist.toString(), Length).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            orderlist = new Gson().fromJson(result, listType);
            customAdapter = new RebookListChooseAdapter(RebookFillOrderActivity.this, orderlist, order, recyclerView);
            recyclerView.setAdapter(customAdapter);

            recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                    View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    final Button imagechange = (Button) childView.findViewById(R.id.uploadImg);
                    imgvw = childView.findViewById(R.id.selectdesgin);
                    imagechange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                if (ActivityCompat.checkSelfPermission(RebookFillOrderActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions((RebookFillOrderActivity.this), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                                } else {
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    return false;
                }

                @Override
                public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean b) {

                }
            });
         /*   recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final Button imagechange = (Button) v.findViewById(R.id.uploadImg);
                    imgvw = v.findViewById(R.id.selectdesgin);
                    imagechange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                if (ActivityCompat.checkSelfPermission(RebookFillOrderActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions((RebookFillOrderActivity.this), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                                } else {
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    return false;
                }
            });*/


            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < orderlist.size(); i++) {
                        View view = recyclerView.getChildAt(i);

                        /*      all orders weight in kg*/

                        EditText weightkg = (EditText) view.findViewById(R.id.weight_kg);
                        String wkg = weightkg.getText().toString();
                        if (wkg.isEmpty()) {
                            weightkg.setError("Empty");
                            weightkg.requestFocus();
                            valueOfweight.removeAll(valueOfweight);
                            return;
                        } else {
                            valueOfweight.add(wkg.toString());
                        }
                        /*      all orders Name */
                        TextView productname = (TextView) view.findViewById(R.id.productname);
                        String Pname = productname.getText().toString();
                        if (Pname.isEmpty()) {
                            productname.setError("Empty");
                            productname.requestFocus();

                        } else {
                            valueOfproductname.add(Pname.toString());
                        }


                        /*      all orders OrderID */

                        TextView orderid = view.findViewById(R.id.orderId);
                        String Oid = orderid.getText().toString();
                        if (Oid.isEmpty()) {
                            orderid.setError("Empty");
                            orderid.requestFocus();
                        } else {
                            valueOfproductid.add(Oid.toString());
                        }

                        /*      all orders layer type by SPinner */

                        Spinner spinner = view.findViewById(R.id.layertype);
                        String spinnervl = "1";
                        if (spinner.getSelectedItemPosition() == 0) {
                            spinnervl = "1".toString();
                        }
                        {
                            spinnervl = "2".toString();
                        }
                        valueofLayerType.add(spinnervl.toString());

                        /*      all orders pack type by SPinner */
                        Spinner spinnerpack = view.findViewById(R.id.packType);
                        String spinnerpackvl = "1";
                        if (spinner.getSelectedItemPosition() == 0) {
                            spinnerpackvl = "Pouching".toString();
                        }
                        {
                            spinnerpackvl = "Roll".toString();
                        }
                        valueofPackType.add(spinnerpackvl.toString());

                        EditText remarksdesign = (EditText) view.findViewById(R.id.remarks);
                        String rmrks = remarksdesign.getText().toString();
                        if (rmrks.isEmpty()) {

                            valueremarksdesign.add("nothing");

                        } else {
                            valueremarksdesign.add(rmrks.toString());
                        }



                        /*      all orders Notification want or not */
                        Switch switchnoti = view.findViewById(R.id.notify);
                        String cheknotify;
                        if (switchnoti.isChecked()) {
                            cheknotify = "Yes".toString();
                        } else {
                            cheknotify = "No".toString();
                        }
                        valueofNotiType.add(cheknotify.toString());

                        ImageView checngeimages = view.findViewById(R.id.selectdesgin);

                        CheckBox changecheck = view.findViewById(R.id.changecheck);
                        if (changecheck.isChecked()) {
                            checngeimages.buildDrawingCache();
                            Bitmap bitmap = checngeimages.getDrawingCache();
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                            byte[] image = stream.toByteArray();
                            //System.out.println("byte array:" + image);
                            String img_str = Base64.encodeToString(image, 0);
                            valuedesignbase.add(img_str);
                            String clientid = String.valueOf(SharedPrefManager.getInstance(RebookFillOrderActivity.this).getUser().getClientID());
                            valueofclientid.add(clientid.toString());
                            Random rand = new Random();
                            // Generate random integers in range 0 to 999
                            String rand_int1 = String.valueOf(rand.nextInt(1000));

                            valueofdesignname.add(rand_int1);

                        } else {

                        }

                    }

                    try {

                        progressDialog.show();
                        String length = String.valueOf(valueOfproductname.size());
                        int lengthdesign=valuedesignbase.size();
                        for(int i=0;i<lengthdesign;i++)
                        {
                            String designurl=valuedesignbase.get(i);
                            String clientidstring=String.valueOf(SharedPrefManager.getInstance(RebookFillOrderActivity.this).getUser().getClientID());
                            String designanme=valueofdesignname.get(i)+".jpg";
                            String designimage = new OrderHelper.PostDesignImage().execute(designurl.toString(), clientidstring.toString(), designanme.toString()).get();

                        }
                        String results = new OrderHelper.POSTRebookPreOrder().execute(valueOfproductname.toString().toString(), valueOfproductid.toString(), valueOfweight.toString(), valueofLayerType.toString(), valueofPackType.toString(), length.toString(), valueofNotiType.toString(), valueremarksdesign.toString()).get();

                        if (results.isEmpty()) {
                            Toast.makeText(RebookFillOrderActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
                            results = new OrderHelper.POSTRebookPreOrder().execute(valueOfproductname.toString().toString(), valueOfproductid.toString(), valueOfweight.toString(), valueofLayerType.toString(), valueofPackType.toString(), length.toString(), valueofNotiType.toString(), valueremarksdesign.toString()).get();

                            dismissmethod();
                        } else {
                            Toast.makeText(RebookFillOrderActivity.this, "Successfully Re-Booked Order", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RebookFillOrderActivity.this, HomePage.class);
                            startActivity(intent);
                            dismissmethod();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imgvw.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            //   super.onActivityResult(requestCode, resultCode, data);

        }


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


}
