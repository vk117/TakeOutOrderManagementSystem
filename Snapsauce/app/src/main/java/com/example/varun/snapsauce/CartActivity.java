package com.example.varun.snapsauce;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.varun.snapsauce.datababse.*;

public class CartActivity extends AppCompatActivity {

    private ListView orders;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private TextView total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        orders = (ListView) findViewById(R.id.orders);
        total = (TextView) findViewById(R.id.textView7);

        Bundle extras = getIntent().getExtras();
        String user = extras.getString("user");

        dbHelper = new DBHelper(CartActivity.this);
        database = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DBSchema.TABLE3_NAME + " " + "WHERE " + DBSchema.ORDERED_BY + "=" + "'" + user + "'";
        Cursor cursor = database.rawQuery(query, null);

        String[] arr = new String[cursor.getCount()];
        int price = 0;
        cursor.moveToFirst();

        int i = 0;

        for(i = 0; i<cursor.getCount(); i++){
            arr[i] = cursor.getString(cursor.getColumnIndex(DBSchema.ITEM_NAME)) + "\n" + "$" + cursor.getString(cursor.getColumnIndex(DBSchema.UNIT_PRICE));
            price = price + Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBSchema.UNIT_PRICE)));
        }

        ArrayAdapter<String> foodAdapter =
                new ArrayAdapter<String>(this, R.layout.food_item, R.id.food_name, arr);

        orders.setAdapter(foodAdapter);

        total.setText("$" + Integer.toString(price));
    }
}
