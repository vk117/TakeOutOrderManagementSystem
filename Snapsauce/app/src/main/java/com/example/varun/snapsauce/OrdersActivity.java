package com.example.varun.snapsauce;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.varun.snapsauce.datababse.*;

public class OrdersActivity extends AppCompatActivity {

    private ListView orders;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private CustomAdapter3 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("user");

        orders = (ListView) findViewById(R.id.orders);
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

        orders.setAdapter(adapter);


    }
}
