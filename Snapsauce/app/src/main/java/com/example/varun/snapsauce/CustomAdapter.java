package com.example.varun.snapsauce;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.varun.snapsauce.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String>{

    private String[] list;
    private Context context;
    private String renderable[];
    private Bitmap[] images;
    private ArrayList<String[]> lines = new ArrayList<String[]>();

    public CustomAdapter(@NonNull Context context, String[] list) {
        super(context, 0 , list);
        this.list = list;
        this.context = context;


            for(String elements: list){
                //System.out.println(elements);
                if(elements!=null) {
                    lines.add(elements.split("\n"));
                }
            }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;

        for(int i=0; i<lines.size(); i++){
            renderable = lines.get(position);
        }

        //System.out.println(display);

        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);

                TextView food_name = (TextView)listItem.findViewById(R.id.food_name);
                food_name.setText(renderable[0]);

                TextView price_show = (TextView)listItem.findViewById(R.id.price_show);
                price_show.setText("$" + renderable[1]);

                TextView calories_name = (TextView)listItem.findViewById(R.id.calories_name);
                calories_name.setText("Calories: " + renderable[2]);

        }

        else if(renderable[4] == "admin"){
            TextView food_name = (TextView)listItem.findViewById(R.id.food_name);
            food_name.setText(renderable[0]);

            TextView price_show = (TextView)listItem.findViewById(R.id.price_show);
            price_show.setText("$" + renderable[1]);

            TextView calories_name = (TextView)listItem.findViewById(R.id.calories_name);
            calories_name.setText("Calories: " + renderable[2]);

            TextView prep_time = (TextView)listItem.findViewById(R.id.prep_time);
            prep_time.setText("Time: " + renderable[3] + " minutes");

        }

        return listItem;
    }
}
