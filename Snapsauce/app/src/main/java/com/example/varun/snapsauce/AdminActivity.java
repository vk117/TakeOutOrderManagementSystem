package com.example.varun.snapsauce;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.MediaStore;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

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
    private Button picture;
    private byte[] bytearray;

    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private String user;
    private String requestbody;

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
        picture = (Button) findViewById(R.id.picture);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);


        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePicture.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(takePicture, 1);
                }
            }
        });



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
                    /*dbHelper = new DBHelper(AdminActivity.this);
                    database = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();

                    values.put(DBSchema.CATEGORY, valCategory);
                    values.put(DBSchema.NAME2, valName);
                    values.put(DBSchema.PRICE, valPrice);
                    values.put(DBSchema.CALORIES, valCalories);
                    values.put(DBSchema.TIME, valTime);
                    values.put("image", bytearray);

                    long status = database.insert(DBSchema.TABLE2_NAME, null, values);
                    database.close();

                    if(status == -1) {
                        Toast.makeText(AdminActivity.this, "Item could not be added", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(AdminActivity.this, "Item added", Toast.LENGTH_SHORT).show();
                    }*/


                    Api api = new Api();

                    try{
                        JSONObject json = new JSONObject();

                        json.put("category", valCategory);
                        json.put("name", valName);
                        json.put("price",  valPrice);
                        json.put("calories", valCalories);
                        json.put("time", valTime);

                        requestbody = json.toString();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    api.post(AdminActivity.this, requestbody, "addproduct", new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            if(result.equals("200")){
                                Toast.makeText(AdminActivity.this, "Item added", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(AdminActivity.this, "Item could not be added", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onSuccessJSON(JSONArray array){}
                    });


                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bytearray = stream.toByteArray();
        }
    }
}
