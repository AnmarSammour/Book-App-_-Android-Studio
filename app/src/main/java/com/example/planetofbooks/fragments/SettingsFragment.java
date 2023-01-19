package com.example.planetofbooks.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.planetofbooks.R;
import com.example.planetofbooks.apis.RetrofitDelete;
import com.example.planetofbooks.apis.RetrofitDeleteFav;
import com.example.planetofbooks.model.Favorite;
import com.example.planetofbooks.model.Result;
import com.example.planetofbooks.model.User;
import com.example.planetofbooks.SharedPref.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    Button deleteBtn;
    int Id;
    String email;
    private List<Favorite> favorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment,container,false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //Get the Id of current user to delete the account
        User user = SharedPrefManager.getInstance(getContext()).getUsers();

        Id = user.getId();

        deleteBtn = getView().findViewById(R.id.deleteBtn);
        deleteBtn.setBackgroundColor(Color.RED);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteUser();
                DeleteUserFav();
            }
        });


    }

    public void DeleteUser(){

        Call<Result> call = RetrofitDelete.getInstance().getMyApi().deleteUser(Id);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.body().getError() == false){

                    Toast.makeText(getContext(),"Response msg ---> "+ response.body().getMessage(),Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    Log.d("msg ---> ",response.body().getMessage());
                    SharedPrefManager.getInstance(getContext()).Logout();
                }else if (response.body().getError() == true){
                    Toast.makeText(getContext(),"Response msg ---> "+ response.body().getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("msg ---> ",response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(getContext(),"Error ---> "+t.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("Error ---> ",t.getMessage());

            }
        });
    }

    public void DeleteUserFav(){
        Call<Result> call = RetrofitDeleteFav.getInstance().getMyApi().deleteFav(email);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.body().getError() == false)
                {

                    Toast.makeText(getContext(), response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
                else if (response.body().getError() == true)
                {
                    Toast.makeText(getContext(),"Response msg ---> "+ response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(getContext(),"Error ---> "+t.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("Error ---> ",t.getMessage());

            }
        });
    }

}
