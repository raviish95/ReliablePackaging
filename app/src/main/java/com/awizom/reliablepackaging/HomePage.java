package com.awizom.reliablepackaging;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.awizom.reliablepackaging.Helper.AdminHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    de.hdodenhof.circleimageview.CircleImageView profileImages;
    TextView userName, identityNo, identityType;

    RecyclerView recyclerView;
    List<Catalog> categorylist;
    CategoryListAdapter adapterCategoryList;
    LinearLayout linearLayout;
    boolean connected = false;
ImageView no_internet;
    Snackbar snackbar;

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
        toolbar.setTitle("Home Page");
        setSupportActionBar(toolbar);
        no_internet=findViewById(R.id.no_internet);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.scrollToPosition(0);
        recyclerView.smoothScrollToPosition(0);
        linearLayout=findViewById(R.id.linearlayout);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        profileImages = headerview.findViewById(R.id.profileImage);
        userName = headerview.findViewById(R.id.profileName);
//        SharedPrefManager.getInstance(this).getUser().;
        //  userName.setText(SharedPrefManager.getInstance(this).getUser().getName());
        userName.setOnClickListener(this);

    }

    private void checkInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            no_internet.setVisibility(View.GONE);
            connected = true;
            getcategorylist();
            //    Toast.makeText(getApplicationContext(), "Internet is On", Toast.LENGTH_SHORT).show();
        } else {
            connected = false;
            no_internet.setVisibility(View.VISIBLE);
            snackbar.show();

        }
    }
    private void getcategorylist() {

        String catalogName = "Home Cleaning & Repairs";
        try {

            String result = new AdminHelper.GETCategoryList().execute(catalogName.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Catalog>>() {
            }.getType();
            categorylist = new Gson().fromJson(result, listType);
            adapterCategoryList = new CategoryListAdapter(HomePage.this, categorylist);

            recyclerView.setAdapter(adapterCategoryList);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //side navigation drwaer started onCLick

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_employee) {
            // Handle the camera action

        } else if (id == R.id.nav_master) {

        } else if (id == R.id.nav_orderPhoto) {


        }

        /* else if (id == R.id.nav_catalogName) {
            intent = new Intent(AdminHomePage.this, AdminCatalogActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_catalogpricing) {
            intent = new Intent(AdminHomePage.this, AdminPricingActivity.class);
            startActivity(intent);
        }*/
        else if (id == R.id.nav_complaintReply) {

        } else if (id == R.id.nav_discount) {

        } else if (id == R.id.nav_logout) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}