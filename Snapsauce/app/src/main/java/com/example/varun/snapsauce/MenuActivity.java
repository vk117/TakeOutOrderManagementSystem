package com.example.varun.snapsauce;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.varun.snapsauce.datababse.*;
import com.example.varun.snapsauce.CustomAdapter;
import com.example.varun.snapsauce.datababse.*;

public class MenuActivity extends AppCompatActivity {

    public ListView menu;

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private String user;
    private DrawerLayout drawer;
    private CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menu = new ListView(this);
        menu = (ListView) findViewById(R.id.menu);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.cart){
                    Intent intent = new Intent(MenuActivity.this, CartActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.order_history){
                    Intent intent = new Intent(MenuActivity.this, OrdersActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                item.setChecked(true);
                drawer.closeDrawers();
                return true;
            }
        });

        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");



        dbHelper = new DBHelper(MenuActivity.this);
        db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DBSchema.TABLE2_NAME;
        Cursor cursor = db.rawQuery(query, null);

        int i = 0;

        String[] arr = new String[cursor.getCount()];
        //String[] brr = new String[cursor.getCount()];
        cursor.moveToFirst();

        if(!user.equals("admin")) {
            for (i = 0; i < cursor.getCount(); i++) {
                arr[i] = cursor.getString(cursor.getColumnIndex(DBSchema.NAME2));
                arr[i] = arr[i] + "\n" + cursor.getString(cursor.getColumnIndex(DBSchema.PRICE));
                arr[i] = arr[i] + "\n" + cursor.getString(cursor.getColumnIndex(DBSchema.CALORIES));
                arr[i] = arr[i] + "\n" + cursor.getString(cursor.getColumnIndex(DBSchema.TIME));
                cursor.moveToNext();
            }
        }
        else{
            System.out.println(user);
            for (i = 0; i < cursor.getCount(); i++) {
                arr[i] = cursor.getString(cursor.getColumnIndex(DBSchema.NAME2));
                arr[i] = arr[i] + "\n" + cursor.getString(cursor.getColumnIndex(DBSchema.PRICE));
                arr[i] = arr[i] + "\n" + cursor.getString(cursor.getColumnIndex(DBSchema.CALORIES));
                arr[i] = arr[i] + "\n" + cursor.getString(cursor.getColumnIndex(DBSchema.TIME));
                arr[i] = arr[i] + "\n" + user;
                cursor.moveToNext();
            }
        }


        adapter = new CustomAdapter(this, arr);
        menu.setAdapter(adapter);

        menu.setClickable(true);
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {


                if(!user.equals("admin")) {
                    String value = (String) adapter.getItemAtPosition(position);

                    System.out.println(value);

                    Intent myIntent = new Intent(MenuActivity.this, ItemActivity.class);

                    myIntent.putExtra("item", value);
                    myIntent.putExtra("user", user);

                    startActivity(myIntent);
                }
                else{
                    String value = (String) adapter.getItemAtPosition(position);
                    final String[] values = value.split("\n");

                    AlertDialog.Builder adb=new AlertDialog.Builder(MenuActivity.this);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete " + values[0]);
                    final int positionToRemove = position;
                    adb.setNegativeButton("Cancel", null);


                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            db = dbHelper.getWritableDatabase();
                            long status = db.delete(DBSchema.TABLE2_NAME, DBSchema.NAME2 + "=" + "'" + values[0] + "'", null);
                            System.out.println(status);
                            recreate();
                        }});
                    adb.show();

                    System.out.println(user);

                }
            }
        });
    }
}
