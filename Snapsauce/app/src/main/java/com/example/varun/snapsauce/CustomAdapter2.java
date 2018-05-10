package com.example.varun.snapsauce;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.varun.snapsauce.datababse.*;

import org.json.JSONArray;

import java.util.ArrayList;

public class CustomAdapter2 extends ArrayAdapter<String> {

    private String[] list;
    private Context context;
    private String[] renderable;
    private ArrayList<String[]> lines = new ArrayList<String[]>();

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public CustomAdapter2(@NonNull Context context, String[] list) {
        super(context, 0 , list);
        this.list = list;
        this.context = context;



        for(String elements: list){
            //System.out.println(elements);
            lines.add(elements.split("\n"));
        }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        for (int i = 0; i < lines.size(); i++) {
            renderable = lines.get(position);
        }

        if(listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);

            TextView qty = (TextView)listItem.findViewById(R.id.qty);
            qty.setText(renderable[3]);

            TextView name = (TextView)listItem.findViewById(R.id.food_name);
            name.setText(renderable[0]);

            TextView price = (TextView)listItem.findViewById(R.id.price_show);
            price.setText(renderable[1]);

            final Button remove = (Button)listItem.findViewById(R.id.button);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Api api = new Api();

                    api.delete(context, "deletecart/".concat(renderable[0]).concat("&").concat(renderable[2]), new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            if(result.equals("200")){
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(context, "Not Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onSuccessJSON(JSONArray arr) {

                        }
                    });

                    ((CartActivity)getContext()).recreate();
                }
            });


        }
        return listItem;

    }
}
