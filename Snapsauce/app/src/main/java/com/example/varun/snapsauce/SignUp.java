package com.example.varun.snapsauce;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import com.example.varun.snapsauce.datababse.DBSchema;
import com.example.varun.snapsauce.datababse.DBHelper;
import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private String requestbody;
    private String response;

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

                    Api api = new Api();
                    try {
                        JSONObject jsonbody = new JSONObject();

                        jsonbody.put("email", emailValue);
                        jsonbody.put("password", passwordValue);
                        jsonbody.put("name", nameValue);
                        jsonbody.put("phone", phoneValue);
                        requestbody = jsonbody.toString();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    api.post(SignUp.this, requestbody, "add", new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            if(!result.equals("200")) {
                                Toast.makeText(SignUp.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(SignUp.this, "Signed Up", Toast.LENGTH_SHORT).show();
                                new Thread(new Runnable() {

                                    public void run() {

                                        try {

                                            GMailSender sender = new GMailSender(

                                                    "snapsauce17@gmail.com",

                                                    "Sonal@1717");

                                            sender.sendMail("Welcome to the team", "On behalf of the snapsauce team, we welcome you!",

                                                    "snapsauce17@gmail.com",

                                                    emailValue.toString());

                                        } catch (Exception e) {

                                            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();

                                        }

                                    }

                                }).start();

                            }
                        }

                        @Override
                        public void onSuccessJSON(JSONArray arr){}
                    });

                }
            }
        });

    }
}
