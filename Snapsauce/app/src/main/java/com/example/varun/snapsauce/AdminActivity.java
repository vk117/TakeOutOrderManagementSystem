package com.example.varun.snapsauce;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.varun.snapsauce.datababse.DBHelper;
import com.example.varun.snapsauce.datababse.DBSchema;

public class AdminActivity extends AppCompatActivity {

    private Spinner category;
    private EditText name;
    private EditText price;
    private EditText time;
    private EditText calories;
    private Button add;

    private String valCategory;
    private String valName;
    private String valPrice;
    private String valTime;
    private String valCalories;
    private DrawerLayout drawer;

    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Bundle bundle = getIntent().getExtras();
        user = bundle.getString("user");

        drawer = (DrawerLayout) findViewById(R.id.drawer_admin);
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view2);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.remove){
                    Intent intent = new Intent(AdminActivity.this, MenuActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                return true;
            }
        });

        category = (Spinner) findViewById(R.id.spinner2);
        name = (EditText) findViewById(R.id.name2);
        price = (EditText) findViewById(R.id.price);
        time = (EditText) findViewById(R.id.time);
        add = (Button) findViewById(R.id.add);
        calories = (EditText) findViewById(R.id.calories);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valCategory = category.getSelectedItem().toString();
                valName = name.getText().toString();
                valPrice = price.getText().toString();
                valTime = time.getText().toString();
                valCalories = calories.getText().toString();

                if(valName.equals("") || valName.trim().length()==0
                        || valPrice.equals("") || valPrice.trim().length()==0
                        || valTime.equals("") || valTime.trim().length()==0){
                    Toast.makeText(AdminActivity.this, R.string.fillAll, Toast.LENGTH_SHORT).show();
                }
                else {
                    dbHelper = new DBHelper(AdminActivity.this);
                    database = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();

                    values.put(DBSchema.CATEGORY, valCategory);
                    values.put(DBSchema.NAME2, valName);
                    values.put(DBSchema.PRICE, valPrice);
                    values.put(DBSchema.CALORIES, valCalories);
                    values.put(DBSchema.TIME, valTime);

                    long status = database.insert(DBSchema.TABLE2_NAME, null, values);
                    database.close();

                    if(status == -1) {
                        Toast.makeText(AdminActivity.this, "Item could not be added", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(AdminActivity.this, "Item added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
