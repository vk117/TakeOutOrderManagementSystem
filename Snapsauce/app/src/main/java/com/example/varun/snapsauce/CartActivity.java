package com.example.varun.snapsauce;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.varun.snapsauce.CustomAdapter2;

import com.example.varun.snapsauce.datababse.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CartActivity extends AppCompatActivity {

    private ListView orders;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private TextView total;
    private Button placeOrder;
    private TextView qty;

    private double frac = 0.0;
    private long hours = 0;
    private double fPart = 0.0;
    private double minutes = 0;
    private int prep_time = 0;
    private String items="";
    private String[] arr;
    private int price = 0;
    private String requestbody;

    private CustomAdapter2 adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        orders = (ListView) findViewById(R.id.orders);
        placeOrder = (Button) findViewById(R.id.place);
        qty = (TextView) findViewById(R.id.qty);

        Bundle extras = getIntent().getExtras();
        final String user = extras.getString("user");


        Api api = new Api();

        api.getJSON(CartActivity.this, "getcart/".concat(user), new VolleyCallback() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onSuccessJSON(JSONArray array) {

                arr = new String[array.length()];

                for(int i=0; i<array.length(); i++){
                    try {
                        arr[i] = array.getJSONObject(i).getString("name") + "\n" + "$" + array.getJSONObject(i).getString("unitPrice") +
                                "\n" + array.getJSONObject(i).getString("orderBy") + "\n" + array.getJSONObject(i).getString("quantity");

                        price = price + Integer.parseInt(array.getJSONObject(i).getString("unitPrice"));
                        prep_time = prep_time + Integer.parseInt(array.getJSONObject(i).getString("prepTime"));
                        items = items + array.getJSONObject(i).getString("name");

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }

                adapter = new CustomAdapter2(CartActivity.this, arr);

                orders.setAdapter(adapter);


                System.out.println(prep_time);

                if(prep_time>60) {
                    frac = prep_time / 60;
                    hours = (long) frac;
                    fPart = frac - hours;
                    minutes = fPart * 60;
                }

                else {
                    minutes = prep_time;
                }


                for(String order: arr){
                    System.out.println(order);
                }



                if(price!=0) {
                    placeOrder.setEnabled(true);
                    placeOrder.setAlpha(1.0f);
                    placeOrder.setText("Place Pickup Order" + " " + " " + " " + "$" + Integer.toString(price));
                }
                else{
                    placeOrder.setEnabled(false);
                    placeOrder.setAlpha(.5f);
                    placeOrder.setText("Place Pickup Order");
                }
            }
        });




        orders.setClickable(true);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Api api = new Api();

                try{
                    JSONObject jsonbody = new JSONObject();

                    jsonbody.put("orderBy", user);
                    jsonbody.put("prepTime", prep_time);
                    jsonbody.put("status", "Processing");
                    jsonbody.put("name", items);

                    requestbody = jsonbody.toString();
                }catch (JSONException e){
                    e.printStackTrace();
                }

                api.post(CartActivity.this, requestbody, "addorder", new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        if(result.equals("200")){
                            Toast.makeText(CartActivity.this, "Ordered", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(CartActivity.this, "Not Ordered", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onSuccessJSON(JSONArray arr) {

                    }
                });



                api.delete(CartActivity.this, "deletecart/".concat(user), new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        if(result.equals("200")){
                            System.out.println("Order placed");
                        }
                        else{
                            System.out.println("There was some error");
                        }
                    }

                    @Override
                    public void onSuccessJSON(JSONArray arr) {

                    }
                });

                recreate();


                Intent intent = new Intent(CartActivity.this, OrderFulfillmentService.class);
                intent.putExtra("user", user);
                startService(intent);
                new Thread(new Runnable() {

                    public void run() {

                        try {

                            GMailSender sender = new GMailSender(

                                    "snapsauce17@gmail.com",

                                    "Sonal@1717");

                            sender.sendMail("Order Accepted", "Hi! " + user + "\n Your order will be ready in " + hours + " hours " + minutes + " minutes " ,

                                    "snapsauce17@gmail.com",

                                    user.toString());

                        } catch (Exception e) {

                            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();

                        }

                    }

                }).start();
            }
        });

    }

    public void refresh(){
        recreate();
    }
}
