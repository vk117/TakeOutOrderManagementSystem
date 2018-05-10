package com.example.varun.snapsauce;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.varun.snapsauce.datababse.*;

import org.json.JSONArray;

import java.util.ArrayList;

public class CustomAdapter3 extends ArrayAdapter<String> {

    private String[] list;
    private Context context;
    private String[] renderable;
    private ArrayList<String[]> lines = new ArrayList<String[]>();

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public CustomAdapter3(@NonNull Context context, String[] list) {
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

        for(int i=0; i<lines.size(); i++){
            renderable = lines.get(position);
        }

        if(listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);

            TextView item = (TextView)listItem.findViewById(R.id.order);
            item.setText(renderable[0]);

            TextView status = (TextView)listItem.findViewById(R.id.status);
            status.setText(renderable[1]);

            Button cancel = (Button) listItem.findViewById(R.id.cancel_order);
            if(renderable[1].equals("complete")){
                System.out.println("Button should be invisible");
                cancel.setVisibility(View.GONE);

            }
            else {
                cancel.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*dbHelper = new DBHelper(getContext());
                        db = dbHelper.getWritableDatabase();

                        String sql = "DELETE FROM " + DBSchema.TABLE4_NAME + " WHERE " + " ( " + DBSchema.ITEM_NAME2 + "=" + "'" + renderable[0] + "'" +
                                " AND " + DBSchema.ORDERED_BY2 + "=" + "'" + renderable[2] + "'" + " ) ";
                        db.execSQL(sql);*/

                        Api api = new Api();

                        api.delete(context, "deleteorder/".concat(renderable[0]).concat("&").concat(renderable[2]), new VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                System.out.println(result);
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

                        OrderFulfillmentService.shouldContinue = false;
                        ((OrdersActivity) getContext()).recreate();
                    }
                });
            }
        }
        return  listItem;
    }
}
