package com.davidh.davidh.clinicapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    EditText idUser, pass;
    Button deposit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idUser = (EditText)findViewById(R.id.et_login_idUser);
        pass = (EditText)findViewById(R.id.et_login_pass);
        deposit = (Button)findViewById(R.id.btn_login_deposit);

        idUser.setHintTextColor(getResources().getColor(R.color.textApp));
        pass.setHintTextColor(getResources().getColor(R.color.textApp));

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idUser.getText().toString().length() <= 0){
                    idUser.setError(getString(R.string.login_error_idUser));
                }else if (pass.getText().toString().length() <= 0) {
                    pass.setError(getString(R.string.login_error_pass));
                }
                else {
                 //hacer cualquier cosa
                }
            }
        });

    }
}
