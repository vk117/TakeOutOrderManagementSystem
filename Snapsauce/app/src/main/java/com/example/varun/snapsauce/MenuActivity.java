package com.example.varun.snapsauce;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.example.varun.snapsauce.datababse.*;
import com.example.varun.snapsauce.CustomAdapter;
import com.example.varun.snapsauce.datababse.*;

public class MenuActivity extends AppCompatActivity {

    public ListView drink;
    public ListView appetizer;
    public ListView main;
    public ListView dessert;

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private String user;
    private DrawerLayout drawer;

    private CustomAdapter adapter;
    private CustomAdapter adapter2;
    private CustomAdapter adapter3;
    private CustomAdapter adapter4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        drink = new ListView(this);
        drink = (ListView) findViewById(R.id.menu);

        appetizer =  new ListView(this);
        appetizer = (ListView) findViewById(R.id.appetizer);

        main = new ListView(this);
        main = (ListView) findViewById(R.id.main);

        dessert = new ListView(this);
        dessert = (ListView) findViewById(R.id.dessert);

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

        String query1 = "SELECT * FROM " + DBSchema.TABLE2_NAME + " WHERE " + DBSchema.CATEGORY + "=" + "'" + "Drink" + "'";
        Cursor cursor1 = db.rawQuery(query1, null);

        String query2 = "SELECT * FROM " + DBSchema.TABLE2_NAME + " WHERE " + DBSchema.CATEGORY + "=" + "'" + "Appetizer" + "'";
        Cursor cursor2 = db.rawQuery(query2, null);

        String query3 = "SELECT * FROM " + DBSchema.TABLE2_NAME + " WHERE " + DBSchema.CATEGORY + "=" + "'" + "Main course" + "'";
        Cursor cursor3 = db.rawQuery(query3, null);

        String query4 = "SELECT * FROM " + DBSchema.TABLE2_NAME + " WHERE " + DBSchema.CATEGORY + "=" + "'" + "Dessert" + "'";
        Cursor cursor4 = db.rawQuery(query4, null);

        int i = 0;
        int j = 0;

        String[] arr = new String[cursor1.getCount()];
        String[] arr2 = new String[cursor2.getCount()];
        String[] arr3 = new String[cursor3.getCount()];
        String[] arr4 = new String[cursor4.getCount()];

        cursor1.moveToFirst();

        if(!user.equals("admin")) {
            if(cursor1.getCount()>0) {
                for (i = 0; i < cursor1.getCount(); i++) {
                    arr[i] = cursor1.getString(cursor1.getColumnIndex(DBSchema.NAME2));
                    arr[i] = arr[i] + "\n" + cursor1.getString(cursor1.getColumnIndex(DBSchema.PRICE));
                    arr[i] = arr[i] + "\n" + cursor1.getString(cursor1.getColumnIndex(DBSchema.CALORIES));
                    arr[i] = arr[i] + "\n" + cursor1.getString(cursor1.getColumnIndex(DBSchema.TIME));
                    byte[] bytes = cursor1.getBlob(cursor1.getColumnIndex("image"));
                    cursor1.moveToNext();
                }
            }
        }
        else{
            if(cursor1.getCount()>0) {
                for (i = 0; i < cursor1.getCount(); i++) {
                    arr[i] = cursor1.getString(cursor1.getColumnIndex(DBSchema.NAME2));
                    arr[i] = arr[i] + "\n" + cursor1.getString(cursor1.getColumnIndex(DBSchema.PRICE));
                    arr[i] = arr[i] + "\n" + cursor1.getString(cursor1.getColumnIndex(DBSchema.CALORIES));
                    arr[i] = arr[i] + "\n" + cursor1.getString(cursor1.getColumnIndex(DBSchema.TIME));
                    arr[i] = arr[i] + "\n" + user;
                    byte[] bytes = cursor1.getBlob(cursor1.getColumnIndex("image"));
                    cursor1.moveToNext();
                }
            }
        }

        cursor2.moveToFirst();

        if(!user.equals("admin")) {
            if(cursor2.getCount()>0) {
                for (j = 0; j < cursor2.getCount(); j++) {
                    arr2[j] = cursor2.getString(cursor2.getColumnIndex(DBSchema.NAME2));
                    arr2[j] = arr2[j] + "\n" + cursor2.getString(cursor2.getColumnIndex(DBSchema.PRICE));
                    arr2[j] = arr2[j] + "\n" + cursor2.getString(cursor2.getColumnIndex(DBSchema.CALORIES));
                    arr2[j] = arr2[j] + "\n" + cursor2.getString(cursor2.getColumnIndex(DBSchema.TIME));
                    byte[] bytes = cursor2.getBlob(cursor2.getColumnIndex("image"));
                    cursor2.moveToNext();
                }
            }
        }
        else{
            if(cursor2.getCount()>0) {
                for (j = 0; j < cursor2.getCount(); j++) {
                    arr2[j] = cursor2.getString(cursor2.getColumnIndex(DBSchema.NAME2));
                    arr2[j] = arr2[j] + "\n" + cursor2.getString(cursor2.getColumnIndex(DBSchema.PRICE));
                    arr2[j] = arr2[j] + "\n" + cursor2.getString(cursor2.getColumnIndex(DBSchema.CALORIES));
                    arr2[j] = arr2[j] + "\n" + cursor2.getString(cursor2.getColumnIndex(DBSchema.TIME));
                    arr2[j] = arr2[j] + "\n" + user;
                    byte[] bytes = cursor2.getBlob(cursor2.getColumnIndex("image"));
                    cursor2.moveToNext();
                }
            }
        }

