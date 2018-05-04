package com.example.varun.snapsauce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Scanner;

public class ItemActivity extends AppCompatActivity {

    private TextView heading;
    private TextView textView3;
    private TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        heading = (TextView) findViewById(R.id.heading);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);

        Bundle extras = getIntent().getExtras();
        String header = extras.getString("item");

        //heading.setText(header);

        /*Scanner scanner = new Scanner(header);
        while(scanner.hasNextLine()){
            heading.setText(scanner.nextLine());
        }*/

        String[] lines = header.split("\n");
        String second = lines[1];

        heading.setText(lines[0]);

        textView3.setText(second);

        textView4.setText(lines[2]);
    }
}
