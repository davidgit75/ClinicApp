package com.davidh.davidh.clinicapp;

import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomRequest {
    private RequestQueue req;
    private JsonObjectRequest jsonRequest;
    private JSONObject CurrentReponse;

    public JSONObject checkUserInMedicalCenter(String[] keys, String[] values){
        return startRequest(keys, values, ConfigUrl.checkUserInMedicalCenter);
    }


    private JSONObject startRequest(String[] keys, String[] values, String url){
        this.jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                setBodyRequest(keys, values),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try{
                            CurrentReponse = response;
                            req.stop();
                        }catch(Exception e){
                            //Log.d("onError", error.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("onError", error.getMessage());
                        req.stop();
                    }
                }
        );

        return CurrentReponse;
    }


    private JSONObject setBodyRequest(String[] keys, String[] values){
        Map<String, String> body = new HashMap<>();

        for(int i=0; i<keys.length; i++){
            body.put(keys[i], values[i]);
        }

        return new JSONObject(body);
    }
}
