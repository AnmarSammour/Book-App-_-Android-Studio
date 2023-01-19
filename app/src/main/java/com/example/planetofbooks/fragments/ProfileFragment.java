package com.example.planetofbooks.fragments;


import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.planetofbooks.R;
import com.example.planetofbooks.SharedPref.SharedPrefManager;
import com.example.planetofbooks.activities.MainActivity;
import com.example.planetofbooks.apis.RetrofitUpdate;
import com.example.planetofbooks.model.Result;
import com.example.planetofbooks.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    TextInputLayout name,password,email,phone;
    public String Name,Pass,Email,Phone;

    Button updateBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        name = (TextInputLayout) getView().findViewById(R.id.TextInputUsername);
        email =(TextInputLayout) getView().findViewById(R.id.TextInputEmail);
        password =(TextInputLayout) getView().findViewById(R.id.TextInputPassword);
        phone = (TextInputLayout) getView().findViewById(R.id.TextInputPhone);


        //get the Info of current user
        User user = SharedPrefManager.getInstance(getContext()).getUsers();
        name.getEditText().setText(user.getName());
        password.getEditText().setText(user.getPassword());
        email.getEditText().setText(user.getEmail());
        phone.getEditText().setText(user.getPhone());

        updateBtn = getView().findViewById(R.id.updateBtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!UsernameValidateupdate() || !EmailValidateupdate()|| !PasswordValidateupdate() ||  !PhoneValidateupdate() )
                    return;
                else {

                    Call<Result> call = RetrofitUpdate.getInstance().getMyApi().updateUser(user.getId(),
                            name.getEditText().getText().toString(), email.getEditText().getText().toString(),
                            password.getEditText().getText().toString(), phone.getEditText().getText().toString());

                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {


                            if (response.body().getError() == true || response.body().getUser().getName() == null) {
                                Toast.makeText(getContext(), "response msg ---> " + response.body().getMessage(), Toast.LENGTH_LONG).show();

                            } else {


                                User user = new User(response.body().getUser().getId(), response.body().getUser().getName(),
                                        response.body().getUser().getEmail(), response.body().getUser().getPassword(),
                                        response.body().getUser().getPhone());
                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getContext()).userUpdate(user);
                                //reload the info of navigation drawer header
                                MainActivity object = new MainActivity();
                                object.setHeaderInfo();

                                Toast.makeText(getContext(), "response msg ---> " + response.body().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Toast.makeText(getContext(), "something goes wrong!! ===> " + t.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("Error 102 --> ", t.getMessage());

                        }
                    });
                }


            }
        });

    }
    boolean UsernameValidateupdate()
    {
        Name = name.getEditText().getText().toString().trim();
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

    boolean PasswordValidateupdate()
    {
        Pass = password.getEditText().getText().toString().trim();
        if(Pass.isEmpty())
        {
            password.setError("This Field should not be empty");
            return false;
        }
        else if(Pass.length()<8 )
        {
            password.setError("Password field should not be greater than 8 charcter");
            return false;
        }
        else
        {
            return true;
        }
    }//end PasswordValidate

    boolean PhoneValidateupdate()
    {
        Phone = phone.getEditText().getText().toString().trim();
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

    boolean EmailValidateupdate()
    {

        Email = email.getEditText().getText().toString().trim();
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

}