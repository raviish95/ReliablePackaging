package com.awizom.reliablepackaging;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.Adapter.OrderDetailsAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Helper.ProfileHelper;
import com.awizom.reliablepackaging.Model.LoginModel;
import com.awizom.reliablepackaging.Model.MyProfileView;
import com.awizom.reliablepackaging.Model.Order;
import com.awizom.reliablepackaging.Model.OrderCount;
import com.awizom.reliablepackaging.Model.OrderDetailsView;
import com.awizom.reliablepackaging.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* create by ravi on 22-may-2019*/
public class MyProfile extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView name,firm, email, mobno, place, panno, gstno, tinno, nameimage, totalordervalue, totalrunningorder, totalcompltdorder,addmobno;
    private Button account, update_profile,legal_doc;
    private LinearLayout totalodr, runningodr, cmpltdodr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        initview();
    }

    private void initview() {

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Profile");
        toolbar.setBackgroundColor(Color.parseColor("#28B463"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        linearLayout = findViewById(R.id.linearlayout);
        totalodr = findViewById(R.id.totalOrder);
        runningodr = findViewById(R.id.runningOrder);
        cmpltdodr = findViewById(R.id.completedOrder);
        firm=findViewById(R.id.firm_name);
        totalordervalue = findViewById(R.id.totalordervalue);
        totalrunningorder = findViewById(R.id.runningordervalue);
        totalcompltdorder = findViewById(R.id.completedordervalue);
        totalodr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyOrderList.class);
                intent.putExtra("Ordertype", "TotalOdr");
                intent.putExtra("HeaderName", "Total Order");
                startActivity(intent);
            }
        });
        cmpltdodr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyOrderList.class);
                intent.putExtra("Ordertype", "CompletedOdr");
                intent.putExtra("HeaderName", "Completed Order");
                startActivity(intent);
            }
        });
        runningodr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyOrderList.class);
                intent.putExtra("Ordertype", "RunningOdr");
                intent.putExtra("HeaderName", "Running Order");
                startActivity(intent);
            }
        });
        linearLayout.setOnTouchListener(new OnSwipeTouchListener(MyProfile.this) {
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
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobno = findViewById(R.id.mobno);
        panno = findViewById(R.id.panno);
        gstno = findViewById(R.id.gstno);
        tinno = findViewById(R.id.tinno);
        place = findViewById(R.id.place);
        addmobno=findViewById(R.id.addmobno);
        account = findViewById(R.id.accounts);
        update_profile = findViewById(R.id.update);
        legal_doc=findViewById(R.id.legal);
        nameimage = findViewById(R.id.img);

        legal_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, LegalDocument.class);
                startActivity(intent);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyAccount.class);
                startActivity(intent);
            }
        });
        getmyProfile();
        getMyOrderCount();
    }

    private void showupdatedialog(String namestring, String emailstring, String mobilenostring, String placeString, String pannostring, String gstnostring, String tinnostring,String addmobno) {
        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(MyProfile.this);
        final EditText names, emails, mobnos, places, nameimages, totalordervalues, totalrunningorders, totalcompltdorders, pannos, gstnos, tinnos,addphonenos;
        Button update_button;
        LayoutInflater inflater = LayoutInflater.from(MyProfile.this);
        final View dialogView = inflater.inflate(R.layout.updateprofiledialog, null);
        dialogBuilder.setTitle("Update Profile");

        addphonenos=dialogView.findViewById(R.id.addmobno);
        addphonenos.setText(addmobno.toString());
        names = dialogView.findViewById(R.id.name);
        names.setText(namestring.toString());
        emails = dialogView.findViewById(R.id.email);
        emails.setText(emailstring.toString());
        mobnos = dialogView.findViewById(R.id.mobno);
        mobnos.setText(mobilenostring.toString());
        places = dialogView.findViewById(R.id.place);
        places.setText(placeString.toString());
        pannos = dialogView.findViewById(R.id.panno);
        pannos.setText(pannostring.toString());
        gstnos = dialogView.findViewById(R.id.gstno);
        gstnos.setText(gstnostring.toString());
        tinnos = dialogView.findViewById(R.id.tinno);
        tinnos.setText(tinnostring.toString());
        update_button = dialogView.findViewById(R.id.update);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*  matcher for panno*/
                String Pan = pannos.getText().toString().trim();
                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                Matcher matcher = pattern.matcher(Pan);

                /*     matcher for gstno*/
                String gstis = gstnos.getText().toString().trim();
                Pattern gstpattern = Pattern.compile("([0-9]{2}[a-zA-Z]{4}([a-zA-Z]{1}|[0-9]{1})[0-9]{4}[a-zA-Z]{1}([a-zA-Z]|[0-9]){3}){0,15}");
                Matcher gstmatcher = gstpattern.matcher(gstis);

                /*     matcher for tinno*/
                String tinnois = tinnos.getText().toString().trim();
                Pattern tinnopattern = Pattern.compile("(\\d{3}-\\d{2}-\\d{4}|\\d{2}-\\d{7})");
                Matcher tinmatcher = tinnopattern.matcher(tinnois);


                if (!matcher.matches()) {
                    Toast.makeText(getApplicationContext(), Pan + " is Not Matching",
                            Toast.LENGTH_LONG).show();
                    pannos.setError("Pan No is not valid");
                    pannos.requestFocus();


                } else if (!gstmatcher.matches()) {
                    Toast.makeText(getApplicationContext(), gstis + " is Not Matching",
                            Toast.LENGTH_LONG).show();
                    gstnos.setError("GST No is not valid");
                    gstnos.requestFocus();


                } else {
                    String clientid = String.valueOf(SharedPrefManager.getInstance(MyProfile.this).getUser().getClientID());
                    try {
                        String result = new ProfileHelper.UpdateProfile().execute(clientid.toString(), names.getText().toString(), emails.getText().toString(), mobnos.getText().toString(), places.getText().toString(), pannos.getText().toString(), gstnos.getText().toString(), tinnos.getText().toString(),addphonenos.getText().toString()).get();

                        if (result.isEmpty()) {
                            Toast.makeText(MyProfile.this, "Invalid request", Toast.LENGTH_SHORT).show();
                            result = new ProfileHelper.UpdateProfile().execute(clientid.toString(), names.getText().toString(), emails.getText().toString(), mobnos.getText().toString(), places.getText().toString(), pannos.getText().toString(), gstnos.getText().toString(), tinnos.getText().toString()).get();
                        } else {

                            Toast.makeText(MyProfile.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MyProfile.this, HomePage.class);
                            startActivity(intent);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        dialogBuilder.setView(dialogView);
        final android.support.v7.app.AlertDialog b = dialogBuilder.create();
        b.show();


    }

    private void getMyOrderCount() {

        String clientid = String.valueOf(SharedPrefManager.getInstance(this).getUser().getClientID());
        try {

            String result = new ProfileHelper.GetOrderCount().execute(clientid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<OrderCount>() {
            }.getType();
            OrderCount orderCount = new Gson().fromJson(result, listType);
            String totalorder = String.valueOf(orderCount.getTotalstauscount());
            String runningorder = String.valueOf(orderCount.getTotalrunningstatus());
            String completedorder = String.valueOf(orderCount.getTotalcompletedcount());

            totalordervalue.setText(totalorder.toString());
            totalrunningorder.setText(runningorder.toString());
            totalcompltdorder.setText(completedorder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    private void getmyProfile() {
        String pannos="", gstnos="", tinnos="";
        String clientid = String.valueOf(SharedPrefManager.getInstance(this).getUser().getClientID());
        try {

            String result = new ProfileHelper.GETMyProfile().execute(clientid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<MyProfileView>() {
            }.getType();
            MyProfileView myProfileView = new Gson().fromJson(result, listType);
            String nameview = myProfileView.getName().toString();
            String Email = myProfileView.getEmail().toString();
            String PhoneNumber = String.valueOf(myProfileView.getPhoneNumber().toString());
            String AddPhoneNumber = String.valueOf(myProfileView.getAditionalPhoneNumber().toString());
            String pincode = String.valueOf(myProfileView.getPinCode());
            String billingaddredss = String.valueOf(myProfileView.getBillingAdddress().toString());
            String accountType=myProfileView.getAccountType().toString();
            if(accountType.equals("Self"))
            {
                firm.setText(Html.fromHtml("<u>"+accountType.toString()+"</u>"));
            }
            else
            {
                firm.setText(Html.fromHtml("<u>"+myProfileView.getCompanyName().toString())+"</u>");
            }
            try {
                pannos = String.valueOf(myProfileView.getPAN().toString());
                gstnos = String.valueOf(myProfileView.getGstin().toString());
                tinnos = String.valueOf(myProfileView.getTin().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            name.setText(nameview.toString());
            email.setText(Email.toString());
            mobno.setText(PhoneNumber.toString());
            place.setText(billingaddredss.toString());
            panno.setText(pannos.toString());
            gstno.setText(gstnos.toString());
            tinno.setText(tinnos.toString());
            addmobno.setText(AddPhoneNumber.toString());
            nameimage.setText(nameview.toString().split("")[1]);
            final String tinnostring = tinno.getText().toString();
            final String gstnostring = gstno.getText().toString();
            final String pannostring = panno.getText().toString();
            final String mobilenostring = mobno.getText().toString();
            final String emailstring = email.getText().toString();
            final String placestring = place.getText().toString();
            final String namestring = name.getText().toString();
            final String addmobnostring = addmobno.getText().toString();
            update_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showupdatedialog(namestring, emailstring, mobilenostring, placestring, pannostring, gstnostring, tinnostring,addmobnostring);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
