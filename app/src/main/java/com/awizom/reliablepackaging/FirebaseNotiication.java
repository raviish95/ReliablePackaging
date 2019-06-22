package com.awizom.reliablepackaging;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.reliablepackaging.Helper.ProfileHelper;
import com.awizom.reliablepackaging.Model.ResultModel;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class FirebaseNotiication extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        postNotification();
        FirebaseMessaging.getInstance().subscribeToTopic("ServiceNow");
    }

    private void postNotification() {


        String result = null;
        try {
            result = new ProfileHelper.PostfireNotification().execute().get();
            Gson gson = new Gson();
            Type getType = new TypeToken<ResultModel>() {
            }.getType();
          //  ResultModel resultModel = new Gson().fromJson(result, getType);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }


    @Override
    public void onNewIntent(Intent intent) {

        setContentView(R.layout.firebasenotification);
        final TextView IncidentTextView =
                (TextView) findViewById(R.id.txtIncidentNo);
        final TextView SDescTextView =
                (TextView) findViewById(R.id.txtShortDesc);

        final TextView DescTextView =
                (TextView) findViewById(R.id.txtDesc);

        DescTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "token " + FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_LONG).show();

            }
        });


    }


}