package com.example.varun.snapsauce;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.varun.snapsauce.datababse.*;
import android.database.Cursor;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private String emailValue;
    private String passwordValue;
    private Spinner modes;
    private String valueMode;

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email2);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        modes = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mode, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modes.setAdapter(adapter);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailValue = email.getText().toString();
                passwordValue = password.getText().toString();
                valueMode = modes.getSelectedItem().toString();

                if(emailValue.equals("") || emailValue.trim().length()==0
                        || passwordValue.equals("") || passwordValue.trim().length()==0){
                    Toast.makeText(Login.this, R.string.fillAll, Toast.LENGTH_SHORT).show();
                }
                else if(valueMode.equals("Admin")){
                    if(emailValue.equals("admin") && passwordValue.equals("admin")){
                        Intent myIntent = new Intent(Login.this, AdminActivity.class);
                        myIntent.putExtra("user", "admin");
                        startActivity(myIntent);
                    }
                }
                else{
                    dbHelper = new DBHelper(Login.this);
                    database = dbHelper.getReadableDatabase();
                    String query = "SELECT * FROM " + DBSchema.TABLE_NAME + " WHERE " + DBSchema.EMAIL + "=" + "'" + emailValue + "'" +
                            " AND " + DBSchema.PASSWORD + "=" + "'" + passwordValue + "'";
                    Cursor cursor = database.rawQuery(query, null);

                    if(cursor.getCount()!=0){

                        Intent myIntent = new Intent(Login.this, MenuActivity.class);
                        myIntent.putExtra("user", emailValue);
                        startActivity(myIntent);

                        Intent intent = new Intent(Login.this, OrderFulfillmentService.class);
                        intent.putExtra("user", emailValue);
                        startService(intent);
                    }
                    else{
                        Toast.makeText(Login.this, "Wrong username/password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
