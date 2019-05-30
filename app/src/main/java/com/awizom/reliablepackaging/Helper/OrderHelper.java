package com.awizom.reliablepackaging.Helper;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.awizom.reliablepackaging.Config.AppConfig;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OrderHelper extends AppCompatActivity {

    public static final class GETMyOrder extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String json = "";
            String clientID = params[0];

            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API_ + "MyOrder/3");
                builder.addHeader("Content-Type", "Application/json");
                builder.addHeader("Accept", "application/json");

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

    public static final class GETMDesignList extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String json = "";
            String orderid = params[0];

            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API_ + "MyDesign/" + orderid);
                builder.addHeader("Content-Type", "Application/json");
                builder.addHeader("Accept", "application/json");

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

    public static final class GETMyOrderDetails extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String json = "";
            String orderId = params[0];

            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API_ + "OrderDetail/" + orderId);
                builder.addHeader("Content-Type", "Application/json");
                builder.addHeader("Accept", "application/json");

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

    public static final class POSTDesignApprove extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            //     InputStream inputStream
            String designid = params[0];
            String orderId = params[1];
            String isapproved = params[2];
            String remArks = params[3];

            String json = "";
            try {

                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(AppConfig.BASE_URL_API_ + "ApproveDesign");
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                builder.addHeader("Accept", "application/json");
                //builder.addHeader("Authorization", "Bearer " + accesstoken);
                FormBody.Builder parameters = new FormBody.Builder();
                parameters.add("DesignId", designid);
                parameters.add("OrderID", orderId);
                parameters.add("IsApproved", isapproved);
                parameters.add("Remarks", remArks);
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

            if (result.isEmpty()) {

            } else {
                super.onPostExecute(result);
//
            }


        }

    }
}