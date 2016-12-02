package com.davidh.davidh.clinicapp;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
public class newClicinalHistory extends Fragment {

    Button btnSendHistory, btnSearchIdUser;
    View rootView;

    EditText idUser ,names, lastNames, email, age, civilState, sex, occupation, phone,
            reasonQuery, parentalRecords, sickness, surgeries, allergies, tests, recommendations;
    CheckBox drunk, smoke, drugs;

    Boolean existPatient;
    Boolean bDrunk = false, bDrugs = false, bSmoke = false;

    JsonObjectRequest jsonRequest;
    private RequestQueue req;



    public newClicinalHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_new_clicinal_history, container, false);


        idUser = (EditText) rootView.findViewById(R.id.et_IdUserNewHistory);

        names = (EditText) rootView.findViewById(R.id.nhNames);
        names.setEnabled(false);

        lastNames = (EditText) rootView.findViewById(R.id.nhLastNames);
        lastNames.setEnabled(false);

        email = (EditText) rootView.findViewById(R.id.nhEmail);
        email.setEnabled(false);

        age = (EditText) rootView.findViewById(R.id.nhAge);
        age.setEnabled(false);

        civilState = (EditText) rootView.findViewById(R.id.nhCivilState);
        civilState.setEnabled(false);

        sex = (EditText) rootView.findViewById(R.id.nhSex);
        sex.setEnabled(false);

        occupation = (EditText) rootView.findViewById(R.id.nhOccupation);
        occupation.setEnabled(false);

        phone = (EditText) rootView.findViewById(R.id.nhPhone);
        phone.setEnabled(false);

        reasonQuery = (EditText) rootView.findViewById(R.id.nhReasonQuery);
        parentalRecords = (EditText) rootView.findViewById(R.id.nhParental);
        sickness = (EditText) rootView.findViewById(R.id.nhSickness);
        surgeries = (EditText) rootView.findViewById(R.id.nhSurgieries);
        allergies = (EditText) rootView.findViewById(R.id.nhAllergies);
        tests = (EditText) rootView.findViewById(R.id.nhTests);
        recommendations = (EditText) rootView.findViewById(R.id.nhRecommendations);

        drunk = (CheckBox) rootView.findViewById(R.id.nhDrunk);
        drugs = (CheckBox) rootView.findViewById(R.id.nhDrugs);
        smoke = (CheckBox) rootView.findViewById(R.id.nhSmoke);

        req = Volley.newRequestQueue(getActivity().getApplicationContext());

        btnSendHistory = (Button) rootView.findViewById(R.id.nhSend);
        btnSendHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendHistory();
            }
        });

        btnSearchIdUser = (Button) rootView.findViewById(R.id.btnSearchPatientInConsultation);
        btnSearchIdUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchDataUser();
            }
        });

        drunk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bDrunk = b;
            }
        });

        drugs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bDrugs = b;
            }
        });

        smoke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bSmoke= b;
            }
        });


        return rootView;
    }

    private Boolean validateForm(){
        if(idUser.getText().length()<=0){
            idUser.setError("Debe ingresar la identificación del usuario");
            idUser.requestFocus();
            return false;
        }else if(names.getText().length()<=0){
            names.setError("Nombres obligatorios");
            names.requestFocus();
            return false;
        }else if(lastNames.getText().length()<=0){
            lastNames.setError("Apellidos obligatorios");
            lastNames.requestFocus();
            return false;
        }else if(email.getText().length()<=0){
            email.setError("Correo obligatorio");
            email.requestFocus();
            return false;
        }else if(age.getText().length()<=0){
            age.setError("Edad obligatoria");
            age.requestFocus();
            return false;
        }else if(civilState.getText().length()<=0){
            civilState.setError("Esto civil obligatorio");
            civilState.requestFocus();
            return false;
        }else if(sex.getText().length()<=0){
            sex.setError("Sexo obligatorio");
            sex.requestFocus();
            return false;
        }else if(occupation.getText().length()<=0){
            occupation.setError("Ocupación obligatoria");
            occupation.requestFocus();
            return false;
        }else if(phone.getText().length()<=0){
            phone.setError("Teléfono obligatorio");
            phone.requestFocus();
            return false;
        }else if(reasonQuery.getText().length()<=0){
            reasonQuery.setError("Razón de la consulta obligatoria");
            reasonQuery.requestFocus();
            return false;
        }else if(parentalRecords.getText().length()<=0){
            parentalRecords.setError("Antecedentes necesarios");
            parentalRecords.requestFocus();
            return false;
        }else if(sickness.getText().length()<=0){
            sickness.setError("Descripción de enfermedades obligatoria");
            sickness.requestFocus();
            return false;
        }else if(surgeries.getText().length()<=0){
            surgeries.setError("Descripción de cirugías obligatoria");
            surgeries.requestFocus();
            return false;
        }else if(allergies.getText().length()<=0){
            allergies.setError("Descripción de alergias obligatoria");
            allergies.requestFocus();
            return false;
        }else if(tests.getText().length()<=0){
            tests.setError("Describa si el paciente requiere exámenes");
            tests.requestFocus();
            return false;
        }else if(recommendations.getText().length()<=0){
            recommendations.setError("Describa si tiene recomendaciones para el paciente");
            recommendations.requestFocus();
            return false;
        }

        return true;
    }

    private void searchDataUser() {
        if(idUser.getText().toString().equals("")){
            Snackbar.make(rootView, "Debe ingresar la identificación para realizar la consulta", Snackbar.LENGTH_SHORT).show();
        }else{
            Map bodyMap = new HashMap();
            bodyMap.put("identification", idUser.getText().toString());

            req.start();
            jsonRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    UrlService.checkExistPatient,
                    new JSONObject(bodyMap),
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response){
                            try{
                                Log.d("OEEEEE", response.toString());
                                if(response.getString("status").equals("error")){
                                    Snackbar.make(rootView, response.getString("message"), Snackbar.LENGTH_SHORT).show();
                                }else if(response.getString("status").equals("success")){
                                    Boolean state = !response.getBoolean("exist");
                                    existPatient = !state;
                                    if(response.getBoolean("exist")){
                                        names.setText(response.getJSONObject("data").getString("names"));
                                        lastNames.setText(response.getJSONObject("data").getString("lastnames"));
                                        email.setText(response.getJSONObject("data").getString("email"));
                                        age.setText(response.getJSONObject("data").getString("age"));
                                        civilState.setText(response.getJSONObject("data").getString("civil_state"));
                                        sex.setText(response.getJSONObject("data").getString("sex"));
                                        occupation.setText(response.getJSONObject("data").getString("occupation"));
                                        phone.setText(response.getJSONObject("data").getString("phone"));
                                    }else{
                                        names.setText("");
                                        lastNames.setText("");
                                        email.setText("");
                                        age.setText("");
                                        civilState.setText("");
                                        sex.setText("");
                                        occupation.setText("");
                                        phone.setText("");

                                    }

                                    names.setEnabled(state);
                                    lastNames.setEnabled(state);
                                    email.setEnabled(state);
                                    age.setEnabled(state);
                                    civilState.setEnabled(state);
                                    sex.setEnabled(state);
                                    occupation.setEnabled(state);
                                    phone.setEnabled(state);
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

    }

    private void sendHistory() {
        if(validateForm()){
            createHistory();
        }
    }

    private void createHistory() {
        req.start();
        jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                UrlService.sendNewHistory,
                generateBody(),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try{
                            if(response.getString("status").equals("success")){
                                clearForm();
                            }

                            Snackbar.make(rootView, response.getString("message"), Snackbar.LENGTH_SHORT).show();

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
        Map newPatient = new HashMap();
        Map dataHistory = new HashMap();
        Map records= new HashMap();
        Map pathological = new HashMap();
        Map<String, Boolean> personal = new HashMap();

        if(!existPatient){
            newPatient.put("identification", idUser.getText().toString());
            newPatient.put("names", names.getText().toString());
            newPatient.put("lastnames", lastNames.getText().toString());
            newPatient.put("email", email.getText().toString());
            newPatient.put("password", idUser.getText().toString());
            newPatient.put("age", age.getText().toString());
            newPatient.put("civil_state", civilState.getText().toString());
            newPatient.put("sex", sex.getText().toString());
            newPatient.put("occupation", occupation.getText().toString());
            newPatient.put("phone", phone.getText().toString());
        }

        dataHistory.put("reason_to_query", reasonQuery.getText().toString());

        personal.put("drunk", bDrunk);
        personal.put("smoke", bSmoke);
        personal.put("drugs", bDrugs);

        pathological.put("sickness", sickness.getText().toString());
        pathological.put("surgeries", surgeries.getText().toString());
        pathological.put("allergies", allergies.getText().toString());


        records.put("personal", personal);
        records.put("parental", parentalRecords.getText().toString());
        records.put("pathological", pathological);

        dataHistory.put("records", records);
        dataHistory.put("tests", tests.getText().toString());
        dataHistory.put("recommendations", recommendations.getText().toString());
        dataHistory.put("medic", getActivity().getSharedPreferences("user_connected", Context.MODE_PRIVATE).getString("_id", ""));


        body.put("identification", idUser.getText().toString());
        body.put("existPatient", existPatient);
        body.put("newPatient", newPatient);
        body.put("dataHistory", dataHistory);

        return new JSONObject(body);
    }


    private void clearForm(){
        names.setText("");
        lastNames.setText("");
        email.setText("");
        age.setText("");
        civilState.setText("");
        sex.setText("");
        occupation.setText("");
        phone.setText("");

        reasonQuery.setText("");
        drunk.setChecked(false);
        drugs.setChecked(false);
        smoke.setChecked(false);
        parentalRecords.setText("");
        sickness.setText("");
        surgeries.setText("");
        allergies.setText("");
        tests.setText("");
        recommendations.setText("");
    }



}
