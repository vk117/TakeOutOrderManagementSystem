package com.example.varun.snapsauce;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.varun.snapsauce.MenuActivity;

import com.example.varun.snapsauce.datababse.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;

public class ItemActivity extends MenuActivity {

    private TextView heading;
    private TextView textView3;
    private TextView textView4;
    private TextView textView9;
    private TextView textView10;
    private Button addto;
    private EditText qty;

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    private String quantity;
    private int total = 0;
    private int total_time = 0;
    private String requestbody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        heading = (TextView) findViewById(R.id.heading);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        qty = (EditText) findViewById(R.id.qty);
        addto = (Button) findViewById(R.id.addtocart);

        textView9 = (TextView) findViewById(R.id.textView9);
        textView10 = (TextView) findViewById(R.id.textView10);



        Bundle extras = getIntent().getExtras();
        String header = extras.getString("item");
        final String user = extras.getString("user");

        //heading.setText(header);

        /*Scanner scanner = new Scanner(header);
        while(scanner.hasNextLine()){
            heading.setText(scanner.nextLine());
        }*/

        String[] lines = header.split("\n");

        final String name = lines[0];
        final String price = lines[1];
        String calories = lines[2];
        final String time = lines[3];

        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                quantity = qty.getText().toString();
                if(quantity.equals("")){quantity = "0";}

                total = Integer.parseInt(price) * Integer.parseInt(quantity);
                total_time = Integer.parseInt(time) * Integer.parseInt(quantity);
                if(total == 0){
                    addto.setText("Add to cart");
                    addto.setEnabled(false);
                    addto.setAlpha(0.5f);
                }
                else {
                    addto.setText("Add to cart" + " " + " " + " " + "$" + Integer.toString(total));
                    addto.setEnabled(true);
                    addto.setAlpha(1.0f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        heading.setText(name);

        textView3.setText("Price");

        textView4.setText("Calories");

        textView9.setText(price);

        textView10.setText(calories);



        addto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = qty.getText().toString();

                /*dbHelper = new DBHelper(ItemActivity.this);
                database = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(DBSchema.ORDERED_BY, user);
                values.put(DBSchema.QUANTITY, quantity);
                values.put(DBSchema.UNIT_PRICE, total);
                values.put(DBSchema.PREP_TIME, total_time);
                values.put(DBSchema.ITEM_NAME, name);

                long status = database.insert(DBSchema.TABLE3_NAME, null, values);

                if(status == -1) {
                    Toast.makeText(ItemActivity.this, "Could not add to cart", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(ItemActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(ItemActivity.this, CartActivity.class);
                    myIntent.putExtra("user", user);
                    startActivity(myIntent);
                    finish();

                }*/

                Api api = new Api();

                try{
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("orderBy", user);
                    jsonObject.put("name", name);
                    jsonObject.put("quantity", quantity);
                    jsonObject.put("unitPrice", total);
                    jsonObject.put("prepTime", total_time);

                    requestbody = jsonObject.toString();
                }catch (JSONException e){
                    e.printStackTrace();
                }

                api.post(ItemActivity.this, requestbody, "addtocart", new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        if(result.equals("200")){
                            Toast.makeText(ItemActivity.this, "Item added to the cart", Toast.LENGTH_SHORT).show();

                            Intent myIntent = new Intent(ItemActivity.this, CartActivity.class);
                            myIntent.putExtra("user", user);
                            startActivity(myIntent);
                            finish();
                        }
                        else{
                            Toast.makeText(ItemActivity.this, "Cannot add to cart", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onSuccessJSON(JSONArray arr) {

                    }
                });

            }
        });

    }

}
