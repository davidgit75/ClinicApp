package com.davidh.davidh.clinicapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class HistoryDetails extends AppCompatActivity {

    private JSONObject data;
    private TextView txvIdentification, txvPhone, txvEmail, txvGeneralInfo, txvSubGeneralInfo, txvMedicName,
            txvCenterName, txvRecordsPersonal, txvRecordsParental, txvRecordsSickness,
            txvRecordsSurgeries, txvRecordsAllergies, txvReasonsQuery, txvTests, txvRecommendations;

    private String names, lastNames, identification, sex, age, civilState, occupation,
            phone, email, medicName, centerName, strDrugs, strDrunk, strSmoke,
            parental, sickness, surgueries, allergies, reasonsQuery, tests, recommendations;

    private Boolean drunk, smoke, drugs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txvIdentification= (TextView) findViewById(R.id.hd_identification);
        txvPhone = (TextView) findViewById(R.id.hd_phone);
        txvEmail = (TextView) findViewById(R.id.hd_email);
        txvGeneralInfo = (TextView) findViewById(R.id.hd_generalInfo); // sex, civil state
        txvSubGeneralInfo = (TextView) findViewById(R.id.hd_subGeneralInfo); // occupation, age
        txvMedicName = (TextView) findViewById(R.id.hd_medic);
        txvCenterName = (TextView) findViewById(R.id.hd_medicalcenter);
        txvRecordsPersonal = (TextView) findViewById(R.id.hd_records_personal);
        txvRecordsParental = (TextView) findViewById(R.id.hd_records_parental);
        txvRecordsSickness = (TextView) findViewById(R.id.hd_records_sickness);
        txvRecordsSurgeries = (TextView) findViewById(R.id.hd_records_surgeries);
        txvRecordsAllergies = (TextView) findViewById(R.id.hd_records_allergies);
        txvReasonsQuery = (TextView) findViewById(R.id.hd_reasonsQuery);
        txvTests = (TextView) findViewById(R.id.hd_tests);
        txvRecommendations = (TextView) findViewById(R.id.hd_recommendations);

        getData();
    }

    private void getData() {
        String strData = getIntent().getStringExtra("data");
        try{
            data = new JSONObject(strData);
            Log.d("HISTORYDETAILS", data.toString());
            showData();
        }catch(JSONException e){}
    }

    private void showData() {
        try{
            names = data.getJSONObject("patient").getString("names");
            lastNames = data.getJSONObject("patient").getString("lastnames");
            identification = data.getJSONObject("patient").getString("identification");
            phone = data.getJSONObject("patient").getString("phone");
            email = data.getJSONObject("patient").getString("email");
            sex = data.getJSONObject("patient").getString("sex");
            civilState = data.getJSONObject("patient").getString("civil_state");
            occupation = data.getJSONObject("patient").getString("occupation");
            age = data.getJSONObject("patient").getString("age");
            medicName = data.getJSONObject("medic").getString("names");
            centerName = data.getJSONObject("medicalcenter").getString("name");

            drunk = data.getJSONObject("records").getJSONObject("personal").getBoolean("drunk");
            drugs = data.getJSONObject("records").getJSONObject("personal").getBoolean("drugs");
            smoke = data.getJSONObject("records").getJSONObject("personal").getBoolean("smoke");

            if(drunk){
                strDrunk = "Bebe";
            }else{
                strDrunk = "No bebe";
            }

            if(drugs){
                strDrugs = "Usa drogas";
            }else{
                strDrugs = "No usa drogas";
            }

            if(smoke){
                strSmoke= "Fuma";
            }else{
                strSmoke = "No fuma";
            }

            parental = data.getJSONObject("records").getString("parental");
            sickness = data.getJSONObject("records").getJSONObject("pathological").getString("sickness");
            surgueries = data.getJSONObject("records").getJSONObject("pathological").getString("surgeries");
            allergies = data.getJSONObject("records").getJSONObject("pathological").getString("allergies");

            reasonsQuery = data.getString("reason_to_query");
            tests = data.getString("tests");
            recommendations = data.getString("recommendations");

            setTitle(names + " " + lastNames);

            Log.d("HISTORYDETAILS2", names + " " + lastNames);

            txvIdentification.setText(identification);
            txvPhone.setText(phone);
            txvEmail.setText(email);
            txvGeneralInfo.setText(sex + ", " + civilState);
            txvSubGeneralInfo.setText(occupation + ", " + age + " años");
            txvMedicName.setText(medicName);
            txvCenterName.setText(centerName);
            txvRecordsPersonal.setText(strDrunk + ", " + strSmoke  + ", " + strDrugs);
            txvRecordsParental.setText(parental);
            txvRecordsSickness.setText(sickness);
            txvRecordsSurgeries.setText(surgueries);
            txvRecordsAllergies.setText(allergies);
            txvReasonsQuery.setText(reasonsQuery);
            txvTests.setText(tests);
            txvRecommendations.setText(recommendations);

        }catch(JSONException e){
            Log.d("ERROR SHOW", e.getMessage().toString());
        }
    }
}
