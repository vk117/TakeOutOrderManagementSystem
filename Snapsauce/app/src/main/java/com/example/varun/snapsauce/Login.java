package com.example.varun.snapsauce;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.varun.snapsauce.datababse.*;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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
                    
                    Api api = new Api();
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", emailValue);
                    api.get(Login.this, "check/".concat(emailValue).concat("&").concat(passwordValue),new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            if(result.equals("200")){

                                Intent myIntent = new Intent(Login.this, MenuActivity.class);
                                myIntent.putExtra("user", emailValue);
                                startActivity(myIntent);

                                Intent intent = new Intent(Login.this, OrderFulfillmentService.class);
                                intent.putExtra("user", emailValue);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Login.this, "Failed to log in", Toast.LENGTH_SHORT).show();
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
