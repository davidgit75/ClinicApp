package com.davidh.davidh.clinicapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatienHistory extends Fragment {

    View rootView;
    SharedPreferences preferences;

    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryTemplate> histories = new ArrayList<>();

    String idUser;

    JsonObjectRequest jsonRequest;
    private RequestQueue req;

    public PatienHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_patien_history, container, false);

        req = Volley.newRequestQueue(rootView.getContext());

        recyclerView = (RecyclerView)rootView.findViewById(R.id.patient_list_histories);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);

        preferences = rootView.getContext().getSharedPreferences("user_connected", Context.MODE_PRIVATE);
        idUser = preferences.getString("identification", "");

        getHistories(idUser);

        // Inflate the layout for this fragment
        return rootView;
    }

    private void getHistories(String identification){
        req.start();
        jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                UrlService.getHistoryByPatient + identification,
                generateBody(),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try{
                            Log.d("AFTERTREQUEST", response.toString());

                            if(response.getString("status").equals("error")){
                                Snackbar.make(rootView, response.getString("message"), Snackbar.LENGTH_LONG).show();
                            }else if(response.getString("status").equals("success")){
                                JSONArray objHs = response.getJSONArray("data");

                                if(objHs.length()<=0){
                                    Snackbar.make(rootView, "No hay historial para mostrar", Snackbar.LENGTH_LONG).show();
                                }else{
                                    histories = new ArrayList<>();

                                    for(int i=0; i<objHs.length(); i++){
                                        histories.add(new HistoryTemplate(
                                                objHs.getJSONObject(i).getString("reason_to_query"),
                                                objHs.getJSONObject(i).getString("recommendations"),
                                                objHs.getJSONObject(i).getJSONObject("medic").getString("names"),
                                                objHs.getJSONObject(i).getString("date"),
                                                objHs.getJSONObject(i)
                                        ));
                                    }

                                    historyAdapter = new HistoryAdapter(rootView.getContext(), histories);
                                    recyclerView.setAdapter(historyAdapter);
                                    historyAdapter.notifyDataSetChanged();
                                }
                            }

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


    private JSONObject generateBody(){
        Map body = new HashMap();
        body.put("identification", idUser);
        return new JSONObject(body);
    }

}
