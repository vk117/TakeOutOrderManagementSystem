package com.example.varun.snapsauce;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.varun.snapsauce.datababse.*;

import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class OrderFulfillmentService extends IntentService {


    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private String[] arr;
    private String[] order_by;
    private String[] prep_time;
    private ArrayList<String[]> lines = new ArrayList<String[]>();

    private int time=0;
    private String status = "complete";
    public static volatile boolean shouldContinue = true;

    public OrderFulfillmentService() {
        super("OrderFulfillmentService");
    }

    public void timer(int i){

    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        final String user = extras.getString("user");

        dbHelper = new DBHelper(OrderFulfillmentService.this);
        database = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DBSchema.TABLE4_NAME;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        arr = new String[cursor.getCount()];
        order_by = new String[cursor.getCount()];
        prep_time = new String[cursor.getCount()];

        int i;

        for(i = 0; i<cursor.getCount(); i++){
            if(cursor.getString(cursor.getColumnIndex(DBSchema.STATUS)).equals("incomplete")) {
                arr[i] = cursor.getString(cursor.getColumnIndex(DBSchema.ORDERED_BY2));
                arr[i] = arr[i] + "\n" + cursor.getString(cursor.getColumnIndex(DBSchema.PREP_TIME2));
                lines.add(arr[i].split("\n"));
                System.out.println(cursor.getString(cursor.getColumnIndex(DBSchema.PREP_TIME2)));
                cursor.moveToNext();
            }
            else{
                cursor.moveToNext();
            }
        }

        if(shouldContinue == true) {
            for (i = 1; i <= lines.size(); i++) {
                time = Integer.parseInt(lines.get(i - 1)[1]) * 60;
                try {
                    for (int a = time; a >= 0; a--) {
                        Thread.sleep(1000);
                        System.out.println(a);

                        if (a == 0) {

                            database = dbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();

                            values.put(DBSchema.STATUS, "complete");

                            long status = database.update(DBSchema.TABLE4_NAME, values,
                                    DBSchema.ORDERED_BY2 + "=" + "'" + lines.get(i - 1)[0] + "'", null);


                            new Thread(new Runnable() {

                                public void run() {

                                    try {

                                        GMailSender sender = new GMailSender(

                                                "snapsauce17@gmail.com",

                                                "Sonal@1717");

                                        sender.sendMail("Thank you ", "Thank you for ordering with Snapsauce! \nEnjoy your meal!",

                                                "snapsauce17@gmail.com",

                                                user.toString());

                                    } catch (Exception e) {

                                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                                    }

                                }

                            }).start();

                            if (a < 600) {
                                new Thread(new Runnable() {

                                    public void run() {

                                        try {

                                            GMailSender sender = new GMailSender(

                                                    "snapsauce17@gmail.com",

                                                    "Sonal@1717");

                                            sender.sendMail("Hope you are hungry ", "Hi! \nYour order is almost ready",

                                                    "snapsauce17@gmail.com",

                                                    user.toString());

                                        } catch (Exception e) {

                                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                                        }

                                    }

                                }).start();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interrupted !");
                }
            }
        }
        else {
            stopSelf();
            return;
        }
    }


}
