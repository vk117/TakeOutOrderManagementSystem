package com.example.varun.snapsauce;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.varun.snapsauce.datababse.*;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private ListView orders;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private CustomAdapter3 adapter;

    private String[] arr;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        Bundle bundle = getIntent().getExtras();
         user = bundle.getString("user");

         orders = (ListView) findViewById(R.id.orders);

        /*orders = (ListView) findViewById(R.id.orders);
        dbHelper = new DBHelper(OrdersActivity.this);
        db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DBSchema.TABLE4_NAME;
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        String[] arr = new String[cursor.getCount()];
        for(int i=0; i<cursor.getCount(); i++){
            arr[i] = cursor.getString(cursor.getColumnIndex(DBSchema.ITEM_NAME2));
            arr[i] = arr[i] + "\n" + cursor.getString(cursor.getColumnIndex(DBSchema.STATUS));
            arr[i] = arr[i] + "\n" + user;

            cursor.moveToNext();
        }

        adapter = new CustomAdapter3(OrdersActivity.this, arr);

        orders.setAdapter(adapter);*/


        Api api = new Api();

        api.getJSON(OrdersActivity.this, "getorders/".concat(user), new VolleyCallback() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onSuccessJSON(JSONArray array) {

                arr = new String[array.length()];
                try{
                    for(int i=0; i<array.length(); i++){
                        arr[i] = array.getJSONObject(i).getString("name");
                        arr[i] = arr[i] + "\n" + array.getJSONObject(i).getString("status");
                        arr[i] = arr[i] + "\n" + user;
                        System.out.println(arr[i]);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

                adapter = new CustomAdapter3(OrdersActivity.this, arr);

                orders.setAdapter(adapter);
            }
        });


    }
}
