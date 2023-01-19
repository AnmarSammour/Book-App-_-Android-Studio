package com.example.planetofbooks.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.planetofbooks.R;
import com.example.planetofbooks.SharedPref.SharedPrefManager;
import com.example.planetofbooks.apis.RetrofitDeleteFav;
import com.example.planetofbooks.fragments.ViewBookFragment;
import com.example.planetofbooks.model.Favorite;
import com.example.planetofbooks.model.Result;
import com.example.planetofbooks.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>
{

    private List<Favorite> favItemList;
    //the context object
    private Context mCtx;
    boolean flag = false;

    String namebook;

    Fragment fragment;
    public FavoriteAdapter(Context context, List<Favorite> favItemList,Fragment fragment){
        this.favItemList = favItemList;
        this.mCtx = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_itemfav,parent,false);


        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String email = SharedPrefManager.getInstance(fragment.getActivity()).getUsers().getEmail();

        if(favItemList.get(position).getUser_email().toString().contains(email)){
            holder.name.setText(favItemList.get(position).getNamebook());
            Glide.with(mCtx).load("http://10.0.2.2/books/img/"+favItemList.get(position).getImgLink())
                    .apply(new RequestOptions().override(350,350))
                    .error(R.drawable.notfound).into(holder.imageView);

            holder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("Namebook",favItemList.get(position).getNamebook());
                bundle.putString("Nameauthor",favItemList.get(position).getNameauthor());
                bundle.putString("Year",favItemList.get(position).getYear());
                bundle.putString("ImgLink",favItemList.get(position).getImgLink() );
                bundle.putString("Description",favItemList.get(position).getDescription());

                AppCompatActivity activity =(AppCompatActivity) view.getContext();
                ViewBookFragment viewBookFragment = new ViewBookFragment();
                viewBookFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,viewBookFragment).commit();

            });
        }else {


            holder.itemView.setVisibility(View.INVISIBLE);
            holder.name.setText(favItemList.get(position).getNamebook());
            Glide.with(mCtx).load("http://10.0.2.2/books/img/"+favItemList.get(position).getImgLink())
                    .apply(new RequestOptions().override(350,350))
                    .error(R.drawable.notfound).into(holder.imageView);
        }

        namebook =favItemList.get(position).getNamebook();

        holder.favIconfav.setOnClickListener((view -> {
            favItemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, favItemList.size());
            DeleteUserFav();

        }));


    }

    public void DeleteUserFav(){
        Call<Result> call = RetrofitDeleteFav.getInstance().getMyApi().deleteFav(namebook);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.body().getError() == false)
                {

                    Toast.makeText(mCtx, response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
                else if (response.body().getError() == true)
                {
                    Toast.makeText(mCtx,"Response msg ---> "+ response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(mCtx,"Error ---> "+t.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("Error ---> ",t.getMessage());

            }
        });
    }

    @Override
    public int getItemCount() {
        return favItemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView imageView,favIconfav;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.booknamefav);
            imageView =itemView.findViewById(R.id.imageViewfav);
            favIconfav = itemView.findViewById(R.id.favBtnfav);


        }
    }
}
