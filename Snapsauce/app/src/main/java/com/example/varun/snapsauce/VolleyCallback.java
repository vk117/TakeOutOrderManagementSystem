package com.example.varun.snapsauce;

import org.json.JSONArray;
import com.example.varun.snapsauce.CustomAdapter;

public interface VolleyCallback {

    void onSuccess(String result);
    void onSuccessJSON(JSONArray arr);
}
