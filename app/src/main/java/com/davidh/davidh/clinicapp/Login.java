package com.davidh.davidh.clinicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText idUser, pass;
    TextView goToRegister;
    Button deposit;
    CustomRequest cRequest;
    RequestQueue req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idUser = (EditText)findViewById(R.id.et_login_idUser);
        pass = (EditText)findViewById(R.id.et_login_pass);
        deposit = (Button)findViewById(R.id.btn_login_deposit);
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

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkUserInMedicalCenter();
            }
        });

    }

    public void checkUserInMedicalCenter(){
        if (idUser.getText().toString().length() <= 0){
            idUser.setError(getString(R.string.login_error_idUser));
        }else if (pass.getText().toString().length() <= 0) {
            pass.setError(getString(R.string.login_error_pass));
        }
        else {

            //hacer cualquier cosa
            String [] keys = {"claves"};
            String [] values = {"valores"};
            //cRequest.checkUserInMedicalCenter(keys, values);

            //Log.d("XXX", res.toString());
        }
    }

    private void goToRegister(){
        Intent intent = new Intent(getApplicationContext(), Register.class);
        finish();
        startActivity(intent);
    }
}
