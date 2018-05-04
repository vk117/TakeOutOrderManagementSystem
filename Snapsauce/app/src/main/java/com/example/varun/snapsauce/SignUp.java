package com.example.varun.snapsauce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import com.example.varun.snapsauce.datababse.DBSchema;
import com.example.varun.snapsauce.datababse.DBHelper;
import android.content.ContentValues;

public class SignUp extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText phoneNumber;
    private Button proceed;

    private String nameValue;
    private String emailValue;
    private String passwordValue;
    private String phoneValue;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password2);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        proceed = (Button) findViewById(R.id.signup);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameValue = name.getText().toString();
                emailValue = email.getText().toString();
                passwordValue = password.getText().toString();
                phoneValue = phoneNumber.getText().toString();

                if(nameValue.equals("") || nameValue.trim().length()==0 ||
                        emailValue.equals("") || emailValue.trim().length()==0 ||
                        passwordValue.equals("") || passwordValue.trim().length()==0 ||
                        phoneValue.equals("") || phoneValue.trim().length()==0){

                    Toast.makeText(SignUp.this, R.string.fillAll, Toast.LENGTH_SHORT).show();
                }
                else{
                    dbHelper = new DBHelper(SignUp.this);
                    database = dbHelper.getWritableDatabase();


                    ContentValues values = new ContentValues();

                    values.put(DBSchema.NAME, nameValue);
                    values.put(DBSchema.EMAIL, emailValue);
                    values.put(DBSchema.PASSWORD, passwordValue);
                    values.put(DBSchema.PHONE, phoneValue);

                    long status = database.insert(DBSchema.TABLE_NAME, null, values);
                    database.close();

                    if(status == -1) {
                        Toast.makeText(SignUp.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SignUp.this, "Signed Up", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
