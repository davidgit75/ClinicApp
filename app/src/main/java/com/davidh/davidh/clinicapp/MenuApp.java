package com.davidh.davidh.clinicapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MenuApp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private JSONObject userConnected;
    private int appDrawer, appLayout, appNavView;
    private String roleUser;
    private SharedPreferences preferences;
    public TextView drawerPatientUsername, drawerPatientEmail, drawerMedicUsername, drawerMedicEmail;
    PatienHistory patientHistory;
    PatienTest patientTest;
    PatientProfile patientProfile;
    MedicPatients medicPatients;
    MediTests medicTests;
    newClicinalHistory newHistory;
    MedicProfile medicProfile;
    int containerPatient, containerMedic;

    FragmentManager fragment;
    FragmentTransaction transaction;

    //FloatingActionButton fabActionsMedic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("user_connected", Context.MODE_PRIVATE);

        initPreferences(); // R.layout.DRAWER_CALLER R.id.DRAWER

        setContentView(appLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(appDrawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(appNavView);
        navigationView.setNavigationItemSelectedListener(this);

        drawerPatientUsername = (TextView) findViewById(R.id.drawerPatientUsername);
        drawerPatientEmail= (TextView) findViewById(R.id.drawerPatientEmail);
        drawerMedicUsername = (TextView) findViewById(R.id.drawerMedicUsername);
        drawerMedicEmail= (TextView) findViewById(R.id.drawerMedicEmail);

        patientHistory = new PatienHistory();
        patientTest = new PatienTest();
        patientProfile = new PatientProfile();
        medicPatients = new MedicPatients();
        medicTests = new MediTests();
        newHistory = new newClicinalHistory();
        medicProfile = new MedicProfile();

        containerPatient = R.id.containerPatient;
        containerMedic = R.id.containerMedic;

        if(roleUser.equals("medic")){
            showFragment(containerMedic, medicPatients);
        }else if(roleUser.equals("patient")){
            showFragment(containerPatient, patientHistory);
        }
    }

    private void initPreferences(){
        Map map = new HashMap();
        map.put("_id", preferences.getString("_id", ""));
        map.put("username", preferences.getString("username", ""));
        map.put("email", preferences.getString("email", ""));
        map.put("role", preferences.getString("role", ""));
        userConnected = new JSONObject(map);

        roleUser = preferences.getString("role", "");

        try{
            if(userConnected.getString("role").equals("medic")){
                appLayout = R.layout.medic_menu_app;
                appDrawer = R.id.medic_drawer_layout;
                appNavView= R.id.nav_view_medic;
            }else if(userConnected.getString("role").equals("patient")){
                appLayout = R.layout.activity_menu_app;
                appDrawer = R.id.drawer_layout;
                appNavView= R.id.nav_view;
            }

        }catch (JSONException e){

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(appDrawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // User actions
        if(roleUser.equals("medic")){
            actionsMedic(id);
        }else if(roleUser.equals("patient")){
            actionsPatient(id);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(appDrawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void actionsPatient(int id){
        switch(id){
            case R.id.patientHistory:
                showFragment(containerPatient, patientHistory);
                break;
            case R.id.patientTests:
                showFragment(containerPatient, patientTest);
                break;
            case R.id.patientProfile:
                showFragment(containerPatient, medicProfile);
                break;
            case R.id.patientBtnLogout:
                logout();
                break;
        }
    }

    private void actionsMedic(int id){
        switch(id){
            case R.id.medicMypatients:
                showFragment(containerMedic, medicPatients);
                break;
            case R.id.medicTests:
                showFragment(containerMedic, medicTests);
                break;
            case R.id.medicNewHistory:
                showFragment(containerMedic, newHistory);
                break;
            case R.id.medicProfile:
                showFragment(containerMedic, medicProfile);
                break;
            case R.id.medicBtnLogout:
                logout();
                break;
            default:
                showFragment(containerMedic, medicPatients);
                break;

        }
    }

    private void showFragment(int id, Fragment frag){
        fragment = getSupportFragmentManager();
        transaction = fragment.beginTransaction();
        transaction.replace(id, frag);
        transaction.commit();
    }

    private void logout(){
        finish();
        SharedPreferences user_connected = getSharedPreferences("user_connected", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = user_connected.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
}
