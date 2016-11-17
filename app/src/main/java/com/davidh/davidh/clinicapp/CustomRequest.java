package com.davidh.davidh.clinicapp;

import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomRequest {
    JsonObjectRequest jsonRequest;
    private RequestQueue req;
    public static JSONObject currentResponse;

    private void setCurrentResponse(JSONObject newResponse){
        currentResponse = newResponse;
    }


    private void makeRequest(Map<String, String> body, String url, int method){
        req.start();
        jsonRequest = new JsonObjectRequest(
                method,
                url,
                new JSONObject(body),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try{
                            Log.d("AFTERTREQUEST", response.toString());
                            setCurrentResponse(response);
                            req.stop();
                        }catch(Exception error){
                            Log.d("onError", error.getMessage());
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

        req.add(jsonRequest);
    }

}
