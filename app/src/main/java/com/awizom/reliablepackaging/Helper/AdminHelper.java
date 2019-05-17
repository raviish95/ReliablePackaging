package com.awizom.reliablepackaging.Helper;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.awizom.reliablepackaging.Config.AppConfig;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class AdminHelper extends AppCompatActivity {

    public static final class GETCategoryList extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String json = "";
            String catalogNameOne = params[0];

            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API_Admin + "GetCategoryName");
                builder.addHeader("Content-Type", "Application/json");
                builder.addHeader("Accept", "application/json");
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("CatalogName", catalogNameOne);
                builder.post(parameters.build());
                okhttp3.Response response = client.newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            return json;
        }

        protected void onPostExecute(String result) {

            try {
                if (result.isEmpty()) {

                } else {
                    super.onPostExecute(result);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


}
