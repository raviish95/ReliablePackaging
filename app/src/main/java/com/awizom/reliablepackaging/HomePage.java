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
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
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
import android.widget.Toast;

import com.awizom.reliablepackaging.Adapter.GridImageAdapter;
import com.awizom.reliablepackaging.Adapter.OrderListAdapter;
import com.awizom.reliablepackaging.Helper.OrderHelper;
import com.awizom.reliablepackaging.Helper.ProfileHelper;
import com.awizom.reliablepackaging.login.LoginActivity;
import com.awizom.reliablepackaging.login.MainActivity;
import com.awizom.reliablepackaging.Model.MyProfileView;
import com.awizom.reliablepackaging.Model.Order;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

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
    TextView rebook, neworder, closeBtn;
    TextView textCartItemCount;
    private ImageView notification;
    int mCartItemCount = 10;
    TextView username;
    GridView gridView;
    String clientid = "", userName = "", userId = "";
    SwipeRefreshLayout mSwipeRefreshLayout;
    private AlertDialog progressDialog;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.


    /* For OnBackPRess in HomePage */
    @SuppressLint("ResourceType")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            final AlertDialog.Builder alertbox = new AlertDialog.Builder(HomePage.this);//R.style.AlertDialogTheme (Sakshee change alert)
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

//            alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
//            alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));

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
        toolbar.setBackgroundColor(Color.parseColor("#F9CD50"));
        toolbar.setLogo(R.drawable.action_logo);
        toolbar.setSubtitleTextAppearance(getApplicationContext(), R.style.styleA);
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.styleA);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new GridImageAdapter(this));
        TextView autoslideOffer = findViewById(R.id.auto_sldier);
        autoslideOffer.setSelected(true);
        progressDialog = new SpotsDialog(this, R.style.Custom);
        clientid = String.valueOf(SharedPrefManager.getInstance(this).getUser().getClientID());
        userName = SharedPrefManager.getInstance(this).getUser().getUserName().toString();
        userId = SharedPrefManager.getInstance(this).getUser().getUserID();
        addorder = findViewById(R.id.addOrder);
        addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrderget();
            }
        });
        no_internet = findViewById(R.id.no_internet);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
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
        View headerview = navigationView.getHeaderView(0);
        username = headerview.findViewById(R.id.profileName);
        getMyProfile();
        getNotiCount();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    initview();
                } catch (Exception e) {
                    e.printStackTrace();
                    // relativeLayout.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void getNotiCount() {
        String clientId = String.valueOf(SharedPrefManager.getInstance(this).getUser().getClientID());
        try {
            String result = new ProfileHelper.GetNotiCount().execute(clientId.toString()).get();
            // Toast.makeText(getApplicationContext(),result.toString(),Toast.LENGTH_LONG).show();
            mCartItemCount = Integer.parseInt(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getMyProfile() {
        //  mSwipeRefreshLayout.setRefreshing(true);
        try {
            String result = new ProfileHelper.GETMyProfile().execute(clientid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<MyProfileView>() {
            }.getType();
            MyProfileView myProfileView = new Gson().fromJson(result, listType);
            String nameview = myProfileView.getName().toString();
            String Email = myProfileView.getEmail().toString();
            String PhoneNumber = String.valueOf(myProfileView.getPhoneNumber().toString());
            String pincode = String.valueOf(myProfileView.getPinCode());
            String billingaddredss = String.valueOf(myProfileView.getBillingAdddress().toString());
            username.setText(nameview.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addOrderget() {

        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(HomePage.this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.order_type, null);
        rebook = dialogView.findViewById(R.id.rebOok);
        neworder = dialogView.findViewById(R.id.neworder);
        closeBtn = dialogView.findViewById(R.id.closeBtn);
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

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
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
        try {
           // Toast.makeText(getApplicationContext(), "deviceid->" + FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_LONG).show();
            String result = new OrderHelper.GETMyOrder().execute(clientid.toString()).get();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            orderlist = new Gson().fromJson(result, listType);
            adapterOrderList = new OrderListAdapter(HomePage.this, orderlist);
            recyclerView.setAdapter(adapterOrderList);
            mSwipeRefreshLayout.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        MenuItem menuItem = menu.findItem(R.id.admin1);
        MenuItem logouts = menu.findItem(R.id.action_logout);
        logouts.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                progressDialog.show();
                try {
                    SharedPrefManager.getInstance(HomePage.this).logout();
                    Intent intent = new Intent(HomePage.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    dismissmethod();

                } catch (Exception e) {
                    e.printStackTrace();
                    dismissmethod();
                }
                return true;
            }
        });
        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        notification = (ImageView) actionView.findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Intent intent = new Intent(HomePage.this, NotificationActivity.class);
                startActivity(intent);
                dismissmethod();
            }
        });
        setupBadge();
        return true;
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
                progressDialog.show();
                try {

                    SharedPrefManager.getInstance(this).logout();
                    Intent intent = new Intent(this, LoginActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    dismissmethod();

                } catch (Exception e) {
                    e.printStackTrace();
                    dismissmethod();
                }


                //do somthing
                break;
            }

            case R.id.nav_password: {
                progressDialog.show();
                Intent intent = new Intent(this, ChangePassword.class);
                intent.putExtra("UserName", userName.toString());
                intent.putExtra("UserID", userId.toString());
                startActivity(intent);
                dismissmethod();
                // Toast.makeText(getApplicationContext(), "CHange Password", Toast.LENGTH_LONG).show();
                //do somthing
                break;
            }
            case R.id.nav_profile: {
                progressDialog.show();
                Intent intent = new Intent(this, MyProfile.class);
                startActivity(intent);
                dismissmethod();
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
            case R.id.nav_feedback: {
                Toast.makeText(getApplicationContext(), "Feedback", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(this,FeedbackActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.nav_refer: {
                /*  Toast.makeText(getApplicationContext(), "ReferApp", Toast.LENGTH_LONG).show();*/

                break;
            }
            case R.id.my_acc: {
                progressDialog.show();
                Intent intent = new Intent(this, MyAccount.class);
                startActivity(intent);
                dismissmethod();
                /*   Toast.makeText(getApplicationContext(), "My Account", Toast.LENGTH_LONG).show();*/
                break;
            }
            case R.id.my_order: {
                progressDialog.show();
                Intent intent = new Intent(this, MyOrderList.class);
                intent.putExtra("HeaderName","My Order");
                startActivity(intent);
                dismissmethod();
                /*   Toast.makeText(getApplicationContext(), "My Account", Toast.LENGTH_LONG).show();*/
                break;
            }

            case R.id.my_jobs: {
                progressDialog.show();
                Intent intent = new Intent(this, MyOrderList.class);
                intent.putExtra("HeaderName","My Job");
                startActivity(intent);
                dismissmethod();
                /*   Toast.makeText(getApplicationContext(), "My Account", Toast.LENGTH_LONG).show();*/
                break;
            }
        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}