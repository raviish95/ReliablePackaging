package com.awizom.reliablepackaging;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.awizom.reliablepackaging.Adapter.OrderListAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    List<Order> orderlist;
    OrderListAdapter adapterOrderList;
    LinearLayout linearLayout;
    boolean connected = false;
    ImageView no_internet;
    Snackbar snackbar;
    DrawerLayout drawer;
    FloatingActionButton addorder;
    TextView rebook, neworder;
    TextView textCartItemCount;
    int mCartItemCount = 10;

    /* For OnBackPRess in HomePage */
    @SuppressLint("ResourceType")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(HomePage.this);
            alertbox.setIcon(R.drawable.ic_search_black_24dp);
            alertbox.setIconAttribute(90);
            alertbox.setTitle("Do You Want To Exit ?");
            alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    // finish used for destroyed activity
                    finishAffinity();
                    System.exit(0);
                }
            });

            alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    // Nothing will be happened when clicked on no button
                    // of Dialog
                }
            });
            alertbox.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    //layout declaration
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initview();
    }

    private void initview() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#87CEFA"));
        toolbar.setTitle("Home Page");
        setSupportActionBar(toolbar);
        addorder = findViewById(R.id.addOrder);
        addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrderget();
            }
        });
        no_internet = findViewById(R.id.no_internet);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.scrollToPosition(0);
        recyclerView.smoothScrollToPosition(0);
        linearLayout = findViewById(R.id.linearlayout);
        snackbar = Snackbar.make(linearLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initview();
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        checkInternet();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void addOrderget() {

        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(HomePage.this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.order_type, null);
        rebook = dialogView.findViewById(R.id.rebOok);
        neworder =dialogView.findViewById(R.id.neworder);
        rebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, RebookOrderActivity.class);
                startActivity(intent);
            }
        });
        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, AddOrderActivity.class);
                startActivity(intent);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogView.setBackgroundColor(Color.parseColor("#F0F8FF"));
        final android.support.v7.app.AlertDialog b = dialogBuilder.create();
        b.show();

    }

    private void checkInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            no_internet.setVisibility(View.GONE);
            connected = true;
            getMyOrderList();
            //    Toast.makeText(getApplicationContext(), "Internet is On", Toast.LENGTH_SHORT).show();
        } else {
            connected = false;
            no_internet.setVisibility(View.VISIBLE);
            snackbar.show();

        }
    }

    private void getMyOrderList() {

        String clientid = "3";
        try {

            String result = new OrderHelper.GETMyOrder().execute(clientid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            orderlist = new Gson().fromJson(result, listType);
            adapterOrderList = new OrderListAdapter(HomePage.this, orderlist);

            recyclerView.setAdapter(adapterOrderList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        MenuItem menuItem = menu.findItem(R.id.admin1);


        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();
        return true;
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        switch (menuItem.getItemId()) {

            case R.id.nav_logout: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //do somthing
                break;
            }

            case R.id.nav_password: {
                Intent intent=new Intent(this,ChangePassword.class);
                startActivity(intent);
               // Toast.makeText(getApplicationContext(), "CHange Password", Toast.LENGTH_LONG).show();
                //do somthing
                break;
            }
            case R.id.nav_profile: {
                Intent intent=new Intent(this,MyProfile.class);
                startActivity(intent);
              /*  Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_LONG).show();*/
                break;
            }
            case R.id.nav_send: {

                String phoneNumber = "", message = "hi reliable packaging";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                intent.putExtra("sms_body", message);
                startActivity(intent);
               /*Toast.makeText(getApplicationContext(), "Send", Toast.LENGTH_LONG).show();*/
                break;
            }
            case R.id.nav_refer: {
              /*  Toast.makeText(getApplicationContext(), "ReferApp", Toast.LENGTH_LONG).show();*/

                break;
            }
            case R.id.my_acc: {
                Intent intent=new Intent(this,MyAccount.class);
                startActivity(intent);
             /*   Toast.makeText(getApplicationContext(), "My Account", Toast.LENGTH_LONG).show();*/
                break;
            }

        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}