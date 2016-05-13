package com.mot.AndroidDP;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by bkmr38 on 5/10/2016.
 */
public class Login extends Activity {

    private Button loginButton;
    private EditText userName;
    private EditText userPassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        loginButton =(Button)findViewById(R.id.login);
        userName=(EditText)findViewById(R.id.username);
        userPassword=(EditText)findViewById(R.id.password);

        userName.setText("cps");
        userPassword.setText("cps");


        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user=userName.getText().toString();
                String password=userPassword.getText().toString();

                boolean result=HttpUtility.ValidateUser(user,password);
                if(result){
                    GlobalPara.UserName=user;
                    GlobalPara.PSW=password;
                    Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Incorrect User Name or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}