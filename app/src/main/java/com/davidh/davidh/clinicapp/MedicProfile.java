package com.davidh.davidh.clinicapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicProfile extends Fragment {

    View rootView;
    EditText names, lastNames, email, identification, occupation, sex, civilState, phone, password, newPassword;
    Button send;
    SharedPreferences preferences;

    JsonObjectRequest jsonRequest;
    RequestQueue req;


    public MedicProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_medic_profile, container, false);

        req = Volley.newRequestQueue(getActivity().getApplicationContext());

        names = (EditText) rootView.findViewById(R.id.mepNames);
        lastNames = (EditText) rootView.findViewById(R.id.mepLastNames);
        email = (EditText) rootView.findViewById(R.id.mepEmail);
        identification = (EditText) rootView.findViewById(R.id.mepIdentification);
        occupation = (EditText) rootView.findViewById(R.id.mepOccupation);
        sex = (EditText) rootView.findViewById(R.id.mepSex);
        civilState = (EditText) rootView.findViewById(R.id.mepCivilState);
        password = (EditText) rootView.findViewById(R.id.mepPassword);
        newPassword = (EditText) rootView.findViewById(R.id.mepNewPassword);
        phone = (EditText) rootView.findViewById(R.id.mepPhone);
        send = (Button) rootView.findViewById(R.id.mepSend);
        preferences = rootView.getContext().getSharedPreferences("user_connected", Context.MODE_PRIVATE);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){

                    if(
                            (!password.getText().toString().equals("")&&newPassword.getText().toString().equals("")) ||
                            (password.getText().toString().equals("")&&!newPassword.getText().toString().equals(""))
                            ){
                        Snackbar.make(rootView, "Para modificar su contraseña debe ingresar ambos campos", Snackbar.LENGTH_SHORT).show();

                    }else{
                        editProfile();
                    }


                }
            }
        });

        setInitForm();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void editProfile() {
        req.start();
        jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                UrlService.editProfile,
                getBody(),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try{
                            Log.d("AFTERTEDITXXX", response.toString());

                            //if(response.getString("status").equals("success"))
                            Snackbar.make(rootView, response.getString("message"), Snackbar.LENGTH_LONG).show();

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

    private JSONObject getBody() {
        Map data = new HashMap();
        Map pass = new HashMap();
        Map body = new HashMap();

        data.put("names", names.getText().toString());
        data.put("lastnames", lastNames.getText().toString());
        data.put("email", email.getText().toString());
        data.put("identification", identification.getText().toString());
        data.put("civil_state", civilState.getText().toString());
        data.put("sex", sex.getText().toString());
        data.put("occupation", occupation.getText().toString());
        data.put("phone", phone.getText().toString());

        pass.put("password", password.getText().toString());
        pass.put("new_password", newPassword.getText().toString());

        body.put("data", data);
        body.put("pass", pass);
        body.put("role", preferences.getString("role", ""));

        return new JSONObject(body);
    }

    private void setInitForm() {
        names.setText(preferences.getString("username", ""));
        lastNames.setText(preferences.getString("lastnames", ""));
        email.setText(preferences.getString("email", ""));
        identification.setText(preferences.getString("identification", ""));
        civilState.setText(preferences.getString("civil_state", ""));
        sex.setText(preferences.getString("sex", ""));
        phone.setText(preferences.getString("phone", ""));
        occupation.setText(preferences.getString("occupation", ""));
    }

    private boolean validateForm() {
        Boolean answer = false;

        if(names.getText().toString().equals("")){
            Snackbar.make(rootView, "Nombre es obligatorio", Snackbar.LENGTH_SHORT).show();
        }else if(lastNames.getText().toString().equals("")){
            Snackbar.make(rootView, "Apellido es obligatorio", Snackbar.LENGTH_SHORT).show();
        }else if(email.getText().toString().equals("")){
            Snackbar.make(rootView, "Correo es obligatorio", Snackbar.LENGTH_SHORT).show();
        }else if(identification.getText().toString().equals("")){
            Snackbar.make(rootView, "Identificación es obligatoria", Snackbar.LENGTH_SHORT).show();
        }else if(occupation.getText().toString().equals("")){
            Snackbar.make(rootView, "Ocupación es obligatoria", Snackbar.LENGTH_SHORT).show();
        }else if(sex.getText().toString().equals("")){
            Snackbar.make(rootView, "Sexo es obligatorio", Snackbar.LENGTH_SHORT).show();
        }else if(civilState.getText().toString().equals("")){
            Snackbar.make(rootView, "Estado civil es obligatorio", Snackbar.LENGTH_SHORT).show();
        }else if(phone.getText().toString().equals("")){
            Snackbar.make(rootView, "Teléfono es obligatorio", Snackbar.LENGTH_SHORT).show();
        }else{
            answer = true;
        }

        return answer;
    }

}
