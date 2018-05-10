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
import android.widget.Toast;

import com.example.varun.snapsauce.datababse.*;
import com.example.varun.snapsauce.CustomAdapter;
import com.example.varun.snapsauce.datababse.*;

import org.json.JSONArray;
import org.json.JSONException;

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

    private String[] arr;
    private String[] arr2;
    private String[] arr3;
    private String[] arr4;


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


        Api api1 = new Api();
        api1.getJSON(MenuActivity.this, "getproducts/".concat("Drink"), new VolleyCallback() {
            @Override
            public void onSuccessJSON(JSONArray array) {
                arr = new String[array.length()];

                for(int i=0; i<array.length(); i++){
                    try{
                        arr[i] = array.getJSONObject(i).getString("name");
                        arr[i] = arr[i] + "\n" + array.getJSONObject(i).getString("price");
                        arr[i] = arr[i] + "\n" + array.getJSONObject(i).getString("calories");
                        arr[i] = arr[i] + "\n" + array.getJSONObject(i).getString("time");
                        arr[i] = arr[i] + "\n" + user;
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                adapter = new CustomAdapter(MenuActivity.this, arr);
                drink.setAdapter(adapter);
                ListUtils.setDynamicHeight(drink);
            }

            @Override
            public void onSuccess(String result){}
        });


        Api api2 = new Api();
        api2.getJSON(MenuActivity.this, "getproducts/".concat("Appetizer"), new VolleyCallback() {
            @Override
            public void onSuccessJSON(JSONArray array) {
                arr2 = new String[array.length()];

                for(int i=0; i<array.length(); i++){
                    try{
                        arr2[i] = array.getJSONObject(i).getString("name");
                        arr2[i] = arr2[i] + "\n" + array.getJSONObject(i).getString("price");
                        arr2[i] = arr2[i] + "\n" + array.getJSONObject(i).getString("calories");
                        arr2[i] = arr2[i] + "\n" + array.getJSONObject(i).getString("time");
                        arr2[i] = arr2[i] + "\n" + user;
                        System.out.println(arr2[i]);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                adapter2 = new CustomAdapter(MenuActivity.this, arr2);
                appetizer.setAdapter(adapter2);
                ListUtils.setDynamicHeight(appetizer);
            }

            @Override
            public void onSuccess(String result){}
        });


        Api api3 = new Api();
        api3.getJSON(MenuActivity.this, "getproducts/".concat("Main course"), new VolleyCallback() {
            @Override
            public void onSuccessJSON(JSONArray array) {
                arr3 = new String[array.length()];

                for(int i=0; i<array.length(); i++){
                    try{
                        arr3[i] = array.getJSONObject(i).getString("name");
                        arr3[i] = arr3[i] + "\n" + array.getJSONObject(i).getString("price");
                        arr3[i] = arr3[i] + "\n" + array.getJSONObject(i).getString("calories");
                        arr3[i] = arr3[i] + "\n" + array.getJSONObject(i).getString("time");
                        arr3[i] = arr3[i] + "\n" + user;
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                adapter3 = new CustomAdapter(MenuActivity.this, arr3);
                main.setAdapter(adapter3);
                ListUtils.setDynamicHeight(main);
            }

            @Override
            public void onSuccess(String result){}
        });


        Api api4 = new Api();
        api4.getJSON(MenuActivity.this, "getproducts/".concat("Dessert"), new VolleyCallback() {
            @Override
            public void onSuccessJSON(JSONArray array) {
                arr4 = new String[array.length()];

                for(int i=0; i<array.length(); i++){
                    try{
                        arr4[i] = array.getJSONObject(i).getString("name");
                        arr4[i] = arr4[i] + "\n" + array.getJSONObject(i).getString("price");
                        arr4[i] = arr4[i] + "\n" + array.getJSONObject(i).getString("calories");
                        arr4[i] = arr4[i] + "\n" + array.getJSONObject(i).getString("time");
                        arr4[i] = arr4[i] + "\n" + user;
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                adapter4 = new CustomAdapter(MenuActivity.this, arr4);
                dessert.setAdapter(adapter4);
                ListUtils.setDynamicHeight(dessert);
            }

            @Override
            public void onSuccess(String result){}
        });


        drink.setClickable(true);
        drink.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!user.equals("admin")) {
                    String value = (String) parent.getItemAtPosition(position);

                    System.out.println(value);

                    Intent myIntent = new Intent(MenuActivity.this, ItemActivity.class);

                    myIntent.putExtra("item", value);
                    myIntent.putExtra("user", user);

                    startActivity(myIntent);
                }
                else{
                    final String value = (String) parent.getItemAtPosition(position);
                    final String[] values = value.split("\n");

                    AlertDialog.Builder adb=new AlertDialog.Builder(MenuActivity.this);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete " + values[0]);
                    final int positionToRemove = position;
                    adb.setNegativeButton("Cancel", null);


                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           Api api = new Api();
                           api.delete(MenuActivity.this, "deleteproduct/".concat(values[0]), new VolleyCallback() {
                               @Override
                               public void onSuccess(String result) {
                                   if(result.equals("200")){
                                       Toast.makeText(MenuActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                   }
                                   else{
                                       Toast.makeText(MenuActivity.this, "Not Deleted", Toast.LENGTH_SHORT).show();
                                   }
                                   recreate();
                               }

                               @Override
                               public void onSuccessJSON(JSONArray arr) {

                               }
                           });
                           recreate();
                        }});
                    adb.show();

                    //System.out.println(user);

                }
            }
        });


        appetizer.setClickable(true);
        appetizer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!user.equals("admin")) {
                    String value = (String) parent.getItemAtPosition(position);

                    System.out.println(value);

                    Intent myIntent = new Intent(MenuActivity.this, ItemActivity.class);

                    myIntent.putExtra("item", value);
                    myIntent.putExtra("user", user);

                    startActivity(myIntent);
                }
                else{
                    final String value = (String) parent.getItemAtPosition(position);
                    final String[] values = value.split("\n");

                    AlertDialog.Builder adb=new AlertDialog.Builder(MenuActivity.this);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete " + values[0]);
                    final int positionToRemove = position;
                    adb.setNegativeButton("Cancel", null);


                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Api api = new Api();
                            api.delete(MenuActivity.this, "deleteproduct/".concat(values[0]), new VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    if(result.equals("200")){
                                        Toast.makeText(MenuActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(MenuActivity.this, "Not Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                    recreate();
                                }

                                @Override
                                public void onSuccessJSON(JSONArray arr) {

                                }
                            });
                            recreate();
                        }});
                    adb.show();

                }
            }
        });


        main.setClickable(true);
        main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!user.equals("admin")) {
                    String value = (String) parent.getItemAtPosition(position);

                    System.out.println(value);

                    Intent myIntent = new Intent(MenuActivity.this, ItemActivity.class);

                    myIntent.putExtra("item", value);
                    myIntent.putExtra("user", user);

                    startActivity(myIntent);
                }
                else{
                    final String value = (String) parent.getItemAtPosition(position);
                    final String[] values = value.split("\n");

                    AlertDialog.Builder adb=new AlertDialog.Builder(MenuActivity.this);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete " + values[0]);
                    final int positionToRemove = position;
                    adb.setNegativeButton("Cancel", null);


                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Api api = new Api();
                            api.delete(MenuActivity.this, "deleteproduct/".concat(values[0]), new VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    if(result.equals("200")){
                                        Toast.makeText(MenuActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(MenuActivity.this, "Not Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                    recreate();
                                }

                                @Override
                                public void onSuccessJSON(JSONArray arr) {

                                }
                            });
                            recreate();
                        }});
                    adb.show();

                }
            }
        });


        dessert.setClickable(true);
        dessert.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!user.equals("admin")) {
                    String value = (String) parent.getItemAtPosition(position);

                    System.out.println(value);

                    Intent myIntent = new Intent(MenuActivity.this, ItemActivity.class);

                    myIntent.putExtra("item", value);
                    myIntent.putExtra("user", user);

                    startActivity(myIntent);
                }
                else{
                    final String value = (String) parent.getItemAtPosition(position);
                    final String[] values = value.split("\n");

                    AlertDialog.Builder adb=new AlertDialog.Builder(MenuActivity.this);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete " + values[0]);
                    final int positionToRemove = position;
                    adb.setNegativeButton("Cancel", null);


                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Api api = new Api();
                            api.delete(MenuActivity.this, "deleteproduct/".concat(values[0]), new VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    if(result.equals("200")){
                                        Toast.makeText(MenuActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(MenuActivity.this, "Not Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                    recreate();
                                }

                                @Override
                                public void onSuccessJSON(JSONArray arr) {

                                }
                            });
                            recreate();
                        }});
                    adb.show();

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
