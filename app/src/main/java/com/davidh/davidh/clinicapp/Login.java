package com.davidh.davidh.clinicapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText idUser, pass;
    TextView goToRegister;
    Button btnLogin;
    JsonObjectRequest jsonRequest;
    private RequestQueue req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idUser = (EditText)findViewById(R.id.et_login_idUser);
        pass = (EditText)findViewById(R.id.et_login_pass);
        btnLogin = (Button)findViewById(R.id.btn_login_deposit);
        goToRegister = (TextView) findViewById(R.id.linkLoginToRegister);


        idUser.setHintTextColor(getResources().getColor(R.color.textApp));
        pass.setHintTextColor(getResources().getColor(R.color.textApp));

        req = Volley.newRequestQueue(this);

        //cRequest =  new CustomRequest(req);

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    public void loginUser(){
        if (idUser.getText().toString().length() <= 0){
            idUser.setError(getString(R.string.login_error_idUser));
        }else if (pass.getText().toString().length() <= 0) {
            pass.setError(getString(R.string.login_error_pass));
        }
        else {
            checkUserIsMedical();
        }
    }

    private void goToRegister(){
        Intent intent = new Intent(getApplicationContext(), Register.class);
        finish();
        startActivity(intent);
    }

    private void initSession(String id, String username, String email, String role){
        SharedPreferences sharedPreferences = getSharedPreferences("user_connected", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("_id", id);
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("role", role);
        editor.putString("identification", idUser.getText().toString());
        editor.commit();
        Log.d("INITSESSION", editor.toString());
    }

    private JSONObject getBody(){
        Map body = new HashMap();
        body.put("identification", idUser.getText().toString());
        body.put("password", pass.getText().toString());
        return new JSONObject(body);
    }

    private void initApp(String typeUser){
        Log.d("INITAPP", typeUser);
        Intent intent = new Intent(getApplicationContext(), MenuApp.class);
        //intent.putExtra("typeUser", typeUser);
        finish();
        startActivity(intent);
    }

    private void checkUserIsMedical(){
        final ProgressDialog pd = ProgressDialog.show(this, "Verificando credenciales del usuario", "Esperando respuesta");

        req.start();
        jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                UrlService.loginUserInApp,
                getBody(),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try{
                            Log.d("AFTERLOGIN", response.toString());

                            pd.dismiss();

                            if(response.getString("status").equals("success")){
                                JSONObject userToConnect = response.getJSONObject("data");
                                initSession(userToConnect.getString("_id"), userToConnect.getString("names"), userToConnect.getString("email"), response.getString("typeUser"));
                                initApp(response.getString("typeUser"));
                            }else{
                                Snackbar.make(findViewById(R.id.container_login), response.getString("message"), Snackbar.LENGTH_SHORT).show();
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
