package com.example.varun.snapsauce;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.varun.snapsauce.datababse.*;

public class MenuActivity extends AppCompatActivity {

    public ListView menu;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menu = new ListView(this);
        menu = (ListView) findViewById(R.id.menu);

        /*String[] food = {
                "Butter Chicken",
                "Tandoori Chicken",
                "Rasmalai"
        };*/

        dbHelper = new DBHelper(MenuActivity.this);
        db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DBSchema.TABLE2_NAME;
        Cursor cursor = db.rawQuery(query, null);

        int i = 0;

        String[] arr = new String[cursor.getCount()];
        //String[] brr = new String[cursor.getCount()];
        cursor.moveToFirst();

        for(i=0; i<cursor.getCount(); i++){
            arr[i] = cursor.getString(cursor.getColumnIndex(DBSchema.NAME2));
            arr[i] = arr[i] + "\n" + "$" + cursor.getString(cursor.getColumnIndex(DBSchema.PRICE));
            arr[i] = arr[i] + "\n" + "Calories: " + cursor.getString(cursor.getColumnIndex(DBSchema.CALORIES));
            cursor.moveToNext();
        }

        ArrayAdapter<String> foodAdapter =
                new ArrayAdapter<String>(this, R.layout.food_item, R.id.food_name, arr);

                //ListView foodlist = new ListView(this);
                menu.setAdapter(foodAdapter);

        menu.setClickable(true);
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                String value = (String)adapter.getItemAtPosition(position);

                Intent myIntent = new Intent(MenuActivity.this, ItemActivity.class);

                myIntent.putExtra("item", value);
                startActivity(myIntent);
            }
        });
    }
}
