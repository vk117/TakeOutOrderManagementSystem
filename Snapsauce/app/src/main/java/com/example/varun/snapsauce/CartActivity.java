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
    private String items = "\n";

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

        dbHelper = new DBHelper(CartActivity.this);
        database = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DBSchema.TABLE3_NAME + " " + "WHERE " + DBSchema.ORDERED_BY + "=" + "'" + user + "'";
        Cursor cursor = database.rawQuery(query, null);

        String[] arr = new String[cursor.getCount()];
        int price = 0;
        cursor.moveToFirst();

        int i = 0;

        for(i = 0; i<cursor.getCount(); i++){
            arr[i] = cursor.getString(cursor.getColumnIndex(DBSchema.ITEM_NAME)) + "\n" + "$" + cursor.getString(cursor.getColumnIndex(DBSchema.UNIT_PRICE)) +
            "\n" + cursor.getString(cursor.getColumnIndex(DBSchema.ORDERED_BY)) + "\n" + cursor.getString(cursor.getColumnIndex(DBSchema.QUANTITY));
            price = price + Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBSchema.UNIT_PRICE)));
            prep_time = prep_time + Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBSchema.PREP_TIME)));
            items = items + cursor.getString(cursor.getColumnIndex(DBSchema.ITEM_NAME));
            cursor.moveToNext();
        }

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

        /*ArrayAdapter<String> foodAdapter =
                new ArrayAdapter<String>(this, R.layout.food_item, R.id.food_name, arr);*/

        adapter = new CustomAdapter2(CartActivity.this, arr);

        orders.setAdapter(adapter);


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

        orders.setClickable(true);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DBHelper(CartActivity.this);
                database = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(DBSchema.ORDERED_BY, user);
                values.put(DBSchema.PREP_TIME2, prep_time);
                values.put(DBSchema.STATUS, "incomplete");
                values.put(DBSchema.ITEM_NAME2, items);

                long status = database.insert(DBSchema.TABLE4_NAME, null, values);
                if(status==-1){
                    Toast.makeText(CartActivity.this, "Order cannot be placed", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CartActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                }

                database.delete(DBSchema.TABLE3_NAME, DBSchema.ORDERED_BY + "=" + "'" + user + "'", null);
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
