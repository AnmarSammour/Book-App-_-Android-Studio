package com.example.planetofbooks.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planetofbooks.R;
import com.example.planetofbooks.apis.RetrofitSignUp;
import com.example.planetofbooks.model.Result;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    public Button RegBtn,clearBtn;
    TextInputEditText name,pass,phone,email,ConfirmPassword;
    public String Name,Pass,Email,Phone,ConfirmPassword1;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar =(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sing Up");
        setSupportActionBar(toolbar);

        name = (TextInputEditText) findViewById(R.id.name);
        pass = (TextInputEditText) findViewById(R.id.pass);
        phone = (TextInputEditText) findViewById(R.id.phone);
        email = (TextInputEditText) findViewById(R.id.email);

        ConfirmPassword = (TextInputEditText) findViewById(R.id.ConfirmPassword);

        clearBtn = (Button) findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignIn.class));
            }
        });

        RegBtn = (Button) findViewById(R.id.signupBtn);
        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Call insertUser method
                insertUser();


            }
        });
    }//End onCreate method

    private void insertUser(){
        if(!UsernameValidate() || !EmailValidate()|| !PasswordValidate() || !validatePassword() || !PhoneValidate() )
            return;
        else {
            //retrieve data from Edit texts
            Name = name.getText().toString().trim();
            Pass = pass.getText().toString().trim();
            Email = email.getText().toString().trim();
            Phone = phone.getText().toString().trim();

            //Here we will handle the http request to insert user to mysql db

            Call<Result> call = RetrofitSignUp.getInstance().getMyApi().insertUser(Name, Pass, Email, Phone);

            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {


                    if (response.body().getError() == true) {

                        Log.d("something goes wrong --- > ", response.body().getMessage());
                        Toast.makeText(SignUp.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else if (response.body().getError() == false) {

                        Log.d("Response ---> ", "User registered successfully");

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                        startActivity(new Intent(getApplicationContext(), SignIn.class));


                    }


                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                    Log.d("Failed to Insert Data ---> ", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Failed to Insert Data --> " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }//End insertUser method

    boolean UsernameValidate()
    {
        Name = name.getText().toString().trim();
        if(Name.isEmpty())
        {
            name.setError("This Field should not be empty");
            return false;
        }
        else if(Name.length()>30 )
        {
            name.setError("Name field should not be greater than 20 charcter");
            return false;
        }
        else
        {
            return true;
        }
    }//end UsernameValidate

    boolean PasswordValidate()
    {
        Pass = pass.getText().toString().trim();
        if(Pass.isEmpty())
        {
            pass.setError("This Field should not be empty");
            return false;
        }
        else if(Pass.length()<8 )
        {
            pass.setError("Password field should not be greater than 8 charcter");
            return false;
        }
        else
        {
            return true;
        }
    }//end PasswordValidate

    boolean PhoneValidate()
    {
        Phone = phone.getText().toString().trim();
        String validIDs = Phone.substring(0,2);

        if(Phone.isEmpty())
        {
            phone.setError("This Field should not be empty");
            return false;
        }
        else if(Phone.length()!=9 )
        {
            phone.setError("The length of number should be 9 digits");
            return false;
        }
        else if(!validIDs.equals("56") && !validIDs.equals("59") && !validIDs.equals("52") && !validIDs.equals("54"))
        {
            phone.setError("Sorry your number is not valid");
            return false;
        }
        else
        {
            return true;
        }
    }//end PhoneValidate

    boolean EmailValidate()
    {

        Email = email.getText().toString().trim();
        if(Email.isEmpty())
        {
            email.setError("This Field should not be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            email.setError("Sorry your Email address is not corract");
            return false;
        }
        else
        {
            return true;
        }
    }//end EmailValidate

    boolean validatePassword() {
        String passwordInput = pass.getText().toString().trim();
        ConfirmPassword1 = ConfirmPassword.getText().toString().trim();
        if (ConfirmPassword1.isEmpty()) {
            ConfirmPassword.setError("Field can't be empty");
            return false;
        }
        if (!passwordInput.equals(ConfirmPassword1)) {
            ConfirmPassword.setError("Password Would Not be matched");
            return false;
        }else {
            return true;
        }
    }


}