        cursor3.moveToFirst();

        if(!user.equals("admin")) {
            if(cursor3.getCount()>0) {
                for (i = 0; i < cursor3.getCount(); i++) {
                    arr3[i] = cursor3.getString(cursor3.getColumnIndex(DBSchema.NAME2));
                    arr3[i] = arr3[i] + "\n" + cursor3.getString(cursor3.getColumnIndex(DBSchema.PRICE));
                    arr3[i] = arr3[i] + "\n" + cursor3.getString(cursor3.getColumnIndex(DBSchema.CALORIES));
                    arr3[i] = arr3[i] + "\n" + cursor3.getString(cursor3.getColumnIndex(DBSchema.TIME));
                    byte[] bytes = cursor3.getBlob(cursor3.getColumnIndex("image"));
                    cursor3.moveToNext();
                }
            }
        }
        else{
            if(cursor3.getCount()>0) {
                for (i = 0; i < cursor3.getCount(); i++) {
                    arr3[i] = cursor3.getString(cursor3.getColumnIndex(DBSchema.NAME2));
                    arr3[i] = arr3[i] + "\n" + cursor3.getString(cursor3.getColumnIndex(DBSchema.PRICE));
                    arr3[i] = arr3[i] + "\n" + cursor3.getString(cursor3.getColumnIndex(DBSchema.CALORIES));
                    arr3[i] = arr3[i] + "\n" + cursor3.getString(cursor3.getColumnIndex(DBSchema.TIME));
                    arr3[i] = arr3[i] + "\n" + user;
                    byte[] bytes = cursor3.getBlob(cursor3.getColumnIndex("image"));
                    cursor3.moveToNext();
                }
            }
        }

        cursor4.moveToFirst();

        if(!user.equals("admin")) {
            if(cursor4.getCount()>0) {
                for (i = 0; i < cursor4.getCount(); i++) {
                    arr4[i] = cursor4.getString(cursor4.getColumnIndex(DBSchema.NAME2));
                    arr4[i] = arr4[i] + "\n" + cursor4.getString(cursor4.getColumnIndex(DBSchema.PRICE));
                    arr4[i] = arr4[i] + "\n" + cursor4.getString(cursor4.getColumnIndex(DBSchema.CALORIES));
                    arr4[i] = arr4[i] + "\n" + cursor4.getString(cursor4.getColumnIndex(DBSchema.TIME));
                    byte[] bytes = cursor4.getBlob(cursor4.getColumnIndex("image"));
                    cursor4.moveToNext();
                }
            }
        }
        else{
            if(cursor4.getCount()>0) {
                for (i = 0; i < cursor4.getCount(); i++) {
                    arr4[i] = cursor4.getString(cursor4.getColumnIndex(DBSchema.NAME2));
                    arr4[i] = arr4[i] + "\n" + cursor4.getString(cursor4.getColumnIndex(DBSchema.PRICE));
                    arr4[i] = arr4[i] + "\n" + cursor4.getString(cursor4.getColumnIndex(DBSchema.CALORIES));
                    arr4[i] = arr4[i] + "\n" + cursor4.getString(cursor4.getColumnIndex(DBSchema.TIME));
                    arr4[i] = arr4[i] + "\n" + user;
                    byte[] bytes = cursor4.getBlob(cursor4.getColumnIndex("image"));
                    cursor4.moveToNext();
                }
            }
        }


        adapter = new CustomAdapter(this, arr);
        drink.setAdapter(adapter);

        adapter2 = new CustomAdapter(this, arr2);
        appetizer.setAdapter(adapter2);

        adapter3 = new CustomAdapter(this, arr3);
        main.setAdapter(adapter3);

        adapter4 = new CustomAdapter(this, arr4);
        dessert.setAdapter(adapter4);

        ListUtils.setDynamicHeight(drink);
        ListUtils.setDynamicHeight(appetizer);
        ListUtils.setDynamicHeight(main);
        ListUtils.setDynamicHeight(dessert);


        drink.setClickable(true);
        drink.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


        appetizer.setClickable(true);
        appetizer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


        main.setClickable(true);
        main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        dessert.setClickable(true);
        dessert.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
}
