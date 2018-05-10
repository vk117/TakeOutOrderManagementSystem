package com.example.varun.snapsauce;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.varun.snapsauce.datababse.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static java.security.AccessController.getContext;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class OrderFulfillmentService extends IntentService {


    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private String[] arr;
    private String[] order_by;
    private String[] prep_time;
    private ArrayList<String[]> lines = new ArrayList<String[]>();

    private int time=0;
    private String status = "complete";
    public static volatile boolean shouldContinue = true;
    private String jsonbody;
    private boolean exec = true;
    private String URL = "http://ec2-54-193-101-253.us-west-1.compute.amazonaws.com:8080/";
    private JSONArray jsonArray;

    public OrderFulfillmentService() {
        super("OrderFulfillmentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        System.out.println("Service started");

        Bundle extras = intent.getExtras();
        final String user = extras.getString("user");



        RequestQueue queue = Volley.newRequestQueue(OrderFulfillmentService.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL.concat("getorders/"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work");
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {

                    try {
                        responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        System.out.println(responseString);
                    }catch (UnsupportedEncodingException e){
                        e.printStackTrace();
                    }

                    try {
                        jsonArray = new JSONArray(responseString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    arr = new String[jsonArray.length()];
                    order_by = new String[jsonArray.length()];
                    prep_time = new String[jsonArray.length()];

                    for(int i=0; i<jsonArray.length(); i++){
                        try{
                            if(jsonArray.getJSONObject(i).getString("status").equals("Processing")){
                                arr[i] = jsonArray.getJSONObject(i).getString("orderBy");
                                arr[i] = arr[i] + "\n" + jsonArray.getJSONObject(i).getString("prepTime");
                                lines.add(arr[i].split("\n"));
                                //System.out.println(arr[i]);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    for (int i = 0; i < lines.size(); i++) {
                        System.out.println("Loop started");
                        exec = true;
                        time = Integer.parseInt(lines.get(i)[1]) * 60;
                        try {
                            for (int a = time; a >= 0; a--) {
                                Thread.sleep(1000);
                                System.out.println(a);

                                if (a == 0) {



                                    try{
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("user", lines.get(i)[0]);
                                        jsonbody = jsonObject.toString();
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }

                                    Api api2 = new Api();
                                    api2.post(OrderFulfillmentService.this, jsonbody, "updatestatus", new VolleyCallback() {
                                        @Override
                                        public void onSuccess(String result) {

                                        }

                                        @Override
                                        public void onSuccessJSON(JSONArray arr) {

                                        }
                                    });


                                    new Thread(new Runnable() {

                                        public void run() {

                                            try {

                                                GMailSender sender = new GMailSender(

                                                        "snapsauce17@gmail.com",

                                                        "Sonal@1717");

                                                sender.sendMail("Thank you ", "Thank you for ordering with Snapsauce! \nEnjoy your meal!",

                                                        "snapsauce17@gmail.com",

                                                        user.toString());

                                            } catch (Exception e) {

                                            }

                                        }

                                    }).start();


                                } else if (a < 600 && exec) {
                                    exec = false;
                                    new Thread(new Runnable() {

                                        public void run() {

                                            try {

                                                GMailSender sender = new GMailSender(

                                                        "snapsauce17@gmail.com",

                                                        "Sonal@1717");

                                                sender.sendMail("Hope you are hungry ", "Hi! \nYour order is almost ready",

                                                        "snapsauce17@gmail.com",

                                                        user.toString());

                                            } catch (Exception e) {


                                            }

                                        }

                                    }).start();
                                }
                            }

                        } catch (InterruptedException e) {
                            System.out.println("Interrupted !");
                        }
                    }
                }

                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        queue.add(stringRequest);

    }


}
