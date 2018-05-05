package com.example.varun.snapsauce;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.varun.snapsauce.MenuActivity;

import com.example.varun.snapsauce.datababse.*;

import java.util.Scanner;

public class ItemActivity extends MenuActivity {

    private TextView heading;
    private TextView textView3;
    private TextView textView4;
    private Button addto;
    private EditText qty;

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    private String quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        heading = (TextView) findViewById(R.id.heading);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        qty = (EditText) findViewById(R.id.qty);
        addto = (Button) findViewById(R.id.addtocart);


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


        heading.setText(name);

        textView3.setText(price);

        textView4.setText(calories);

        addto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = qty.getText().toString();

                dbHelper = new DBHelper(ItemActivity.this);
                database = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(DBSchema.ORDERED_BY, user);
                values.put(DBSchema.QUANTITY, quantity);
                values.put(DBSchema.UNIT_PRICE, price);
                values.put(DBSchema.PREP_TIME, time);
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
                }

            }
        });

    }

}
